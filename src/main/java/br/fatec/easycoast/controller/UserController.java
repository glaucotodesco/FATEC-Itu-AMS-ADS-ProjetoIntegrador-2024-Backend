package br.fatec.easycoast.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.fatec.easycoast.dtos.UserRequestDTO;
import br.fatec.easycoast.dtos.UserResponseDTO;
import br.fatec.easycoast.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> cadastrarUsuario(@RequestBody UserRequestDTO userRequest) {
        UserResponseDTO userResponse = userService.cadastrarUsuario(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }
}