package com.marwaeltayeb.das.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.das.model.ProductApiResponse;
import com.marwaeltayeb.das.repository.SearchRepository;

public class SearchViewModel  extends ViewModel {

    private final SearchRepository searchRepository;

    public SearchViewModel(  ) {
        searchRepository = new SearchRepository();
    }


    public LiveData<ProductApiResponse> getProductsBySearch(String keyword, int userId) {
        return searchRepository.getResponseDataBySearch(keyword, userId);
    }
}
