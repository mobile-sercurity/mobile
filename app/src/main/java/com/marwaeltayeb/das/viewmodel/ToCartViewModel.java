package com.marwaeltayeb.das.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.das.model.Cart;
import com.marwaeltayeb.das.repository.ToCartRepository;
import com.marwaeltayeb.das.utils.RequestCallback;

import okhttp3.ResponseBody;

public class ToCartViewModel extends ViewModel {

    private final ToCartRepository toCartRepository;

    public ToCartViewModel() {
        toCartRepository = new ToCartRepository();
    }

    public LiveData<ResponseBody> addToCart(Cart cart, RequestCallback callback) {
        return toCartRepository.addToCart(cart, callback);
    }
}
