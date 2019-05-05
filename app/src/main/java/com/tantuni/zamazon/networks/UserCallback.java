package com.tantuni.zamazon.networks;

public interface UserCallback<T> {
    void onSuccess(T object);
    void onError(Exception exception);
}
