package com.openai.bt.dto;

public record ToolCall(String id, String type, FunctionCall function) {}

