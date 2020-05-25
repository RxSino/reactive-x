package com.example.reactivex.laihuola;

public class LoginResp<T> {

    public T data;
    public String errorMessage;
    public String errorCode;
    public boolean success;

    public LoginResp() {
    }

    public LoginResp(T data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public LoginResp(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public static <T> LoginResp<T> success(T t) {
        return new LoginResp<>(t, true);
    }

    public static <T> LoginResp<T> error() {
        return new LoginResp<>("-1", "error");
    }

}
