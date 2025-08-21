package com.athena.chat.services;


import com.athena.chat.dto.GroupCreateDTO;
import com.athena.chat.dto.GroupDTO;
import com.athena.chat.dto.chat.ChatDTO;
import com.athena.chat.dto.mapper.GroupMapper;
import com.athena.chat.dto.mapper.UserMapper;
import com.athena.chat.dto.simpledto.UserSimpleDTO;
import com.athena.chat.model.chat.Chat;
import com.athena.chat.model.entities.Group;
import com.athena.chat.model.entities.User;
import com.athena.chat.repositories.GroupRepository;
import com.athena.chat.repositories.UserRepository;
import com.athena.chat.services.chat.ChatService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final ChatService chatService;

    public List<GroupDTO> listarGrupos() throws IllegalAccessException {
        List<Group> grupos = groupRepository.findAll();

        if (grupos.isEmpty()) {
            throw new IllegalAccessException("Nenhum grupo criado até o momento");
        }

        List<GroupDTO> groupDTOS = grupos.stream()
                .map(GroupMapper::toDTO)
                .toList();

        return groupDTOS;
    }

    public Optional<Stream<GroupDTO>> buscarPorId(Long id) {
        Optional<Group> group = Optional.ofNullable(groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nenhum grupo encontrado")));

        Optional<Stream<GroupDTO>> grupo = Optional.ofNullable((group.stream()
                .map(GroupMapper::toDTO)));

        return grupo;
    }

    public List<GroupDTO> buscarGruposPorCriador() {
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

        User usuario = userRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário autenticado não encontrado."));

        List<Group> grupos = groupRepository.findByCriadoPor(usuario);

        return grupos.stream()
                .map(GroupMapper::toDTO)
                .toList();
    }


    public GroupDTO criarGrupo(GroupCreateDTO dto) {

        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

        User criador = userRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário autenticado não encontrado."));


        Group grupo = new Group();
        grupo.setNome(dto.getNome());
        grupo.setDescricao(dto.getDescricao());
        grupo.setCriadoPor(criador);

        UserSimpleDTO setarCriador = UserMapper.toSimpleDTO(criador);

        Chat chat = chatService.criarChat(grupo.getNome());
        chat.setGrupo(grupo);
        grupo.setChat(chat);

        Group salvo = groupRepository.save(grupo);
        GroupDTO groupDTO = GroupMapper.toDTO(salvo);


        log.info("Grupo e chat criados: Grupo={}, Chat={}, Criador={}",
                salvo.getNome(), chat.getNome(), criador.getNome());

        return groupDTO;
    }

    public GroupDTO atualizarGrupo(Long id, GroupDTO groupDTO) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Grupo não encontrado com id: " + id));

        group.setNome(groupDTO.getNome());
        group.setDescricao(groupDTO.getDescricao());

        Group atualizado = groupRepository.save(group);
        return GroupMapper.toDTO(atualizado);
    }


    @Transactional
    public GroupDTO deletarGrupo(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Grupo não encontrado"));

        groupRepository.delete(group);

        log.warn("Grupo deletado: ID={}, Nome={}", group.getId(), group.getNome());

        return GroupMapper.toDTO(group);
    }

    public GroupDTO adicionarUsuarioAoGrupo(Long groupId, Long userId) throws IllegalAccessException {
        try {
            Group grupo = groupRepository.findById(groupId)
                    .orElseThrow(() -> new IllegalArgumentException("Grupo não encontrado"));

            User usuario = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

            // Evita duplicações e sincroniza a relação
            if (!grupo.getMembros().contains(usuario)) {
                grupo.getMembros().add(usuario);
            }

            if (!usuario.getGrupos().contains(grupo)) {
                usuario.getGrupos().add(grupo);
            }

            if (grupo.getChat() != null) {
                chatService.adicionarParticipante(grupo.getChat(), usuario);
            }

            // Primeiro salva usuário
            userRepository.save(usuario);
            // Depois salva o grupo
            Group grupoAtualizado = groupRepository.save(grupo);

            log.info("Usuário {} adicionado ao grupo e ao chat {}",
                    usuario.getNome(), grupo.getChat().getNome());

            return GroupMapper.toDTO(grupoAtualizado);

        } catch (IllegalArgumentException e) {
            log.error("Erro ao adicionar usuário ao grupo: {}", e.getMessage());
            throw new IllegalAccessException("Não foi possível adicionar o usuário: " + e.getMessage());
        }
    }

    public GroupDTO removerUsuarioDoGrupo(Long groupId, Long userId) throws IllegalAccessException {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Grupo não encontrado"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (!group.getMembros().contains(user)) {
            throw new IllegalAccessException("Usuário não faz parte deste grupo");
        }

        // Remove dos dois lados da associação
        group.getMembros().remove(user);
        user.getGrupos().remove(group);

        if (group.getChat() != null) {
            chatService.removerParticipante(group.getChat(), user);
        }

        // Persistência em ordem
        userRepository.save(user);
        Group grupoAtualizado = groupRepository.save(group);

        log.info("Usuário {} removido do grupo e do chat {}", user.getEmail(), group.getChat().getNome());


        return GroupMapper.toDTO(grupoAtualizado);
    }

}
