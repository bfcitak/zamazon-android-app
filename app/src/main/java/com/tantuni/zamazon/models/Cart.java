package com.tantuni.zamazon.models;

import java.util.List;

public class Cart {
    private List<Product> products;
    private Payment payment;

    // Constructors
    public Cart() {

    }

    public Cart(List<Product> products, Payment payment) {
        this.products = products;
        this.payment = payment;
    }

    // Getters and Setters
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public Payment getPayment() {
        return payment;
    }
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "products=" + products +
                ", payment=" + payment +
                '}';
    }
}
