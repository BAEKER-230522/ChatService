package com.example.demo.adaptor.dto;

import com.example.demo.domain.Chat;
import com.example.demo.domain.ChatType;

import java.time.LocalDateTime;

public record ChatRequest(Long sender, Long receiver, String message, ChatType chatType) {
    public Chat toChat() {
        return Chat.builder()
                .sender(sender)
                .receiver(receiver)
                .message(message)
                .chatType(chatType)
                .sendMessageAt(LocalDateTime.now())
                .build();
    }
}
