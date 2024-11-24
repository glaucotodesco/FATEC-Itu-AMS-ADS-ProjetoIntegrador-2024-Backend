package br.fatec.easycoast.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.fatec.easycoast.dtos.UserRequest;
import br.fatec.easycoast.dtos.UserResponse;
import br.fatec.easycoast.entities.User;
import br.fatec.easycoast.mappers.UserMapper;
import br.fatec.easycoast.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse cadastrarUsuario(UserRequest userRequest) {
        // Mapeamento do DTO para a Entidade
        User savedUser = userRepository.save(UserMapper.toEntity(userRequest));
        return UserMapper.toDto(savedUser);
    }

    // Função para listar todos os usuários
    public List<UserResponse> listarUsuarios() {
        List<User> users = userRepository.findAll();
        return users.stream()
                    .map(UserMapper::toDto)
                    .collect(Collectors.toList());
    }

    // Função para resgatar usuário por ID
    public UserResponse resgatarUsuarioPorId(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.map(UserMapper::toDto).orElse(null);
    }
}
