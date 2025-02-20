package com.openai.bt.controller;

import com.openai.bt.dto.ChatGPTRequest;
import com.openai.bt.dto.ChatGPTResponse;
import com.openai.bt.dto.ConversationInfo;
import com.openai.bt.dto.PromptRequest;
import com.openai.bt.service.ChatGPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class OpenAIAPIController {

    @Autowired
    ChatGPTService chatGPTService;

    @PostMapping("/chat")
    public ResponseEntity<ConversationInfo> chat(@RequestBody PromptRequest prompt){
        return ResponseEntity.ok()
                .body(chatGPTService.getChatResponse(prompt));
    }
}
