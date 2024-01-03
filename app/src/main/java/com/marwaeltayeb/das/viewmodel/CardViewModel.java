package com.marwaeltayeb.das.viewmodel;

import static android.service.controls.ControlsProviderService.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.das.model.Card;
import com.marwaeltayeb.das.model.Payment;
import com.marwaeltayeb.das.repository.CardRepository;
import com.marwaeltayeb.das.repository.PaymentRepository;

import okhttp3.ResponseBody;

public class CardViewModel extends ViewModel {

    private final CardRepository cardRepository;
    private final PaymentRepository paymentRepository;

    public CardViewModel(  ) {
        cardRepository = new CardRepository();paymentRepository = new PaymentRepository();
    }

    public LiveData<ResponseBody> sendCard(Card card, String token) {
        return cardRepository.sendCard(card, token);
    }
    public LiveData<ResponseBody> payment(Payment payment, String token) {
        return paymentRepository.payment(payment, token);
    }
}
