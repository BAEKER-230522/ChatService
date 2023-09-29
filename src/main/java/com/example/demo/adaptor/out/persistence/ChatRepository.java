package com.example.demo.adaptor.out.persistence;

import com.example.demo.domain.Chat;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {
    Flux<Chat> findBySenderAndReceiver(String sender, String receiver);
}
