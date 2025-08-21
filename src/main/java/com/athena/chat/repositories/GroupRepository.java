package com.athena.chat.repositories;

import com.athena.chat.model.entities.Group;
import com.athena.chat.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByCriadoPor(User criadoPor);

    Collection<Object> findByNomeIgnoreCase(String setor);
}
