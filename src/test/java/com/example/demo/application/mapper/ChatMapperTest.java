package com.example.demo.application.mapper;

import com.example.demo.adaptor.dto.ChatRequest;
import com.example.demo.domain.Chat;
import com.example.demo.domain.ChatType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ChatMapperTest {
    @InjectMocks
    ChatMapper chatMapper;

    ChatRequest chatRequest(Long sender, Long receiver, String message, ChatType chatType) {
        return new ChatRequest(sender, receiver, message, chatType);
    }

    @Test
    @DisplayName("채팅방 나가기 로 변환")
    void toQuit() {
        ChatRequest request = chatRequest(1L, 2L, "나감", ChatType.OUT);
        Chat chat = chatMapper.toChat(request);


        Assertions.assertEquals(chat.getSender(), request.sender());
        Assertions.assertEquals(chat.getReceiver(), request.receiver());
        Assertions.assertEquals(chat.getMessage(), request.sender() + "님이 나갔습니다.");
        Assertions.assertEquals(chat.getChatType(), request.chatType());
    }

    @Test
    @DisplayName("채팅방 입장 으로 변환")
    void toEnter() {
        ChatRequest request = chatRequest(1L, 2L, "입장", ChatType.IN);
        Chat chat = chatMapper.toChat(request);

        Assertions.assertEquals(chat.getSender(), request.sender());
        Assertions.assertEquals(chat.getReceiver(), request.receiver());
        Assertions.assertEquals(chat.getMessage(), request.sender() + "님이 대화를 시작하였습니다.");
        Assertions.assertEquals(chat.getChatType(), request.chatType());
    }

    @Test
    @DisplayName("대화 메시지 변환")
    void toMessage() {
        ChatRequest request = chatRequest(1L, 2L, "메시지", ChatType.MESSAGE);
        Chat chat = chatMapper.toChat(request);

        Assertions.assertEquals(chat.getSender(), request.sender());
        Assertions.assertEquals(chat.getReceiver(), request.receiver());
        Assertions.assertEquals(chat.getMessage(), request.message());
        Assertions.assertEquals(chat.getChatType(), request.chatType());
    }
}
