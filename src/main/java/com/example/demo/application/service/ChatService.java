package com.example.demo.application.service;

import com.example.demo.adaptor.dto.ChatRequest;
import com.example.demo.adaptor.dto.ChatResponse;
import com.example.demo.application.port.in.FindChatUseCase;
import com.example.demo.application.port.in.SendChatUseCase;
import com.example.demo.application.port.out.FindChatPort;
import com.example.demo.application.port.out.SendChatPort;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService implements SendChatUseCase, FindChatUseCase {

    private final FindChatPort findChatPort;
    private final SendChatPort sendChatPort;
    private final ChatClientService chatClientService;

    @Override
    public Flux<ChatResponse> findBySenderAndReceiver(Long sender, Long receiver) {
        chatClientService.findBySenderAndReceiver(sender, receiver);
        return findChatPort.findChatByMemberId(sender, receiver);
    }

    @Override
    @Transactional
    public Mono<Void> sendChat(ChatRequest request) {
        chatClientService.sendChat(request);
        return sendChatPort.sendChat(request);
    }
}
