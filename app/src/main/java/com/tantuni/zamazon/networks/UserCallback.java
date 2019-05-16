package com.tantuni.zamazon.networks;

public interface UserCallback<T> {
    void onSuccess(T object, String message);
    void onError(Exception exception);
}
