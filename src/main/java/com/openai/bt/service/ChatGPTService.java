package com.openai.bt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.bt.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class ChatGPTService {
    @Autowired
    RestClient restClient;
    @Value("${openai.api.model}")
    private String model;
    @Value("${openai.api.key}")
    private String key;

    public ConversationInfo getChatResponse(PromptRequest promptRequest){
        String prompt= """
               Trích xuất thông tin từ cuộc trò chuyện sau:
               %s
               Trả về dưới dạng JSON đúng chuẩn như dưới đây trả về luôn không nói bất cứ thứ gì ảnh hưởng đến đoạn json:
               {
                   "customerName": "",
                   "dob": "",
                   "insuranceAmount": "",
                   "packageType": ""
               }              
               """.formatted(promptRequest.prompt());
        ChatGPTRequest chatGPTRequest=new ChatGPTRequest(model,
                List.of(new Message("user",prompt)));
        ChatGPTResponse chatGPTResponse=restClient.post()
                .header("Authorization","Bearer "+key)
                .header("Content-Type","application/json")
                .body(chatGPTRequest)
                .retrieve()
                .body(ChatGPTResponse.class);
        String responseContent= chatGPTResponse.choices().get(0).message().content();
        //responseContent = responseContent.replaceAll("```json|```", "").trim();
        System.out.println( responseContent);
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            return objectMapper.readValue(responseContent, ConversationInfo.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
