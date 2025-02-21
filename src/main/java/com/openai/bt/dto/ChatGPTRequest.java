package com.openai.bt.dto;

import java.util.List;
import java.util.Map;

public record ChatGPTRequest(String model, List<Message> messages,List<Map<String, Object>> tools, String tool_choice) {

}
