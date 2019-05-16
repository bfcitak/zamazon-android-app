package com.tantuni.zamazon.models;

import java.util.Set;

public class Seller extends User {

    // Constructors
    public Seller() {

    }

    public Seller(String id, String email, String firstName, String lastName, Boolean active, Set<Role> roles) {
        super(id, email, firstName, lastName, active, roles);
    }

    @Override
    public String toString() {
        return "Seller{} " + super.toString();
    }
}
