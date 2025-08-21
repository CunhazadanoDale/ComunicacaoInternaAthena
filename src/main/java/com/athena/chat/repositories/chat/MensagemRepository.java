package com.athena.chat.repositories.chat;

import com.athena.chat.model.chat.Mensagem;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

    List<Mensagem> findByChatIdOrderByEnviadoEmAsc(Long chatId);
    //Page<Mensagem> findByChatIdOrderByEnviadoEmDesc(Long chatId, Pageable pageable);

}
