package br.fatec.easycoast.dtos;

public record UserRequest(
     String name,
     String login,
     String password,
     UserProfile profile
) {

}
