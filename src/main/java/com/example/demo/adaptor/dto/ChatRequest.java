package com.example.demo.adaptor.dto;

import com.example.demo.domain.Chat;
import com.example.demo.domain.ChatType;

public record ChatRequest(String senderId, String receiverId, String message, String chatType) {
}