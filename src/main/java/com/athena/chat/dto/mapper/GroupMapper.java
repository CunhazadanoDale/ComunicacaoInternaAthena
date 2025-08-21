package com.athena.chat.dto.mapper;


import com.athena.chat.dto.GroupDTO;
import com.athena.chat.dto.simpledto.GroupSimpleDTO;
import com.athena.chat.dto.simpledto.UserSimpleDTO;
import com.athena.chat.model.entities.Group;
import com.athena.chat.model.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupMapper {

    public static GroupDTO toDTO(Group group) {
        List<UserSimpleDTO> membrosDTO = group.getMembros().stream()
                .map(UserMapper::toSimpleDTO)
                .collect(Collectors.toList());

        return new GroupDTO(
                group.getId(),
                group.getNome(),
                group.getDescricao(),
                group.getCriadoPor() != null ? group.getCriadoPor().getNome() : null,
                group.getCriadoEm(),
                membrosDTO
        );
    }

    public static GroupSimpleDTO toSimpleDTO(Group group) {
        return new GroupSimpleDTO(
                group.getId(),
                group.getNome(),
                group.getDescricao()
        );
    }

    public static Group toGroupFromDTO(GroupDTO dto, User criador, Set<User> membros) {
        Group group = new Group();
        group.setId(dto.getId());
        group.setNome(dto.getNome());
        group.setDescricao(dto.getDescricao());
        group.setCriadoPor(criador);
        group.setMembros(membros);
        group.setCriadoEm(dto.getCriadoEm());

        return group;
    }
}
