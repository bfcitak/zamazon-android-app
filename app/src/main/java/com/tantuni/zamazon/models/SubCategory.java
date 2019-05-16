package com.tantuni.zamazon.models;

public class SubCategory {
    private String id;
    private String name;
    private Category parentCategory;

    // Constructors
    public SubCategory() {

    }

    public SubCategory(String id, String name, Category parentCategory) {
        this.id = id;
        this.name = name;
        this.parentCategory = parentCategory;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Category getParentCategory() {
        return parentCategory;
    }
    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
