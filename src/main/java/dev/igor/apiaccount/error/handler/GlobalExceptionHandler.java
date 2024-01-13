package dev.igor.apiaccount.error.handler;

import dev.igor.apiaccount.error.UserNotFoundException;
import dev.igor.apiaccount.error.response.Error;
import dev.igor.apiaccount.error.response.ResponseError;
import dev.igor.apiaccount.error.response.ResponseErrorList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorList> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<Error> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(item -> {
            String fieldName = item.getField();
            String errorMessage = item.getDefaultMessage();
            errors.add(new Error(String.format("'%s' - %s", fieldName, errorMessage)));
        });
        return ResponseEntity.badRequest().body(new ResponseErrorList(
                HttpStatus.BAD_REQUEST.toString(),
                MethodArgumentNotValidException.class.getSimpleName(),
                errors
        ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseError> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.badRequest().body(
                new ResponseError(
                        HttpStatus.BAD_REQUEST.toString(),
                        ex.getMessage(),
                        UserNotFoundException.class.getSimpleName()));
    }
}
