package com.marwaeltayeb.das.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.das.repository.PasswordRepository;

import okhttp3.ResponseBody;

public class PasswordViewModel extends ViewModel {

    private final PasswordRepository passwordRepository;

    public PasswordViewModel() {
        passwordRepository = new PasswordRepository();
    }

    public LiveData<ResponseBody> updatePassword(String token, String newPassword, int userId) {
        return passwordRepository.updatePassword(token, newPassword, userId);
    }
}
