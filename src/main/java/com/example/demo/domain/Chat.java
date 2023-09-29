package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "chat")
@Getter
public class Chat {
    @Id
    private String id;
    private ChatType chatType;
    private String sender;
    private String receiver;
    private String message;
    private LocalDateTime sendMessageAt;
}
