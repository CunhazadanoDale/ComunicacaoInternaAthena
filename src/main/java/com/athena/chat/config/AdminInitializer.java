package com.athena.chat.config;

import com.athena.chat.model.entities.Group;
import com.athena.chat.model.entities.User;
import com.athena.chat.model.entities.permissions.UserRole;
import com.athena.chat.repositories.GroupRepository;
import com.athena.chat.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdmin() {
        return args -> {
            if (userRepository.findByEmail("admin@athena.com").isEmpty()) {
                User admin = new User();
                admin.setNome("Administrador");
                admin.setEmail("admin@athena.com");
                admin.setSenha(passwordEncoder.encode("admin123"));
                admin.setRole(UserRole.ADMIN);

                userRepository.save(admin);
                System.out.println("Usuário ADMIN inicial criado com sucesso.");
            }
        };
    }

    @Bean
    public CommandLineRunner initGruposPadroes(GroupRepository groupRepository, UserRepository userRepository) {
        return args -> {
            String[] setores = {"CONTABILIDADE", "FINANCEIRO", "TECNOLOGIA DA INFORMAÇÃO",
                    "MARKETING", "RECEPÇÃO"};

            for (String setor : setores) {
                if (groupRepository.findByNomeIgnoreCase(setor).isEmpty()) {
                    Group grupo = new Group();
                    grupo.setNome(setor);
                    grupo.setDescricao("Grupo padrão do setor " + setor.toLowerCase());
                    groupRepository.save(grupo);
                }
            }
        };
    }



}
