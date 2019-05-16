package com.tantuni.zamazon.models;

import java.util.Map;

public class Product {
    private String id;
    private String header;
    private String description;
    private Double price;
    private Boolean active;
    private Category category;
    private SubCategory subCategory;
    private Seller seller;
    private Map<String, String> features;

    // Constructors
    public Product() {

    }

    public Product(String id, String header, String description, Double price, Boolean active, Category category, SubCategory subCategory, Seller seller, Map<String, String> features) {
        this.id = id;
        this.header = header;
        this.description = description;
        this.price = price;
        this.active = active;
        this.category = category;
        this.subCategory = subCategory;
        this.seller = seller;
        this.features = features;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public SubCategory getSubCategory() {
        return subCategory;
    }
    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }
    public Seller getSeller() {
        return seller;
    }
    public void setSeller(Seller seller) {
        this.seller = seller;
    }
    public Map<String, String> getFeatures() {
        return features;
    }
    public void setFeatures(Map<String, String> features) {
        this.features = features;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", header='" + header + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", active=" + active +
                ", category=" + category +
                ", subCategory=" + subCategory +
                ", seller=" + seller +
                ", features=" + features +
                '}';
    }
}