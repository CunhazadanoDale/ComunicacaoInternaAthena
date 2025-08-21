package com.athena.chat.dto.mapper;

import com.athena.chat.dto.chat.ChatCreateDTO;
import com.athena.chat.dto.chat.ChatDTO;
import com.athena.chat.model.chat.Chat;
import com.athena.chat.model.entities.User;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class ChatMapper {

    public static ChatDTO toDTO(Chat chat) {
        return new ChatDTO(
                chat.getId(),
                chat.getNome(),
                chat.getParticipantes().stream()  // Stream<User>
                        .map(UserMapper::toSimpleDTO)             // Extrai apenas o ID (Stream<Long>)
                        .collect(Collectors.toSet()), // Coleta em um Set<Long>
                chat.getCriadoEm()
        );
    }


    public static Chat toEntity(ChatCreateDTO dto) {
        Chat chat = new Chat();
        chat.setNome(dto.getNome());
        // participantes serão setados no GroupService

        return chat;
    }
}
