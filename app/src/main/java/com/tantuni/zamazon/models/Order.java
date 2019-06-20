package com.tantuni.zamazon.models;

import java.util.Date;

public class Order {
    String id;
    String customerId;
    Cart cart;
    CreditCard creditCard;
    Address address;
    Date dateOfPurchase;

    // Constructors
    public Order() {

    }

    public Order(String customerId, Cart cart, CreditCard creditCard, Address address, Date dateOfPurchase) {
        this.customerId = customerId;
        this.cart = cart;
        this.creditCard = creditCard;
        this.address = address;
        this.dateOfPurchase = dateOfPurchase;
    }

    public Order(String id, String customerId, Cart cart, CreditCard creditCard, Address address, Date dateOfPurchase) {
        this.id = id;
        this.customerId = customerId;
        this.cart = cart;
        this.creditCard = creditCard;
        this.address = address;
        this.dateOfPurchase = dateOfPurchase;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public CreditCard getCreditCard() {
        return creditCard;
    }
    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }
    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", cart=" + cart +
                ", creditCard=" + creditCard +
                ", address=" + address +
                ", dateOfPurchase=" + dateOfPurchase +
                '}';
    }
}
