package com.athena.chat.dto.mapper;

import com.athena.chat.dto.UserCreateDTO;
import com.athena.chat.dto.UserDTO;
import com.athena.chat.dto.simpledto.UserSimpleDTO;
import com.athena.chat.model.entities.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getSenha(),
                user.getCargo(),
                user.getRole()
        );
    }

    public static User toEntity(UserCreateDTO dto) {
        User user = new User();
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setSenha(dto.getSenha());
        user.setCargo(dto.getCargo());
        user.setRole(dto.getRole());
        return user;
    }

    public static UserSimpleDTO toSimpleDTO(User user) {
        return new UserSimpleDTO(user.getId(), user.getNome(), user.getEmail());
    }

    public static User toUser(UserDTO dto) {
        User user = new User();
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setSenha(dto.getSenha());
        user.setCargo(dto.getCargo());
        user.setRole(dto.getRole());
        return user;
    }

}

