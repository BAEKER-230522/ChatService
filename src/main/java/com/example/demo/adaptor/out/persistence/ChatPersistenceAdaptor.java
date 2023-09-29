package com.example.demo.adaptor.out.persistence;

import com.example.demo.adaptor.dto.ChatRequest;
import com.example.demo.adaptor.dto.ChatResponse;
import com.example.demo.application.mapper.ChatMapper;
import com.example.demo.application.port.out.FindChatPort;
import com.example.demo.application.port.out.SendChatPort;
import com.example.demo.domain.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Repository
public class ChatPersistenceAdaptor implements SendChatPort, FindChatPort {
    private final ChatMapper chatMapper;
    private final ChatRepository chatRepository;
    @Override
    public Flux<ChatResponse> findChatByMemberId(String sender, String receiver) {
        return chatMapper.toResponse(chatRepository.findBySenderAndReceiver(sender, receiver));
    }

    @Override
    public Mono<Void> sendChat(ChatRequest request) {
        Chat chat = chatMapper.toChat(request);
        return chatRepository.save(chat).then();
    }
}
