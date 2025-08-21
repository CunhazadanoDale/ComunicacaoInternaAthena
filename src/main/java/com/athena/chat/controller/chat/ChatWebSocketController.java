package com.athena.chat.controller.chat;


import com.athena.chat.dto.chat.MensagemDTO;
import com.athena.chat.dto.chat.SimpleMensagemDTO;
import com.athena.chat.dto.mapper.MensagemMapper;
import com.athena.chat.model.chat.Mensagem;
import com.athena.chat.services.chat.ChatService;
import com.athena.chat.services.chat.MensagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chats/{chatId}/send") // Ex: /app/chats/1/send
    public String enviarMensagem(@DestinationVariable Long chatId, MensagemDTO mensagemDTO, Principal principal) {
        String nomeUsuario = (principal != null) ? principal.getName() : mensagemDTO.getRemetenteNome();

        MensagemDTO mensagemSalva = chatService.salvarMensagem(chatId, mensagemDTO, nomeUsuario);

        // Envia mensagem para todos os inscritos no /topic/chats/{chatId}
        messagingTemplate.convertAndSend("/topic/chats/" + chatId, mensagemSalva);

        SimpleMensagemDTO simpleMensagemDTO = MensagemMapper.dtoToSimpleDto(mensagemSalva);

        return HtmlUtils.htmlEscape(String.valueOf(simpleMensagemDTO));
    }
}
