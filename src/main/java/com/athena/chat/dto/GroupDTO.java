package com.athena.chat.dto;


import com.athena.chat.dto.simpledto.UserSimpleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String criadoPor; // nome do criador
    private LocalDateTime criadoEm;
    private List<UserSimpleDTO> membros;
}
