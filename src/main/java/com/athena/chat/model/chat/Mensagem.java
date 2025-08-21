package com.athena.chat.model.chat;

import com.athena.chat.model.entities.Group;
import com.athena.chat.model.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "mensagens")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remetente_id")
    private User remetente;

    @Column(columnDefinition = "TEXT")
    private String conteudo;

    private LocalDateTime enviadoEm = LocalDateTime.now();

    @ManyToMany
    @JoinTable(
            name = "mensagem_usuario_lida",
            joinColumns = @JoinColumn(name = "mensagem_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<User> usuariosQueLeram = new HashSet<>();

}

