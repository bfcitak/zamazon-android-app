package com.tantuni.zamazon.models;

public class CreditCard {
    private String id;
    private String cardNumber;
    private String nameOnCard;
    private String cvc;
    private String expiryDate;

    // Constructors
    public CreditCard() {

    }

    public CreditCard(String cardNumber, String nameOnCard, String cvc, String expiryDate) {
        this.cardNumber = cardNumber;
        this.nameOnCard = nameOnCard;
        this.cvc = cvc;
        this.expiryDate = expiryDate;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public String getNameOnCard() {
        return nameOnCard;
    }
    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }
    public String getCvc() {
        return cvc;
    }
    public void setCvc(String cvc) {
        this.cvc = cvc;
    }
    public String getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id='" + id + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", nameOnCard='" + nameOnCard + '\'' +
                ", cvc='" + cvc + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                '}';
    }
}
