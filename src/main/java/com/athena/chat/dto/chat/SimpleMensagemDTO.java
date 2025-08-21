package com.athena.chat.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleMensagemDTO {
    private Long chatId;
    private String nomeEnvio;
    private String conteudo;
}
