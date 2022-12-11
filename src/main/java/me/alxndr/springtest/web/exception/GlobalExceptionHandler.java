package me.alxndr.springtest.web.exception;

import me.alxndr.springtest.web.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author : Alexander Choi
 * @date : 2022/12/11
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity runtimeException(RuntimeException re) {
        return new ResponseEntity(Response.error(re), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
