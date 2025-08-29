package br.fatec.easycoast.services.exceptions;

public class EntityGoneException extends RuntimeException{
    public EntityGoneException(String message){
        super(message); 
    }
    
}
