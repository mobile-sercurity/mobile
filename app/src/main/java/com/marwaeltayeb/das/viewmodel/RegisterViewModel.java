package com.marwaeltayeb.das.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.das.model.RegisterApiResponse;
import com.marwaeltayeb.das.model.User;
import com.marwaeltayeb.das.repository.RegisterRepository;

public class RegisterViewModel extends ViewModel {

    private final RegisterRepository registerRepository;

    public RegisterViewModel() {
        registerRepository = new RegisterRepository();
    }

    public LiveData<RegisterApiResponse> getRegisterResponseLiveData(User user) {
        return registerRepository.getRegisterResponseData(user);
    }
}
