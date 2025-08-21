package com.athena.chat.model.sql;

import com.athena.chat.model.entities.Group;
import com.athena.chat.model.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_documents")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDocumentSQL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;
    private String fileUrl; // URL para acessar o arquivo

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    private LocalDateTime uploadedAt = LocalDateTime.now();
}