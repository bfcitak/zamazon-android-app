package com.tantuni.zamazon.models;

import java.util.List;

public class WishList {
    private List<Product> products;

    // Constructors
    public WishList() {

    }

    public WishList(List<Product> products) {
        this.products = products;
    }

    // Getters and setters
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "WishList{" +
                "products=" + products +
                '}';
    }

}
