package com.example.demo.adaptor.in.web;

import com.example.demo.adaptor.dto.ChatResponse;
import com.example.demo.application.port.in.FindChatUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class TempController {
    private final FindChatUseCase findChatUseCase;
    @GetMapping("/test/{sender}/{receiver}")
    public Flux<ChatResponse> test(@PathVariable("sender") String sender, @PathVariable("receiver") String receiver) {
        return findChatUseCase.findBySenderAndReceiver(sender, receiver);
    }
}
