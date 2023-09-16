package com.example.demo.adaptor.in;

import com.example.demo.adaptor.dto.ChatRequest;
import com.example.demo.adaptor.in.web.ChatController;
import com.example.demo.application.port.in.FindChatUseCase;
import com.example.demo.application.port.in.SendChatUseCase;
import com.example.demo.domain.ChatType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@WebFluxTest(ChatController.class)
public class ChatControllerTest {
    @Autowired
    private WebTestClient testClient;

    @MockBean
    private SendChatUseCase sendChatUseCase;

    @MockBean
    private FindChatUseCase findChatUseCase;

    ChatRequest chatRequest() {
        return new ChatRequest(1L, 2L, "hello", ChatType.MESSAGE);
    }
    @Test
    @DisplayName("message 타입의 채팅을 보낸다.")
    void sendTest() {
        ChatRequest request = chatRequest();
        when(sendChatUseCase.sendChat(request)).thenReturn(Mono.empty());

        testClient.post()
                .uri("/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);

    }
}
