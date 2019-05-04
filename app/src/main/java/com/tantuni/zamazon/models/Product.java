package com.tantuni.zamazon.models;

public class Product {
    private String id;
    private String header;
    private String description;
    private String category;
    private String subCategory;
    private User seller;
    private Double price;
    private Boolean active;
    private Integer imageId;

    // Constructors
    public Product() {

    }

    public Product(String id, String header, String description, String category, String subCategory, User seller, Double price, Boolean active) {
        this.id = id;
        this.header = header;
        this.description = description;
        this.category = category;
        this.subCategory = subCategory;
        this.seller = seller;
        this.price = price;
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

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
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
        active = active;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", header='" + header + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", seller=" + seller +
                ", price=" + price +
                ", active=" + active +
                ", imageId=" + imageId +
                '}';
    }
}