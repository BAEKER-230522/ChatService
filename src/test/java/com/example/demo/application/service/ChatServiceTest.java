package com.example.demo.application.service;

import com.example.demo.TestInitUtil;
import com.example.demo.adaptor.dto.ChatRequest;
import com.example.demo.adaptor.dto.ChatResponse;
import com.example.demo.application.port.out.FindChatPort;
import com.example.demo.application.port.out.SendChatPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

public class ChatServiceTest {
    @Mock
    private FindChatPort findChatPort;
    @Mock
    private SendChatPort sendChatPort;
    @InjectMocks
    private ChatService chatService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    @DisplayName("message 타입의 채팅을 보낸다.")
    void sendMessageType_responseVoid_time1() {
        ChatRequest request = TestInitUtil.requestMessage();
        when(sendChatPort.sendChat(request)).thenReturn(Mono.empty());

        Mono<Void> sendChat = chatService.sendChat(request);

        StepVerifier.create(sendChat).verifyComplete();
        verify(sendChatPort, times(1)).sendChat(request);
    }

    @Test
    @DisplayName("채팅을 조회한다.")
    void findChat() {
        Long sender = 1L;
        Long receiver = 2L;
        ChatResponse response = TestInitUtil.responseMessage();
        when(findChatPort.findChatByMemberId(sender, receiver)).thenReturn(Flux.just(response));

        Flux<ChatResponse> responseFlux = chatService.findBySenderAndReceiver(sender, receiver);
        StepVerifier.create(responseFlux)
                .expectNext(response)
                .verifyComplete();

        verify(findChatPort, times(1)).findChatByMemberId(sender, receiver);
    }

}
