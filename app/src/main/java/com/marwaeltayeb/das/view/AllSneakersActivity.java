package com.marwaeltayeb.das.view;

import static com.marwaeltayeb.das.storage.LanguageUtils.loadLocale;
import static com.marwaeltayeb.das.utils.Constant.PRODUCT;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.marwaeltayeb.das.R;
import com.marwaeltayeb.das.adapter.ProductAdapter;
import com.marwaeltayeb.das.databinding.ActivityAllSneakersBinding;
import com.marwaeltayeb.das.model.Product;
import com.marwaeltayeb.das.storage.LoginUtils;
import com.marwaeltayeb.das.viewmodel.ProductViewModel;

public class AllSneakersActivity extends AppCompatActivity implements ProductAdapter.ProductAdapterOnClickHandler{

    private ActivityAllSneakersBinding binding;
    private ProductAdapter productAdapter;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_sneakers);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.all_sneakers));

        int userID = LoginUtils.getInstance(this).getUserInfo().getId();

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.loadSneakers("sneaker", userID);

        setupRecyclerViews();

        getAllSneakers();
    }

    private void setupRecyclerViews() {
        // Sneakers
        binding.allSneakersRecyclerView.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4));
        binding.allSneakersRecyclerView.setHasFixedSize(true);
        productAdapter = new ProductAdapter(this,this);
    }

    public void getAllSneakers() {
        // Observe the productPagedList from ViewModel
        productViewModel.productPagedList.observe(this, products -> {
            productAdapter.submitList(products);
        });

        binding.allSneakersRecyclerView.setAdapter(productAdapter);
    }

    @Override
    public void onClick(Product product) {
        Intent intent = new Intent(AllSneakersActivity.this, DetailsActivity.class);
        // Pass an object of product class
        intent.putExtra(PRODUCT, (product));
        startActivity(intent);
    }
}
