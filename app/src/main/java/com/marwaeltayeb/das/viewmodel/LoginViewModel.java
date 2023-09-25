package com.marwaeltayeb.das.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.das.model.LoginApiResponse;
import com.marwaeltayeb.das.repository.LoginRepository;

public class LoginViewModel extends ViewModel {

    private final LoginRepository loginRepository;

    public LoginViewModel() {
        loginRepository = new LoginRepository();
    }

    public LiveData<LoginApiResponse> getLoginResponseLiveData(String email, String password) {
        return loginRepository.getLoginResponseData(email, password);
    }
}
