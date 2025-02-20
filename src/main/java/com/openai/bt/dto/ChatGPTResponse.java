package com.openai.bt.dto;

import java.util.List;

public record ChatGPTResponse(List<Choice> choices) {

}
