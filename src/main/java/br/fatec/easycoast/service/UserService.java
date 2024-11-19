package br.fatec.easycoast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.UserRequestDTO;
import br.fatec.easycoast.dtos.UserResponseDTO;
import br.fatec.easycoast.entity.User;
import br.fatec.easycoast.repository.UserRepository;



@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO cadastrarUsuario(UserRequestDTO userRequest) {
        User user = new User();
        user.setNome(userRequest.getNome());
        user.setLogin(userRequest.getLogin());
        user.setSenha(userRequest.getSenha());
        user.setPerfil(userRequest.getPerfil());

        User savedUser  = userRepository.save(user);

        UserResponseDTO response = new UserResponseDTO();
        response.setId(savedUser .getId());
        response.setNome(savedUser .getNome());
        response.setLogin(savedUser .getLogin());
        response.setPerfil(savedUser .getPerfil());

        return response;
    }
}