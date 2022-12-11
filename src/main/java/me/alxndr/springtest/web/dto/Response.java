package me.alxndr.springtest.web.dto;

import lombok.Builder;
import lombok.Getter;

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

    @Getter
    public static class Message {
        public static final String success = "Success";
    }

    @Getter
    public class Code {
        public static final int SUCCESS = 1;
        public static final int FAIL = -1;

    }
}
