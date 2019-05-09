package com.tantuni.zamazon.models;

import java.util.Set;

public class Customer extends User {
    private Cart cart;

    public Customer() {

    }

    public Customer(String id, String email, String firstName, String lastName, Boolean active, Set<Role> roles, Cart cart) {
        super(id, email, firstName, lastName, active, roles);
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cart=" + cart +
                "} " + super.toString();
    }
}
