package org.example.Exception;


import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
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
        ErrorRes error = new ErrorRes(
                HttpStatus.TOO_MANY_REQUESTS.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRes> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ErrorRes error = new ErrorRes(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

