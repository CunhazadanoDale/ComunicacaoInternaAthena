package com.athena.chat.controller;

import com.athena.chat.dto.GroupCreateDTO;
import com.athena.chat.dto.GroupDTO;
import com.athena.chat.dto.mapper.GroupMapper;
import com.athena.chat.dto.simpledto.UserSimpleDTO;
import com.athena.chat.model.entities.Group;
import com.athena.chat.services.GroupService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/grupos")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<List<GroupDTO>> listarGrupos() {
        try {
            List<GroupDTO> grupos = groupService.listarGrupos();

            return new ResponseEntity<>(grupos, HttpStatus.OK);

        } catch (IllegalAccessException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/meus-grupos")
    public ResponseEntity<List<GroupDTO>> listarGruposDoCriador() {
        List<GroupDTO> grupos = groupService.buscarGruposPorCriador();
        return new ResponseEntity<>(grupos, HttpStatus.OK);
    }

    @GetMapping("/{id}/usuarios")
    public ResponseEntity<GroupDTO> buscarPorId(@PathVariable Long id) {
        try {
            Optional<Stream<GroupDTO>> grupoOpt = groupService.buscarPorId(id);

            return grupoOpt.map(stream -> stream.findFirst()
                            .map(ResponseEntity::ok)
                            .orElse(ResponseEntity.notFound().build()))
                    .orElse(ResponseEntity.notFound().build());

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<GroupDTO> criarGrupo(@RequestBody @Valid GroupCreateDTO dto) {
        GroupDTO grupoCriado = groupService.criarGrupo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(grupoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDTO> atualizarGrupo(@PathVariable Long id, @RequestBody GroupDTO groupDTO) {
        try {
            GroupDTO atualizado = groupService.atualizarGrupo(id, groupDTO);
            return ResponseEntity.ok(atualizado);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GroupDTO> deletarGrupo(@PathVariable Long id) {
        try {
            GroupDTO deletado = groupService.deletarGrupo(id);
            return ResponseEntity.ok(deletado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{groupId}/usuarios/{userId}")
    public ResponseEntity<GroupDTO> adicionarUsuarioAoGrupo(@PathVariable Long groupId, @PathVariable Long userId) {
        try {
            GroupDTO grupoAtualizado = groupService.adicionarUsuarioAoGrupo(groupId, userId);
            return ResponseEntity.ok(grupoAtualizado);
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{groupId}/usuarios/{userId}")
    public ResponseEntity<GroupDTO> removerUsuarioDoGrupo(@PathVariable Long groupId, @PathVariable Long userId) {
        try {
            GroupDTO grupoAtualizado = groupService.removerUsuarioDoGrupo(groupId, userId);
            return ResponseEntity.ok(grupoAtualizado);
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/{id}/usuarios-por-grupo")
    public ResponseEntity<List<UserSimpleDTO>> listarUsuariosPorGrupo(@PathVariable Long id) {
        try {
            Optional<Stream<GroupDTO>> grupoOpt = groupService.buscarPorId(id);

            return grupoOpt.map(stream -> stream.findFirst()
                            .map(grupoDTO -> ResponseEntity.ok(grupoDTO.getMembros()))
                            .orElse(ResponseEntity.notFound().build()))
                    .orElse(ResponseEntity.notFound().build());

        } catch (Exception e) {

            return ResponseEntity.badRequest().build();

        }
    }


}
