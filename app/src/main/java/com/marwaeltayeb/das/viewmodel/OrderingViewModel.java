package com.marwaeltayeb.das.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.das.model.Ordering;
import com.marwaeltayeb.das.repository.OrderingRepository;

import okhttp3.ResponseBody;

public class OrderingViewModel extends ViewModel {

    private final OrderingRepository orderingRepository;

    public OrderingViewModel(  ) {
        orderingRepository = new OrderingRepository();
    }

    public LiveData<ResponseBody> orderProduct(Ordering ordering) {
        return orderingRepository.orderProduct(ordering);
    }
}
