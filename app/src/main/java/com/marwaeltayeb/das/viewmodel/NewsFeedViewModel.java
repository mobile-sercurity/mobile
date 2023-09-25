package com.marwaeltayeb.das.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.das.model.NewsFeedResponse;
import com.marwaeltayeb.das.repository.NewsFeedRepository;

public class NewsFeedViewModel extends ViewModel {

    private final NewsFeedRepository newsFeedRepository;

    public NewsFeedViewModel() {
        newsFeedRepository = new NewsFeedRepository();
    }

    public LiveData<NewsFeedResponse> getPosters() {
        return newsFeedRepository.getPosters();
    }
}
