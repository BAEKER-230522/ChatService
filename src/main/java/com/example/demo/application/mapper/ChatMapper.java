package com.example.demo.application.mapper;

import com.example.demo.adaptor.dto.ChatRequest;
import com.example.demo.adaptor.dto.ChatResponse;
import com.example.demo.domain.Chat;
import com.example.demo.domain.ChatType;
import com.example.demo.global.util.jwt.JwtProvider;
import com.example.demo.global.util.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class ChatMapper {
    private final RedisUtil redisUtil;
    private final JwtProvider jwtProvider;
    public Chat toChat(ChatRequest request) {
        String chatType = request.chatType().toUpperCase();
        if (chatType.equals("OUT")) return quit(request);
        else if (chatType.equals("IN")) return enter(request);
        return message(request);
    }

    private Chat quit(ChatRequest request) {
        return Chat.builder()
                .sender(request.senderId())
                .receiver(request.receiverId())
                .message(getUserName(request.senderId()) + "님이 나갔습니다.")
                .chatType(chatType(request.chatType()))
                .sendMessageAt(LocalDateTime.now())
                .build();
    }

    private Chat enter(ChatRequest request) {
        return Chat.builder()
                .sender(request.senderId())
                .receiver(request.receiverId())
                .message(getUserName(request.senderId()) + "님이 대화를 시작하였습니다.")
                .chatType(chatType(request.chatType()))
                .sendMessageAt(LocalDateTime.now())
                .build();
    }

    private Chat message(ChatRequest request) {
        return Chat.builder()
                .sender(request.senderId())
                .receiver(request.receiverId())
                .message(request.message())
                .chatType(chatType(request.chatType()))
                .sendMessageAt(LocalDateTime.now())
                .build();
    }

    public Flux<ChatResponse> toResponse(Flux<Chat> chatFlux) {
        return chatFlux.flatMap(chat -> {
            Mono<String> senderMono = Mono.fromFuture(() -> getUserName(String.valueOf(chat.getSender())));
            Mono<String> receiverMono = Mono.fromFuture(() -> getUserName(String.valueOf(chat.getReceiver())));
            return Mono.zip(senderMono, receiverMono)
                    .map(tuple -> new ChatResponse(
                            tuple.getT1(), tuple.getT2(), chat.getMessage(),
                            chat.getChatType(), chat.getSendMessageAt()));
        });
    }

    private ChatType chatType(String chatType) {
        return switch (chatType.toUpperCase()) {
            case "IN" -> ChatType.IN;
            case "OUT" -> ChatType.OUT;
            default -> ChatType.MESSAGE;
        };
    }

    @Async
    @Cacheable(value = "username", key = "#sender")
    public CompletableFuture<String> getUserName(String sender) {
        return jwtProvider.getClaims(redisUtil.getValue(sender))
                .thenApply(claims -> claims.get("username").toString());
    }
}
