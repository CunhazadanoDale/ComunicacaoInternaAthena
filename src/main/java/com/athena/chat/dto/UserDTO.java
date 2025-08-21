package com.athena.chat.dto;

import com.athena.chat.model.entities.permissions.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String cargo;
    private UserRole role;
}

