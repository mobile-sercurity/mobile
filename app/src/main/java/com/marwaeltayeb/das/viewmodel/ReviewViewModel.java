package com.marwaeltayeb.das.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.das.model.ReviewApiResponse;
import com.marwaeltayeb.das.repository.ReviewRepository;

public class ReviewViewModel extends ViewModel {

    private final ReviewRepository reviewRepository;

    public ReviewViewModel(  ) {
        reviewRepository = new ReviewRepository();
    }

    public LiveData<ReviewApiResponse> getReviews(int productId) {
        return reviewRepository.getReviews(productId);
    }
}
