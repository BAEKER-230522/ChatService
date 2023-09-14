package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "chat")
public class Chat {
    @Id
    private String id;
    private ChatType chatType;
    private Long sender;
    private Long receiver;
    private String message;
    private LocalDateTime sendMessageAt;
}
