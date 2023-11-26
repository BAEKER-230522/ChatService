package com.example.demo.application.service;

import com.example.demo.adaptor.dto.ChatRequest;
import com.example.demo.adaptor.dto.ChatResponse;
import com.example.demo.application.port.in.ChatFindCommand;
import com.example.demo.application.port.in.FindChatUseCase;
import com.example.demo.application.port.in.SendChatCommand;
import com.example.demo.application.port.in.SendChatUseCase;
import com.example.demo.application.port.out.FindChatPort;
import com.example.demo.application.port.out.SendChatPort;
import lombok.RequiredArgsConstructor;
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
    public Flux<ChatResponse> findBySenderAndReceiver(ChatFindCommand command) {
//        chatClientService.findBySenderAndReceiver(sender, receiver);
        return findChatPort.findChatByMemberId(command.sender(), command.receiver());
    }

    @Override
    @Transactional
    public Mono<Void> sendChat(SendChatCommand command) {
//        chatClientService.sendChat(request);
        return sendChatPort.sendChat(command);
    }
}
