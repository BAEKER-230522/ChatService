package com.example.demo.adaptor.dto;

import com.example.demo.domain.ChatType;

public record ChatRequest(Long sender, Long receiver, String message, ChatType chatType) {
}
