package com.example.demo.adaptor.in;

import com.example.demo.TestInitUtil;
import com.example.demo.adaptor.dto.ChatRequest;
import com.example.demo.adaptor.in.web.ChatController;
import com.example.demo.application.port.in.FindChatUseCase;
import com.example.demo.application.port.in.SendChatUseCase;
import com.example.demo.global.config.RSocketConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = ChatController.class)
@Import(RSocketConfig.class)
public class ChatControllerTest {
    @Autowired
    private WebTestClient testClient;

    @MockBean
    private SendChatUseCase sendChatUseCase;

    @MockBean
    private FindChatUseCase findChatUseCase;

    @Autowired
    private RSocketRequester rSocketRequester;
    @Test
    @DisplayName("message 타입의 채팅을 보낸다.")
    void sendTest() {
        ChatRequest request = TestInitUtil.requestMessage();
        when(sendChatUseCase.sendChat(request)).thenReturn(Mono.empty());

        rSocketRequester.rsocketClient().connect();
    }
}
