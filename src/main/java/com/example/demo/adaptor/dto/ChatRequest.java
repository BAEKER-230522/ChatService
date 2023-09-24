package com.example.demo.adaptor.dto;

import com.example.demo.domain.Chat;
import com.example.demo.domain.ChatType;

public record ChatRequest(Long sender, Long receiver, String message, String chatType) {
}