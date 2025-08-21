package com.athena.chat.services;

import com.athena.chat.dto.UserDTO;
import com.athena.chat.dto.mapper.UserMapper;
import com.athena.chat.dto.simpledto.GroupSimpleDTO;
import com.athena.chat.dto.simpledto.UserSimpleDTO;
import com.athena.chat.model.entities.Group;
import com.athena.chat.model.entities.User;
import com.athena.chat.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserSimpleDTO> listarUsuarios() throws IllegalAccessException {
        List<User> usuarios = userRepository.findAll();

        if(usuarios.isEmpty()) {
            throw new IllegalAccessException("Nenhum usuario criado até o momento");
        }

        return usuarios.stream().map(usuario -> {
            UserSimpleDTO dto = new UserSimpleDTO();
            dto.setId(usuario.getId());
            dto.setNome(usuario.getNome());
            dto.setEmail(usuario.getEmail());
            
            return dto;
        }).toList();
    }

    public Optional<UserDTO> buscarPorId(Long id) {
        User userEncontrado = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        return Optional.of(UserMapper.toDTO(userEncontrado));
    }

    public List<GroupSimpleDTO> listarGruposDoUsuario(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Set<Group> grupos = user.getGrupos();

        return grupos.stream()
                .map(grupo -> new GroupSimpleDTO(grupo.getId(), grupo.getNome(), grupo.getDescricao()))
                .toList();
    }



}
