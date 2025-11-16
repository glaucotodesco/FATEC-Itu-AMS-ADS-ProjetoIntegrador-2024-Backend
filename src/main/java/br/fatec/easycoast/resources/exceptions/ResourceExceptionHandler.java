package br.fatec.easycoast.resources.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.fatec.easycoast.services.exceptions.DatabaseException;
import br.fatec.easycoast.services.exceptions.EntityGoneException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {
    //It will run when a validation fails, and return an error for the request of the API
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validationException(MethodArgumentNotValidException exception, HttpServletRequest request){
        //Create a validation error
        ValidationError error = new ValidationError();
        
        //Get the status code
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        //Set the error text
        error.setError("Validation Error");
        //Set the error messsage
        error.setMessage(exception.getMessage());
        //Set the path of the error
        error.setPath(request.getRequestURI());
        //Set the status code
        error.setStatus(status.value());
        //Set the time when it happened
        error.setTimeStamp(Instant.now());

        //Get the result of the exception
        exception.getBindingResult()
                 //Get the validation erros
                 .getFieldErrors()
                 //For each error, put the messagem in the validation error
                 .forEach(e -> error.addError(e.getDefaultMessage()));

        //Return the validation error in the body of the response
        return ResponseEntity.status(status).body(error);
    }

    //It will run when there is a DatabaseException, and return an error for the request of the API
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> databaseException(DatabaseException exception, HttpServletRequest request){
        //Create a standard error
        StandardError error = new StandardError();
        
        //Get the status code
        HttpStatus status = HttpStatus.BAD_REQUEST;

        //Set the error text
        error.setError("Database Error");
        //Set the error messsage
        error.setMessage(exception.getMessage());
        //Set the path of the error
        error.setPath(request.getRequestURI());
        //Set the status code
        error.setStatus(status.value());
        //Set the time when it happened
        error.setTimeStamp(Instant.now());

        //Return the standard error in the body of the response
        return ResponseEntity.status(status).body(error);
    }

    //It will run when there is a EntityNotFoundException, and return an error for the request of the API
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFoundException(EntityNotFoundException exception, HttpServletRequest request){
        //Create a standard error
        StandardError error = new StandardError();
        
        //Get the status code
        HttpStatus status = HttpStatus.NOT_FOUND;

        //Set the error text
        error.setError("Resource not found");
        //Set the error messsage
        error.setMessage(exception.getMessage());
        //Set the path of the error
        error.setPath(request.getRequestURI());
        //Set the status code
        error.setStatus(status.value());
        //Set the time when it happened
        error.setTimeStamp(Instant.now());

        //Return the standard error in the body of the response
        return ResponseEntity.status(status).body(error);
    }

    //It will run when there is an illegalArgumentException, and return an error for the request of the API
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> illegalArgumentException(IllegalArgumentException exception, HttpServletRequest request){
        //Create a standard error
        StandardError error = new StandardError();
        
        //Get the status code
        HttpStatus status = HttpStatus.BAD_REQUEST;

        //Set the error text
        error.setError("Bad request");
        //Set the error messsage
        error.setMessage(exception.getMessage());
        //Set the path of the error
        error.setPath(request.getRequestURI());
        //Set the status code
        error.setStatus(status.value());
        //Set the time when it happened
        error.setTimeStamp(Instant.now());

        //Return the standard error in the body of the response
        return ResponseEntity.status(status).body(error);
    }

    //It will run when there is an EntityGoneException, and return an error for the request of the API
    @ExceptionHandler(EntityGoneException.class)
    public ResponseEntity<StandardError> entityGoneException(EntityGoneException exception, HttpServletRequest request){
        //Create a standard error
        StandardError error = new StandardError();
        
        //Get the status code
        HttpStatus status = HttpStatus.GONE;

        //Set the error text
        error.setError("Gone");
        //Set the error messsage
        error.setMessage(exception.getMessage());
        //Set the path of the error
        error.setPath(request.getRequestURI());
        //Set the status code
        error.setStatus(status.value());
        //Set the time when it happened
        error.setTimeStamp(Instant.now());

        //Return the standard error in the body of the response
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<StandardError> handleIllegalState(IllegalStateException exception, HttpServletRequest request) {
        StandardError error = new StandardError();
        HttpStatus status = HttpStatus.CONFLICT;

        error.setError("Operation not permitted due to current state");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        error.setStatus(status.value());
        error.setTimeStamp(Instant.now());

        return ResponseEntity.status(status).body(error);
    }

}