package com.athena.chat.controller.chat;

import com.athena.chat.dto.chat.MensagemDTO;
import com.athena.chat.model.entities.User;
import com.athena.chat.services.LoginService;
import com.athena.chat.services.chat.MensagemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensagens")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class MensagemController {

    private final MensagemService mensagemService;

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<MensagemDTO>> listarMensagens(
            @PathVariable Long chatId,
            @AuthenticationPrincipal User userDetails) {

        Long userId = userDetails.getId();
        List<MensagemDTO> mensagens = mensagemService.listarMensagens(chatId, userId);

        return new ResponseEntity<>(mensagens, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<MensagemDTO> enviarMensagem(
            @RequestBody MensagemDTO dto,
            @AuthenticationPrincipal User userDetails) {

        dto.setRemetenteId(userDetails.getId());
        MensagemDTO mensagemDTO = mensagemService.salvarMensagem(dto);

        return new ResponseEntity<>(mensagemDTO, HttpStatus.OK);
    }

}

