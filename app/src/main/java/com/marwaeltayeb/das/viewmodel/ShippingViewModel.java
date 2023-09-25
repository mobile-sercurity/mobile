package com.marwaeltayeb.das.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.das.model.Shipping;
import com.marwaeltayeb.das.repository.ShippingRepository;

import okhttp3.ResponseBody;

public class ShippingViewModel  extends ViewModel {

    private final ShippingRepository shippingRepository;

    public ShippingViewModel() {
        shippingRepository = new ShippingRepository();
    }

    public LiveData<ResponseBody> addShippingAddress(Shipping shipping) {
        return shippingRepository.addShippingAddress(shipping);
    }
}
