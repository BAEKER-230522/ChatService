package com.example.demo.adaptor.dto;

import com.example.demo.domain.ChatType;

import java.time.LocalDateTime;

public record ChatResponse(String sender, String receiver, String message, ChatType chatType, LocalDateTime sendMessageAt) {
}
