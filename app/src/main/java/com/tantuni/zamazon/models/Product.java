package com.tantuni.zamazon.models;

public class Product {
    private String id;
    private String header;
    private String description;
    private String category;
    private String subCategory;
    private Double price;
    private User seller;
    private Boolean active;
    private Integer imageId;

    // Constructors
    public Product() {

    }

    public Product(String id, String header, String description, String category, String subCategory, Double price, User seller, Boolean active) {
        this.id = id;
        this.header = header;
        this.description = description;
        this.category = category;
        this.subCategory = subCategory;
        this.price = price;
        this.seller = seller;
        this.active = active;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        active = active;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

}