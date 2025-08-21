package com.athena.chat.services;

import com.athena.chat.config.security.LoginResponseDTO;
import com.athena.chat.config.security.TokenService;
import com.athena.chat.dto.UserCreateDTO;
import com.athena.chat.dto.UserDTO;
import com.athena.chat.dto.mapper.UserMapper;
import com.athena.chat.dto.simpledto.AuthenticationDTO;
import com.athena.chat.model.entities.User;
import com.athena.chat.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public LoginResponseDTO login(AuthenticationDTO data) {
        System.out.println("Senha banco: " + userRepository.findByEmail(data.email()).get().getSenha());
        System.out.println("Senha digitada: " + data.senha());
        System.out.println("Senha confere? " + new BCryptPasswordEncoder().matches(data.senha(), userRepository.findByEmail(data.email()).get().getSenha()));


        var login = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = authenticationManager.authenticate(login);

        var token = tokenService.generateToken((User) auth.getPrincipal());


        return new LoginResponseDTO(token);
    }

    public User registrar(UserCreateDTO data) {
        if (this.userRepository.findByEmail(data.getEmail()).isPresent()) {
            throw new IllegalArgumentException("E-mail já está em uso.");
        }

        User registro = UserMapper.toEntity(data);

        String encryptedPassword = new BCryptPasswordEncoder().encode(registro.getSenha());
        User user = new User(data.getNome(), data.getEmail(), encryptedPassword,
                data.getCargo(), data.getRole());

        return this.userRepository.save(user);
    }

    public UserDTO atualizarUsuario(Long id, UserDTO userDTO) {
        User savedUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        User user = UserMapper.toUser(userDTO);
        user.setId(id);
        if (userDTO.getSenha() != null && !userDTO.getSenha().isBlank()) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.getSenha());
            user.setSenha(encryptedPassword);
        }
        savedUser = userRepository.save(user);

        return UserMapper.toDTO(savedUser);

    }

    public UserDTO deletarUsuario(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        userRepository.delete(user);

        return UserMapper.toDTO(user);
    }




}
