package com.athena.chat.controller;

import com.athena.chat.config.security.LoginResponseDTO;
import com.athena.chat.dto.UserCreateDTO;
import com.athena.chat.dto.UserDTO;
import com.athena.chat.dto.simpledto.AuthenticationDTO;
import com.athena.chat.model.entities.User;
import com.athena.chat.services.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        LoginResponseDTO logar = loginService.login(data);

        return new ResponseEntity<>(logar, HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody @Valid UserCreateDTO data) {
        User registrar = loginService.registrar(data);

        return new ResponseEntity<>(registrar, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> atualizarUsuario(@PathVariable Long id,
                                                    @RequestBody @Valid UserDTO userDTO) {
        UserDTO user = loginService.atualizarUsuario(id, userDTO);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserDTO> deletarUsuario(@PathVariable Long id) {
        UserDTO deletarUsuario = loginService.deletarUsuario(id);

        return new ResponseEntity<>(deletarUsuario, HttpStatus.OK);
    }


}
