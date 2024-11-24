package br.fatec.easycoast.mappers;

import br.fatec.easycoast.dtos.UserRequest;
import br.fatec.easycoast.dtos.UserResponse;
import br.fatec.easycoast.entities.User;

public class UserMapper {

    // Método para mapear de UserRequest para a entidade User
    public static User toEntity(UserRequest requestDTO) {
        User user = new User();
        user.setName(requestDTO.name());       
        user.setLogin(requestDTO.login());     
        user.setPassword(requestDTO.password()); 
        user.setProfile(requestDTO.profile());  
        return user;
    }

    // Método para mapear de User para UserResponse
    public static UserResponse toDto(User user) {
        return new UserResponse(
            user.getId(),                      
            user.getName(),                   
            user.getLogin(),                 
            user.getPassword(),               
            user.getProfile()                 
        );
    }
}