package br.fatec.easycoast.dtos;

public record UserResponse( 
     Long id,
     String name,
     String login,
     String password,
     UserProfile profile ) {

}
