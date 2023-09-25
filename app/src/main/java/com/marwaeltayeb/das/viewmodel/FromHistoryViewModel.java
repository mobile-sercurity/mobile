package com.marwaeltayeb.das.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.das.repository.FromHistoryRepository;

import okhttp3.ResponseBody;

public class FromHistoryViewModel extends ViewModel {

    private final FromHistoryRepository fromHistoryRepository;

    public FromHistoryViewModel() {
        fromHistoryRepository = new FromHistoryRepository();
    }

    public LiveData<ResponseBody> removeAllFromHistory() {
        return fromHistoryRepository.removeAllFromHistory();
    }
}
