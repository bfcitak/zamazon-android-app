package com.tantuni.zamazon.models;

import java.util.Set;

public class Cart {
    private Set<Product> products;
    private Payment payment;

    // Constructors
    public Cart() {

    }

    public Cart(Set<Product> products, Payment payment) {
        this.products = products;
        this.payment = payment;
    }

    // Getters and Setters
    public Set<Product> getProducts() {
        return products;
    }
    public void setProducts(Set<Product> products) {
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
