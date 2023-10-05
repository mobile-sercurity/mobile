package com.marwaeltayeb.das.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product implements Parcelable {

    private String colorSelect = "";
    private String sizeSelect = "";
    @SerializedName("id")
    private int productId;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("price")
    private double productPrice;
    @SerializedName("quantity")
    private int productQuantity;
    @SerializedName("supplier")
    private String productSupplier;
    @SerializedName("category")
    private String productCategory;
    @SerializedName("image")
    private String productImage;
    @SerializedName("isFavourite")
    private int isFavourite;
    @SerializedName("isInCart")
    private int isInCart;
    // Include child Parcelable objects
    private Product mInfo;
    @SerializedName("color")
    private String productColor;
    @SerializedName("size")
    private String productSize;
    @SerializedName("cartID")
    private int cartID;
    @SerializedName("cart_color")
    private String cartColor;
    @SerializedName("cart_size")
    private String cartSize;


    public Product(
            String productName, double productPrice, int productQuantity,
            String productSupplier, String productCategory, String productColor,
            String productSize, int cartID, String cartColor, String cartSize
    ) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productSupplier = productSupplier;
        this.productCategory = productCategory;
        this.productColor = productColor;
        this.productSize = productSize;
        this.cartID = cartID;
        this.cartColor = cartColor;
        this.cartSize = cartSize;
    }

    public Product() { }

    public int getProductId() {
        return productId;
    }
    public int getCartID() {
        return cartID;
    }
    public String getCartColor() {
        return cartColor;
    }
    public String getCartSize() {
        return cartSize;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public int isFavourite() {
        return isFavourite;
    }

    public int isInCart() {
        return isInCart;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite ? 1 : 0;
    }

    public void setIsInCart(boolean isInCart) {
        this.isInCart = isInCart ? 1 : 0;
    }

    public String getProductColor() {
        return productColor;
    }
    public String getProductSize() {
        return productSize;
    }
    public String getColorSelect() {
        return colorSelect;
    }
    public String getSizeSelect() {
        return sizeSelect;
    }
    public void setColorSelect(String color) {
        this.colorSelect = color;
    }
    public void setSizeSelect(String size) {
        this.sizeSelect = size;
    }

    // Write the values to be saved to the `Parcel`.
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(productId);
        out.writeString(productName);
        out.writeDouble(productPrice);
        out.writeInt(productQuantity);
        out.writeString(productSupplier);
        out.writeString(productCategory);
        out.writeString(productImage);
        out.writeInt(isFavourite);
        out.writeInt(isInCart);
        out.writeParcelable(mInfo, flags);
        out.writeString(productColor);
        out.writeString(productSize);
        out.writeInt(cartID);
        out.writeString(cartColor);
        out.writeString(cartSize);
    }

    // Retrieve the values written into the `Parcel`.
    private Product(Parcel in) {
        productId = in.readInt();
        productName = in.readString();
        productPrice = in.readDouble();
        productQuantity = in.readInt();
        productSupplier = in.readString();
        productCategory = in.readString();
        productImage = in.readString();
        isFavourite = in.readInt();
        isInCart = in.readInt();
        mInfo = in.readParcelable(Product.class.getClassLoader());
        productColor = in.readString();
        productSize = in.readString();
        cartID = in.readInt();
        cartColor = in.readString();
        cartSize = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Product> CREATOR
            = new Parcelable.Creator<Product>() {

        // This simply calls our new constructor and
        // passes along `Parcel`, and then returns the new object!
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
