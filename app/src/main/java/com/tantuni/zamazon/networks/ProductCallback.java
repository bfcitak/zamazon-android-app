package com.tantuni.zamazon.networks;

public interface ProductCallback<T> {
    void onSuccess(T object, String message);
    void onError(Exception exception);
}
