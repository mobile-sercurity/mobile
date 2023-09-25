package com.marwaeltayeb.das.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.das.repository.FromCartRepository;
import com.marwaeltayeb.das.utils.RequestCallback;

import okhttp3.ResponseBody;

public class FromCartViewModel extends ViewModel {

    private final FromCartRepository fromCartRepository;

    public FromCartViewModel(  ) {
        fromCartRepository = new FromCartRepository();
    }

    public LiveData<ResponseBody> removeFromCart(int userId, int productId, RequestCallback callback) {
        return fromCartRepository.removeFromCart(userId, productId, callback);
    }
}
