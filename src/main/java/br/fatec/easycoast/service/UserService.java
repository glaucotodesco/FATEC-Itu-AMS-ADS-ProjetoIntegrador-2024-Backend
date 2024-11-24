package br.fatec.easycoast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.UserRequest;
import br.fatec.easycoast.dtos.UserResponse;
import br.fatec.easycoast.entity.User;
import br.fatec.easycoast.repository.UserRepository;
import br.fatec.easycoast.mappers.UserMapper;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse cadastrarUsuario(UserRequest userRequest) {
        // Mapeamento do DTO para a Entidade
        User savedUser = userRepository.save(UserMapper.toEntity(userRequest));
        return UserMapper.toDto(savedUser);
    }
}