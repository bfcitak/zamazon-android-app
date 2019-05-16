package com.tantuni.zamazon.models;

public class Payment {
    private Double total;
    private Double tax;

    // Constructors
    public Payment() {

    }

    public Payment(Double total, Double tax) {
        this.total = total;
        this.tax = tax;
    }

    // Getters and Setters
    public Double getTotal() {
        return total;
    }
    public void setTotal(Double total) {
        this.total = total;
    }
    public Double getTax() {
        return tax;
    }
    public void setTax(Double tax) {
        this.tax = tax;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "total=" + total +
                ", tax=" + tax +
                '}';
    }
}
