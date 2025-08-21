package com.athena.chat.dto.chat;

import com.athena.chat.dto.UserDTO;
import com.athena.chat.dto.simpledto.UserSimpleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    private Long id;
    private String nome;
    private Set<UserSimpleDTO> participantes;
    private LocalDateTime criadoEm;

    public <R> ChatDTO(Long id, String nome, R collect) {
    }
}
