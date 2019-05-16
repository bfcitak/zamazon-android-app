package com.tantuni.zamazon.models;

public class Role {
    private String id;
    private String role;

    public Role() {

    }

    public Role(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
