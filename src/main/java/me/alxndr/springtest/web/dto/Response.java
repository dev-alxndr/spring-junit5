package me.alxndr.springtest.web.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;

/**
 * @author : Alexander Choi
 * @date : 2022/12/11
 */
@Getter
public class Response<T> {

    private int code;
    private String message;
    private T body;

    @Builder
    public Response(final int code, final String message, final T body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }


    public static<T> Response<T> OK(T body) {
        return Response.<T>builder()
                .code(Code.SUCCESS)
                .message(Message.SUCCESS)
                .body(body)
                .build();
    }


    public static Response error(BindingResult bindingResult) {
        final HashMap<Object, Object> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return Response.builder()
                .code(Code.FAIL)
                .message(Message.FAIL)
                .body(errors)
                .build();
    }

    public static Response error(final RuntimeException re) {

        return Response.builder()
                .code(Code.FAIL)
                .message(Message.FAIL)
                .body(re.getMessage())
                .build();
    }

    @Getter
    public static class Message {
        public static final String SUCCESS = "Success";
        public static final String FAIL = "Fail";
    }

    @Getter
    public static class Code {
        public static final int SUCCESS = 1;
        public static final int FAIL = -1;

    }
}
