package com.tantuni.zamazon.networks;

public interface ProductCallback<T> {
    void onSuccess(T object);
    void onError(Exception exception);
}
