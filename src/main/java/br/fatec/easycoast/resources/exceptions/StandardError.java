package br.fatec.easycoast.resources.exceptions;

import java.time.Instant;

//Error that will be on response
public class StandardError {
    //Whe the error happened
    private Instant timeStamp;
    //Status code error
    private Integer status;
    //The error that happened
    private String error;
    //The message of the error
    private String message;
    //The path of the file
    private String path;

    public Instant getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }    
}
