package org.example.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExeptionHanlder {

    @ExceptionHandler(UsernameNotFound.class)
    public ResponseEntity<ErrorRes> handleUsernameNotFound(UsernameNotFound ex) {
        ErrorRes error = new ErrorRes(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
