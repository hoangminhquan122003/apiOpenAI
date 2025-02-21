package com.openai.bt.dto;

import java.util.List;

public record Message(String role, String content, List<ToolCall> tool_calls) {}

