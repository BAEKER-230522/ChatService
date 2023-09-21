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
    public void test(@PathVariable("sender") Long sender, @PathVariable("receiver") Long receiver) {
        Flux<ChatResponse> bySenderAndReceiver = findChatUseCase.findBySenderAndReceiver(sender, receiver);
        System.out.println("bySenderAndReceiver = " + bySenderAndReceiver);
        for (Object o : bySenderAndReceiver.toIterable() ) {
            System.out.println("print mongo"+o.toString());
        }
    }
}
