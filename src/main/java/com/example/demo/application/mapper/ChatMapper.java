package com.example.demo.application.mapper;

import com.example.demo.adaptor.dto.ChatRequest;
import com.example.demo.adaptor.dto.ChatResponse;
import com.example.demo.domain.Chat;
import com.example.demo.domain.ChatType;
import com.example.demo.global.util.jwt.JwtProvider;
import com.example.demo.global.util.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ChatMapper {
    private final RedisUtil redisUtil;
    private final JwtProvider jwtProvider;
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

    public Flux<ChatResponse> toResponse(Flux<Chat> chatFlux) {
        return chatFlux.flatMap(chat -> {
            Mono<String> senderMono = this.getUserName(chat.getSender());
            Mono<String> receiverMono = this.getUserName(chat.getReceiver());

            return Mono.zip(senderMono, receiverMono)
                    .map(tuple -> new ChatResponse(
                            tuple.getT1(), tuple.getT2(), chat.getMessage(),
                            chat.getChatType(), chat.getSendMessageAt()));
        });
    }

    @Cacheable(value = "username", key = "#sender")
    public Mono<String> getUserName(Long sender) {
        return redisUtil.getValue(sender.toString())
                .flatMap(token -> {
                    Map<String, Object> claims = jwtProvider.getClaims(token);
                    return Mono.justOrEmpty(claims.get("username").toString());
                });
    }
}
