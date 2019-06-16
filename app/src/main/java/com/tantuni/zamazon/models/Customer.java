package com.tantuni.zamazon.models;

import java.util.Set;

public class Customer extends User {
    private Cart cart;
    private WishList wishList;

    public Customer() {

    }

    public Customer(String id, String email, String password, String firstName, String lastName, Boolean active, Set<Role> roles, Cart cart, WishList wishList) {
        super(id, email, password, firstName, lastName, active, roles);
        this.cart = cart;
        this.wishList = wishList;
    }


}
