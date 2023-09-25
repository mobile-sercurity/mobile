package com.marwaeltayeb.das.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.das.model.History;
import com.marwaeltayeb.das.repository.ToHistoryRepository;

import okhttp3.ResponseBody;

public class ToHistoryViewModel extends ViewModel {

    private final ToHistoryRepository toHistoryRepository;

    public ToHistoryViewModel() {
        toHistoryRepository = new ToHistoryRepository();
    }

    public LiveData<ResponseBody> addToHistory(History history) {
        return toHistoryRepository.addToHistory(history);
    }
}
