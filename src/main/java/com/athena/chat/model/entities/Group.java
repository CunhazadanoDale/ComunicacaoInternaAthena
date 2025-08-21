package com.athena.chat.model.entities;

import com.athena.chat.model.chat.Chat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "grupos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nome;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "criado_por")
    private User criadoPor;

    @ManyToMany
    @JoinTable(
            name = "grupo_usuario",
            joinColumns = @JoinColumn(name = "grupo_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<User> membros = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id")
    @JsonIgnore
    private Chat chat;


    private LocalDateTime criadoEm = LocalDateTime.now();
}

