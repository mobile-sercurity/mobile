package com.marwaeltayeb.das.viewmodel;

import static com.marwaeltayeb.das.net.OxfordDataSourceFactory.oxfordDataSource;
import static com.marwaeltayeb.das.net.ProductDataSourceFactory.productDataSource;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.marwaeltayeb.das.model.Product;
import com.marwaeltayeb.das.net.OxfordDataSourceFactory;
import com.marwaeltayeb.das.net.ProductDataSource;
import com.marwaeltayeb.das.net.ProductDataSourceFactory;

public class ProductViewModel extends ViewModel {

    // Create liveData for PagedList and PagedKeyedDataSource
    public LiveData<PagedList<Product>> productPagedList;

    public LiveData<PagedList<Product>> oxfordPagedList;

    // Get PagedList configuration
    private static final PagedList.Config  pagedListConfig =
            (new PagedList.Config.Builder())
                    .setEnablePlaceholders(false)
                    .setPageSize(ProductDataSource.PAGE_SIZE)
                    .build();

    public void loadSneakers(String category, int userId){
        // Get our database source factory
        ProductDataSourceFactory productDataSourceFactory = new ProductDataSourceFactory(category,userId);

        // Build the paged list
        productPagedList = (new LivePagedListBuilder<>(productDataSourceFactory, pagedListConfig)).build();
    }

    public void loadOxfords(String category, int userId){
        // Get our database source factory
        OxfordDataSourceFactory oxfordDataSourceFactory = new OxfordDataSourceFactory(category,userId);

        // Build the paged list
        oxfordPagedList = (new LivePagedListBuilder<>(oxfordDataSourceFactory, pagedListConfig)).build();
    }

    public void invalidate(){
        if(productDataSource != null) productDataSource.invalidate();
        if(oxfordDataSource!= null) oxfordDataSource.invalidate();
    }
}
