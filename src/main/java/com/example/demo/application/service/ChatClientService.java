package com.example.demo.application.service;

import com.example.demo.adaptor.dto.ChatRequest;
import com.example.demo.adaptor.dto.ChatResponse;
import com.example.demo.application.port.in.FindChatUseCase;
import com.example.demo.application.port.in.SendChatUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ChatClientService{
    private final RSocketRequester rSocketRequester;

    public Flux<ChatResponse> findBySenderAndReceiver(Long sender, Long receiver) {
        return rSocketRequester.route("/chat/{sender}/{receiver}", sender, receiver)
                .retrieveFlux(ChatResponse.class);
    }

    public Mono<Void> sendChat(ChatRequest request) {
        return Mono.just(rSocketRequester.route("/chat")
                .data(request)
                .send()
                .block());
    }
}
