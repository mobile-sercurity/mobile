package com.marwaeltayeb.das.net;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.marwaeltayeb.das.model.Product;

public class OxfordDataSourceFactory extends DataSource.Factory{

    // Creating the mutable live database
    private final MutableLiveData<PageKeyedDataSource<Integer, Product>> oxfordLiveDataSource = new MutableLiveData<>();

    public static ProductDataSource oxfordDataSource;

    private final String category;
    private final int userId;

    public OxfordDataSourceFactory(String category, int userId){
        this.category = category;
        this.userId = userId;
    }

    @Override
    public DataSource<Integer, Product> create() {
        // Getting our Data source object
        oxfordDataSource = new ProductDataSource(category,userId);

        // Posting the Data source to get the values
        oxfordLiveDataSource.postValue(oxfordDataSource);

        // Returning the Data source
        return oxfordDataSource;
    }
}