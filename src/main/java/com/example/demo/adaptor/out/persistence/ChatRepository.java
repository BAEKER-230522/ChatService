package com.example.demo.adaptor.out.persistence;

import com.example.demo.domain.Chat;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {
    Flux<Chat> findBySenderAndReceiver(Long sender, Long receiver);
}
