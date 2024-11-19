package br.fatec.easycoast.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.easycoast.dtos.UserRequestDTO;
import br.fatec.easycoast.dtos.UserResponseDTO;
import br.fatec.easycoast.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> cadastrarUsuario(@RequestBody UserRequestDTO userRequest) {
        UserResponseDTO userResponse = userService.cadastrarUsuario(userRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(userResponse);
    }
}