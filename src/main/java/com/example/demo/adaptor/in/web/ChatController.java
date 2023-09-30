package com.example.demo.adaptor.in.web;

import com.example.demo.adaptor.dto.ChatRequest;
import com.example.demo.adaptor.dto.ChatResponse;
import com.example.demo.application.port.in.FindChatUseCase;
import com.example.demo.application.port.in.SendChatUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final SendChatUseCase sendChatUseCase;
    private final FindChatUseCase findChatUseCase;
    private final RSocketRequester rSocketRequester;
    private final List<RSocketRequester> CLIENTS = new ArrayList<>();

    @MessageMapping("chat")
    public Mono<Void> chat(@RequestBody ChatRequest chatRequest) {
        System.out.println("chatRequest = " + chatRequest);
        rSocketRequester.route("chat")
                .data(chatRequest)
                .send();
        return sendChatUseCase.sendChat(chatRequest);
    }

    @MessageMapping("test")
    public String test() {
        System.out.println("test");
        rSocketRequester.route("test")
                .data("test")
                .send();
        return "test";
    }

    @MessageMapping("show.message.{sender}.{receiver}")
    public Flux<ChatResponse> findChat(@DestinationVariable("sender") String sender, @DestinationVariable("receiver") String receiver) {
        return findChatUseCase.findBySenderAndReceiver(sender, receiver);
    }

    @ConnectMapping("connect")
    public void onConnect(RSocketRequester requester) {
        System.out.println("onConnect");
        requester.rsocket()
                .onClose()
                .doFirst(() -> {
                    CLIENTS.add(requester);
                })
                .doOnError(error -> {
                })
                .doFinally(consumer -> {
                    CLIENTS.remove(requester);
                })
                .subscribe();
    }

}
