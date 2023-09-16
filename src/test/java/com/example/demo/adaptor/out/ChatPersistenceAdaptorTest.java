package com.example.demo.adaptor.out;

import com.example.demo.adaptor.dto.ChatResponse;
import com.example.demo.adaptor.out.persistence.ChatPersistenceAdaptor;
import com.example.demo.adaptor.out.persistence.ChatRepository;
import com.example.demo.application.mapper.ChatMapper;
import com.example.demo.domain.Chat;
import com.example.demo.domain.ChatType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatPersistenceAdaptorTest {

    @Mock
    private ChatMapper chatMapper;
    @Mock
    private ChatRepository chatRepository;

    @InjectMocks
    private ChatPersistenceAdaptor chatPersistenceAdaptor;

    @Test
    @DisplayName("채팅을 조회한다.")
    public void testFindChatByMemberId() {
        Chat mockChat = new Chat(null,ChatType.MESSAGE, 1L, 2L, "testMessage", LocalDateTime.now());
        ChatResponse mockResponse = new ChatResponse("sender", "receiver", "testMessage", ChatType.MESSAGE, LocalDateTime.now());

        when(chatRepository.findBySenderAndReceiver(1L, 2L)).thenReturn(Flux.just(mockChat));
        when(chatMapper.toResponse(any(Flux.class))).thenReturn(Flux.just(mockResponse));

        Flux<ChatResponse> result = chatPersistenceAdaptor.findChatByMemberId(1L, 2L);

        StepVerifier.create(result)
                .expectNext(mockResponse)
                .verifyComplete();
    }


}
