package com.tantuni.zamazon.models;

import java.util.Set;

public class Admin extends User {

    // Constructors
    public Admin() {

    }

    public Admin(String id, String email, String password, String firstName, String lastName, Boolean active, Set<Role> roles) {
        super(id, email, password, firstName, lastName, active, roles);
    }

    @Override
    public String toString() {
        return "Admin{} " + super.toString();
    }
}
