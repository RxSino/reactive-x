package com.example.reactivex.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResp<T> {

    private int code;
    private String message;
    private T data;

    public BaseResp(T data) {
        this.data = data;
        this.code = 0;
        this.message = "ok";
    }

    public BaseResp(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> BaseResp<T> success(T data) {
        return new BaseResp<>(data);
    }

    public static <T> BaseResp<T> error() {
        return new BaseResp<>(-1, "系统错误");
    }

    public static <T> BaseResp<T> notFound() {
        return new BaseResp<>(404, "not found");
    }

}
