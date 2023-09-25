package com.marwaeltayeb.das.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.das.model.Image;
import com.marwaeltayeb.das.repository.UserImageRepository;

public class UserImageViewModel extends ViewModel {

    private final UserImageRepository userImageRepository;

    public UserImageViewModel() {
        userImageRepository = new UserImageRepository();
    }

    public LiveData<Image> getUserImage(int userId) {
        return userImageRepository.getUserImage(userId);
    }
}
