package com.athena.chat.controller.chat;

import com.athena.chat.dto.chat.ChatDTO;
import com.athena.chat.model.chat.Chat;
import com.athena.chat.services.chat.ChatService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/{id}")
    public ResponseEntity<ChatDTO> buscarPorId(@PathVariable Long id) {
        ChatDTO chat = chatService.buscarPorId(id);

        return new ResponseEntity<>(chat, HttpStatus.FOUND);
    }

    @GetMapping
    public ResponseEntity<List<ChatDTO>> listarTodos() {
        List<ChatDTO> chat = chatService.listarTodos();

        return new ResponseEntity<>(chat, HttpStatus.OK);
    }
}
