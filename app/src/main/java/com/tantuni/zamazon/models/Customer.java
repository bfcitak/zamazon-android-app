package com.tantuni.zamazon.models;

import java.util.ArrayList;
import java.util.Set;

public class Customer extends User {
    private Cart cart;
    private WishList wishList;
    private ArrayList<CreditCard> creditCards;
    private ArrayList<Address> addresses;

    // Constructors
    public Customer() {

    }

    public Customer(String id, String email, String password, String firstName, String lastName, Boolean active, Set<Role> roles, Cart cart, WishList wishList, ArrayList<CreditCard> creditCards, ArrayList<Address> addresses) {
        super(id, email, password, firstName, lastName, active, roles);
        this.cart = cart;
        this.wishList = wishList;
        this.creditCards = creditCards;
        this.addresses = addresses;
    }

    // Getters and Setters
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public WishList getWishList() {
        return wishList;
    }
    public void setWishList(WishList wishList) {
        this.wishList = wishList;
    }
    public ArrayList<CreditCard> getCreditCards() {
        return creditCards;
    }
    public void setCreditCards(ArrayList<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }
    public ArrayList<Address> getAddresses() {
        return addresses;
    }
    public void setAddresses(ArrayList<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cart=" + cart +
                ", wishList=" + wishList +
                ", creditCards=" + creditCards +
                ", addresses=" + addresses +
                "} " + super.toString();
    }

}
