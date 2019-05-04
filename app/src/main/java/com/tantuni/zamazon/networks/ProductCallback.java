package com.tantuni.zamazon.networks;

import com.tantuni.zamazon.models.Product;

import java.util.ArrayList;

public interface ProductCallback<T> {
    void onSuccess(T object);
    void onError(Exception exception);
}
