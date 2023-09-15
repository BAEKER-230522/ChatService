package com.example.demo.application.mapper;

import com.example.demo.adaptor.dto.ChatRequest;
import com.example.demo.domain.Chat;
import com.example.demo.domain.ChatType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ChatMapper {

    public Chat toChat(ChatRequest request) {
        if (request.chatType().equals(ChatType.OUT)) return quit(request);
        else if (request.chatType().equals(ChatType.IN)) return enter(request);
        return message(request);
    }

    private Chat quit(ChatRequest request) {
        return Chat.builder()
                .sender(request.sender())
                .receiver(request.receiver())
                .message(request.sender() + "님이 나갔습니다.")
                .chatType(request.chatType())
                .sendMessageAt(LocalDateTime.now())
                .build();
    }
    private Chat enter(ChatRequest request) {
        return Chat.builder()
                .sender(request.sender())
                .receiver(request.receiver())
                .message(request.sender() + "님이 대화를 시작하였습니다.")
                .chatType(request.chatType())
                .sendMessageAt(LocalDateTime.now())
                .build();
    }
    private Chat message(ChatRequest request) {
        return Chat.builder()
                .sender(request.sender())
                .receiver(request.receiver())
                .message(request.message())
                .chatType(request.chatType())
                .sendMessageAt(LocalDateTime.now())
                .build();
    }
}
