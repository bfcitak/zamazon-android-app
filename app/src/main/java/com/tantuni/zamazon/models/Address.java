package com.tantuni.zamazon.models;

public class Address {
    private String id;
    private String title;
    private String address;
    private String receiverName;

    // Constructors
    public Address() {

    }

    public Address(String title, String address, String receiverName) {
        this.title = title;
        this.address = address;
        this.receiverName = receiverName;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getReceiverName() {
        return receiverName;
    }
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", receiverName='" + receiverName + '\'' +
                '}';
    }
}
