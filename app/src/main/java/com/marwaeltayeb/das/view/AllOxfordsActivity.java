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
import com.marwaeltayeb.das.databinding.ActivityAllOxfordsBinding;
import com.marwaeltayeb.das.model.Product;
import com.marwaeltayeb.das.storage.LoginUtils;
import com.marwaeltayeb.das.viewmodel.ProductViewModel;

public class AllOxfordsActivity extends AppCompatActivity implements ProductAdapter.ProductAdapterOnClickHandler {

    private ActivityAllOxfordsBinding binding;
    private ProductAdapter oxfordAdapter;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_oxfords);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.all_oxfords));

        int userID = LoginUtils.getInstance(this).getUserInfo().getId();

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.loadOxfords("oxford",userID);

        setupRecyclerViews();

        getAllOxfords();
    }

    private void setupRecyclerViews() {
        // Oxfords
        binding.allOxfordsRecyclerView.setHasFixedSize(true);
        binding.allOxfordsRecyclerView.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4));
        oxfordAdapter = new ProductAdapter(this, this);
    }

    public void getAllOxfords() {
        productViewModel.oxfordPagedList.observe(this, products -> oxfordAdapter.submitList(products));
        binding.allOxfordsRecyclerView.setAdapter(oxfordAdapter);
    }

    @Override
    public void onClick(Product product) {
        Intent intent = new Intent(AllOxfordsActivity.this, DetailsActivity.class);
        // Pass an object of product class
        intent.putExtra(PRODUCT, (product));
        startActivity(intent);
    }
}
