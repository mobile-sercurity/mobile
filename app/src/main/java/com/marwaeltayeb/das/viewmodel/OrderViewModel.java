package com.marwaeltayeb.das.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.das.model.OrderApiResponse;
import com.marwaeltayeb.das.repository.OrderRepository;

public class OrderViewModel extends ViewModel {

    private final OrderRepository orderRepository;

    public OrderViewModel() {
        orderRepository = new OrderRepository();
    }

    public LiveData<OrderApiResponse> getOrders(int userId) {
        return orderRepository.getOrders(userId);
    }
}

