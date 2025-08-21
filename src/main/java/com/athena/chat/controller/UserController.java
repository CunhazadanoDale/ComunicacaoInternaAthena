package com.athena.chat.controller;

import com.athena.chat.dto.UserDTO;
import com.athena.chat.dto.simpledto.GroupSimpleDTO;
import com.athena.chat.dto.simpledto.UserSimpleDTO;
import com.athena.chat.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserSimpleDTO>> listarUsuarios() throws IllegalAccessException {
        List<UserSimpleDTO> usuarios = userService.listarUsuarios();

        return new ResponseEntity<>(usuarios, HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserDTO>> buscarPorId(@PathVariable Long id) {
        Optional<UserDTO> userDTO = userService.buscarPorId(id);

        return new ResponseEntity<>(userDTO, HttpStatus.FOUND);
    }

    @GetMapping("/grupos/{userid}")
    public ResponseEntity<List<GroupSimpleDTO>> listarGruposDoUsuario(@PathVariable Long userid) {
        List<GroupSimpleDTO> gruposUsuario = userService.listarGruposDoUsuario(userid);

        return new ResponseEntity<>(gruposUsuario, HttpStatus.FOUND);
    }

}
