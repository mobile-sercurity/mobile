package com.marwaeltayeb.das.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.das.model.CartApiResponse;
import com.marwaeltayeb.das.repository.CartRepository;

public class CartViewModel extends ViewModel {

    private final CartRepository cartRepository;

    public CartViewModel() {
        cartRepository = new CartRepository();
    }

    public LiveData<CartApiResponse> getProductsInCart(int userId) {
        return cartRepository.getProductsInCart(userId);
    }
}
