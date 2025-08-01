package org.example.Exception;


import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLTransientConnectionException;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExeptionHandler {

    @ExceptionHandler(UsernameNotFound.class)
    public ResponseEntity<ErrorRes> handleUsernameNotFound(UsernameNotFound ex) {
        ErrorRes error = new ErrorRes(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorRes> handleExpiredJwtException(ExpiredJwtException ex) {
        ErrorRes error = new ErrorRes(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResembleEmailFound.class)
    public ResponseEntity<ErrorRes> handleResembleDataException(ResembleEmailFound ex){
        ErrorRes error = new ErrorRes(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TooManyRequest.class)
    public ResponseEntity<ErrorRes> handleTooManyRequestSent(TooManyRequest ex){
        log.error("Database connection timeout", ex);
        ErrorRes error = new ErrorRes(
                HttpStatus.TOO_MANY_REQUESTS.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.TOO_MANY_REQUESTS);
    }

    public String formatFieldError(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRes> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(this::formatFieldError).toList();
        ErrorRes error = new ErrorRes(
                HttpStatus.BAD_REQUEST.value(),
                errors,
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLTransientConnectionException.class)
    public ResponseEntity<ErrorRes> handleSQLTransientConnectionException(SQLTransientConnectionException ex){
        ErrorRes error = new ErrorRes(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Database is currently busy or unavailable. Please try again later.",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

