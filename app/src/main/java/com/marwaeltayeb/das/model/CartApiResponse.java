package com.marwaeltayeb.das.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartApiResponse {

    @SerializedName("carts")
    private List<Product> carts;

    public List<Product> getProductsInCart() {
        return carts;
    }
}
