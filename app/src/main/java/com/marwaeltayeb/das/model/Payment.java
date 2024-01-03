package com.marwaeltayeb.das.model;

import com.google.gson.annotations.SerializedName;

public class Payment {

    @SerializedName("cartId")
    private int cartId;
    @SerializedName("amount")
    private double amount;
    @SerializedName("password")
    private String password;
    public Payment(int cartId, double amount, String password) {
        this.cartId = cartId;
        this.amount = amount;
        this.password = password;
    }

    public int getCartId() {return this.cartId;}
    public double getAmount() {return this.amount;}
}



