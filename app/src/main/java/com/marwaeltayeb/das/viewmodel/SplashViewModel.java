package com.marwaeltayeb.das.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.das.model.MalwareApiResponse;
import com.marwaeltayeb.das.repository.MalwareResponse;

public class SplashViewModel extends ViewModel {

    private final MalwareResponse malwareRepository;

    public SplashViewModel() {
        malwareRepository = new MalwareResponse();
    }

    public LiveData<MalwareApiResponse> getMalware() {
        return malwareRepository.getMalware();
    }
}
