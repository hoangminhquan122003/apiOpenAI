package com.openai.bt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.bt.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class ChatGPTService {
    @Autowired
    RestClient restClient;

    @Value("${openai.api.model}")
    private String model;

    @Value("${openai.api.key}")
    private String key;

    public ConversationInfo getChatResponse(PromptRequest promptRequest) {
        Map<String, Object> functionSchema = Map.of(
                "type", "function",
                "function", Map.of(
                        "name", "extractConversationInfo",
                        "description", "Trích xuất thông tin khách hàng từ cuộc trò chuyện",
                        "parameters", Map.of(
                                "type", "object",
                                "properties", Map.of(
                                        "customerName", Map.of("type", "string", "description", "Tên khách hàng"),
                                        "dob", Map.of("type", "string", "description", "Ngày sinh của khách hàng"),
                                        "insuranceAmount", Map.of("type", "string", "description", "Số tiền bảo hiểm"),
                                        "packageType", Map.of("type", "string", "description", "Loại gói bảo hiểm")
                                ),
                                "required", List.of("customerName", "dob", "insuranceAmount", "packageType")
                        )
                )
        );

        ChatGPTRequest chatGPTRequest = new ChatGPTRequest(
                model,
                List.of(new Message("user", promptRequest.prompt(), null)), // `null` vì message đầu vào không có tool_calls
                List.of(functionSchema),
                "required"
        );

        ChatGPTResponse chatGPTResponse = restClient.post()
                .header("Authorization", "Bearer " + key)
                .header("Content-Type", "application/json")
                .body(chatGPTRequest)
                .retrieve()
                .body(ChatGPTResponse.class);

        System.out.println("API Response: " + chatGPTResponse);

        FunctionCall functionCall = chatGPTResponse.choices().getFirst().message().tool_calls().get(0).function();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(functionCall.arguments(), ConversationInfo.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


