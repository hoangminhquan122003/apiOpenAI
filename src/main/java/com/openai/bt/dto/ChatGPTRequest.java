package com.openai.bt.dto;

import java.util.List;

public record ChatGPTRequest(String model, List<Message> messages) {

}
