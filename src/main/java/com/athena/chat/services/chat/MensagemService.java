package com.athena.chat.services.chat;

import com.athena.chat.dto.chat.MensagemDTO;
import com.athena.chat.dto.mapper.MensagemMapper;
import com.athena.chat.model.chat.Chat;
import com.athena.chat.model.chat.Mensagem;
import com.athena.chat.model.entities.User;
import com.athena.chat.repositories.UserRepository;
import com.athena.chat.repositories.chat.ChatRepository;
import com.athena.chat.repositories.chat.MensagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MensagemService {

    private final MensagemRepository mensagemRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    public MensagemDTO salvarMensagem(MensagemDTO dto) {
        User remetente = userRepository.findById(dto.getRemetenteId())
                .orElseThrow(() -> new IllegalArgumentException("Remetente não encontrado"));

        Chat chat = chatRepository.findById(dto.getChatId())
                .orElseThrow(() -> new IllegalArgumentException("Chat não encontrado"));

        Mensagem mensagem = MensagemMapper.fromDTO(dto, remetente, chat);

        Mensagem salva = mensagemRepository.save(mensagem);

        return MensagemMapper.toDTO(salva, remetente.getId());
    }

    public List<MensagemDTO> listarMensagens(Long chatId, Long userId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat não encontrado"));

        return chat.getMensagens().stream()
                .map(m -> MensagemMapper.toDTO(m, userId))
                .collect(Collectors.toList());
    }


    public void marcarComoLida(Long mensagemId, Long usuarioId) {
        Mensagem mensagem = mensagemRepository.findById(mensagemId)
                .orElseThrow(() -> new RuntimeException("Mensagem não encontrada"));

        User usuario = userRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        mensagem.getUsuariosQueLeram().add(usuario);
        mensagemRepository.save(mensagem);
    }



}

