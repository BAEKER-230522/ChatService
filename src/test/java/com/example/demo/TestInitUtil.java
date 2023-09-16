package com.example.demo;

import com.example.demo.adaptor.dto.ChatRequest;
import com.example.demo.adaptor.dto.ChatResponse;
import com.example.demo.domain.ChatType;

import java.time.LocalDateTime;

public class TestInitUtil {
    public static ChatRequest requestMessage() {
        return new ChatRequest(1L, 2L, "hello", ChatType.MESSAGE);
    }

    public static ChatRequest requestEnter() {
        return new ChatRequest(1L, 2L, "enter", ChatType.IN);
    }

    public static ChatRequest requestQuit() {
        return new ChatRequest(1L, 2L, "quit", ChatType.OUT);
    }

    public static ChatResponse responseMessage() {
        return new ChatResponse("sender", "receiver", "hello", ChatType.MESSAGE, LocalDateTime.now());
    }
}
