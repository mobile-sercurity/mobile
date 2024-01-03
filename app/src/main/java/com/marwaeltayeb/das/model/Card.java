package com.marwaeltayeb.das.model;

import com.google.gson.annotations.SerializedName;

public class Card {

    @SerializedName("nameOnCard")
    private String nameOnCard;
    @SerializedName("cardNumber")
    private String cardNumber;
    @SerializedName("cvc")
    private String cardCvc;
    @SerializedName("month")
    private int month;
    @SerializedName("year")
    private int year;
    @SerializedName("password")
    private String password;

    public Card(String nameOnCard, String cardNumber, String cardCvc, int month, int year, String password) {
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.cardCvc = cardCvc;
        this.month = month;
        this.year = year;
        this.password = password;
    }

    public String getName() {return this.nameOnCard;}
    public String getNumber() {return this.cardNumber;}
    public String getCvc() {return this.cardCvc;}
    public int getMonth() {return this.month;}
    public int getYear() {return this.year;}
    public String getPassword() {return this.password;}
}



