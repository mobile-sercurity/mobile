package com.marwaeltayeb.das.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.das.model.FavoriteApiResponse;
import com.marwaeltayeb.das.repository.FavoriteRepository;

public class FavoriteViewModel extends ViewModel {

    private final FavoriteRepository favoriteRepository;

    public FavoriteViewModel() {
        favoriteRepository = new FavoriteRepository();
    }

    public LiveData<FavoriteApiResponse> getFavorites(int userId) {
        return favoriteRepository.getFavorites(userId);
    }
}
