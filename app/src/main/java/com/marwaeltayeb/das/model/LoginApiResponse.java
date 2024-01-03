package com.marwaeltayeb.das.model;

public class LoginApiResponse {

    private int id;
    private String name;
    private String email;
    private boolean error;
    private String message;
    private String password;
    private String token;
    private boolean isAdmin;

    private String card_month;
    private String card_year;
    private String name_on_card;
    private String card_number;
    private String cvc;
    public LoginApiResponse(String message) {
        this.message = message;
        this.error = true;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
    public String getCardName() {return name_on_card;}
    public String getCardNumber() {return card_number;}
    public String cardCvc() {return cvc;}
    public String getCardMonth() {return card_month;}
    public String getCardYear() {return card_year;}
}
