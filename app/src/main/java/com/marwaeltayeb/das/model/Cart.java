package com.marwaeltayeb.das.model;

import com.google.gson.annotations.SerializedName;

public class Cart {

    @SerializedName("userId")
    private int userId;
    @SerializedName("productId")
    private int productId;
    @SerializedName("productColor")
    private String productColor;
    @SerializedName("productSize")
    private String productSize;

    public Cart(int userId, int productId, String productColor, String productSize) {
        this.userId = userId;
        this.productId = productId;
        this.productColor = productColor;
        this.productSize = productSize;
    }
}
