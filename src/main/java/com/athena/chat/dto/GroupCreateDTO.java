package com.athena.chat.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupCreateDTO {

    @NotBlank(message = "O nome do grupo é obrigatório")
    private String nome;

    private String descricao;
}
