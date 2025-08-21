package com.athena.chat.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NovaMensagemDTO {
    private Long chatId;
    private String conteudo;
}
