package com.athena.chat.dto.mapper;

import com.athena.chat.dto.chat.MensagemDTO;
import com.athena.chat.dto.chat.SimpleMensagemDTO;
import com.athena.chat.model.chat.Chat;
import com.athena.chat.model.chat.Mensagem;
import com.athena.chat.model.entities.User;

import java.time.LocalDateTime;

public class MensagemMapper {

    public static MensagemDTO toDTO(Mensagem mensagem, Long userId) {
        boolean lida = mensagem.getUsuariosQueLeram().stream()
                .anyMatch(u -> u.getId().equals(userId));

        return new MensagemDTO(
                mensagem.getId(),
                mensagem.getChat().getId(),
                mensagem.getRemetente().getId(),
                mensagem.getRemetente().getNome(),
                mensagem.getConteudo(),
                mensagem.getEnviadoEm(),
                lida
        );
    }

    public static Mensagem fromDTO(MensagemDTO dto, User remetente, Chat chat) {
        Mensagem mensagem = new Mensagem();
        mensagem.setChat(chat);
        mensagem.setRemetente(remetente);
        mensagem.setConteudo(dto.getConteudo());
        mensagem.setEnviadoEm(LocalDateTime.now());
        return mensagem;
    }

    public static SimpleMensagemDTO dtoToSimpleDto(MensagemDTO mensagem) {
        SimpleMensagemDTO simpleMensagemDTO = new SimpleMensagemDTO();
        simpleMensagemDTO.setChatId(mensagem.getId());
        simpleMensagemDTO.setNomeEnvio(mensagem.getRemetenteNome());
        simpleMensagemDTO.setConteudo(mensagem.getConteudo());
        return simpleMensagemDTO;
    }
}

