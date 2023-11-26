package com.example.demo.application.port.in;

import com.example.demo.adaptor.dto.ChatRequest;

public record SendChatCommand(String senderId, String receiverId, String message, String chatType) {
    public SendChatCommand toCommand(ChatRequest request){
        return new SendChatCommand(request.senderId(), request.receiverId(), request.message(), request.chatType());
    }
}
