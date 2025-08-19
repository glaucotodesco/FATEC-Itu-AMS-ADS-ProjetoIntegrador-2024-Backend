package br.fatec.easycoast.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

//Error when a validators are failed
public class ValidationError extends StandardError {
    //List of every validator that failed
    private List<String> errors = new ArrayList<>();

    public void addError(String error){
        this.errors.add(error);
    }

    public List<String> getErros(){
        return errors;
    }
}
