package com.marwaeltayeb.das.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.das.model.Favorite;
import com.marwaeltayeb.das.repository.AddFavoriteRepository;
import com.marwaeltayeb.das.utils.RequestCallback;

import okhttp3.ResponseBody;

public class AddFavoriteViewModel extends ViewModel {

    private final AddFavoriteRepository addFavoriteRepository;

    public AddFavoriteViewModel() {
        addFavoriteRepository = new AddFavoriteRepository();
    }

    public LiveData<ResponseBody> addFavorite(Favorite favorite, RequestCallback callback) {
        return addFavoriteRepository.addFavorite(favorite,callback);
    }
}
