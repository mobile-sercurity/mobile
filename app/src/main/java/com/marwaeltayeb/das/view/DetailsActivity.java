package com.marwaeltayeb.das.view;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;
import static com.marwaeltayeb.das.storage.LanguageUtils.loadLocale;
import static com.marwaeltayeb.das.utils.Constant.LOCALHOST;
import static com.marwaeltayeb.das.utils.Constant.PRODUCT;
import static com.marwaeltayeb.das.utils.Constant.PRODUCTID;
import static com.marwaeltayeb.das.utils.Constant.PRODUCT_ID;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.das.R;
import com.marwaeltayeb.das.adapter.ReviewAdapter;
import com.marwaeltayeb.das.adapter.ColorAdapter;
import com.marwaeltayeb.das.adapter.SizeAdapter;
import com.marwaeltayeb.das.databinding.ActivityDetailsBinding;
import com.marwaeltayeb.das.model.Cart;
import com.marwaeltayeb.das.model.Product;
import com.marwaeltayeb.das.model.Review;
import com.marwaeltayeb.das.storage.LoginUtils;
import com.marwaeltayeb.das.utils.RequestCallback;
import com.marwaeltayeb.das.viewmodel.ReviewViewModel;
import com.marwaeltayeb.das.viewmodel.ToCartViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DetailsActivity";

    private ActivityDetailsBinding binding;
    private ReviewViewModel reviewViewModel;
    private ToCartViewModel toCartViewModel;
    private ReviewAdapter reviewAdapter;

    private ColorAdapter colorAdapter;
    private SizeAdapter sizeAdapter;
    private List<Review> reviewList;
    private List<String> listColor;
    private List<String> listSize;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        reviewViewModel = ViewModelProviders.of(this).get(ReviewViewModel.class);
        toCartViewModel = ViewModelProviders.of(this).get(ToCartViewModel.class);

        binding.txtSeeAllReviews.setOnClickListener(this);
        binding.writeReview.setOnClickListener(this);
        binding.addToCart.setOnClickListener(this);
        binding.buy.setOnClickListener(this);

        getProductDetails();

        setUpRecycleView();

        getReviewsOfProduct();
        getColorList();
        getSizeList();
    }

    private void setUpRecycleView() {
        binding.listOfReviews.setHasFixedSize(true);
        binding.listOfReviews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.listOfReviews.setItemAnimator(null);
    }

    private void getProductDetails() {
        // Receive the product object
        product = getIntent().getParcelableExtra(PRODUCT);

//        Log.d(TAG,"" + product);
//        Log.d(TAG,"isFavourite " + product.isFavourite() + " isInCart " + product.isInCart());

        // Set Custom ActionBar Layout
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.action_bar_title_layout);
        ((TextView) findViewById(R.id.action_bar_title)).setText(product.getProductName());

        binding.nameOfProduct.setText(product.getProductName());
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(product.getProductPrice());
        binding.priceOfProduct.setText(String.valueOf(formattedPrice + " VNĐ"));
        binding.colorTitle.setText("Chọn màu");
        binding.sizeTitle.setText("Chọn cỡ");

        String imageUrl = LOCALHOST + product.getProductImage().replaceAll("\\\\", "/");
//        Glide.with(this)
//                .load(imageUrl)
//                .into(binding.imageOfProduct);
        flipImages(product.getProductListImage());

    }

    private void flipImages(String listImage) {
        String[] parts = listImage.substring(1, listImage.length() - 1).split(", ");
        List<String> images = new ArrayList<>();
        for (String part : parts) {
            String element = LOCALHOST + part.substring(1, part.length() - 1).replaceAll("\\\\", "/");
            images.add(element);
        }
        for (String image : images) {
            ImageView imageView = new ImageView(this);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, // width
                    ViewGroup.LayoutParams.MATCH_PARENT  // height
            );
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(layoutParams);

            Glide.with(this)
                .load(image)
                .into(imageView);
//            imageView.setBackgroundResource(image);
            binding.imageOfProduct.addView(imageView);
        }

        binding.imageOfProduct.setFlipInterval(500);
//        binding.imageOfProduct.setAutoStart(true);

        // Set the animation for the view that enters the screen
        binding.imageOfProduct.setInAnimation(this, R.anim.slide_in_right);
        binding.imageOfProduct.setOutAnimation(this, R.anim.slide_out_left);
        // Set the animation for the view leaving th screen
        binding.prevBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                binding.imageOfProduct.showPrevious();
            }
        });
        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                binding.imageOfProduct.showNext();
            }
        });


    }

    private void getColorList() {
//        Get list color
        String[] parts = product.getProductColor().substring(1, product.getProductColor().length() - 1).split(", ");
        listColor = new ArrayList<>();
        for (String part : parts) {
            String element = part.substring(1, part.length() - 1);
            listColor.add(element);
        }

        if(listColor.size() > 5){
            binding.colorList.getLayoutParams().height = 240;
        }
        colorAdapter = new ColorAdapter(this, listColor, this::onColorClick);
        binding.colorList.setAdapter(colorAdapter);
        colorAdapter.notifyDataSetChanged();
    }
    public void onColorClick(String color) {
        product.setColorSelect(color);
    }
    private void getSizeList() {
        String[] part1s = product.getProductSize().substring(1, product.getProductSize().length() - 1).split(", ");
        listSize = new ArrayList<>();
        for (String part : part1s) {
            String element = part.substring(1, part.length() - 1);
            listSize.add(element);
        }
        if(listSize.size() > 5){
            binding.sizeList.getLayoutParams().height = 240;
        }
        sizeAdapter = new SizeAdapter(this, listSize, this::onSizeClick);
        binding.sizeList.setAdapter(sizeAdapter);
        sizeAdapter.notifyDataSetChanged();
    }
    public void onSizeClick(String size) {
        product.setSizeSelect(size);
    }
    private void getReviewsOfProduct() {
        reviewViewModel.getReviews(product.getProductId()).observe(this, reviewApiResponse -> {
            if (reviewApiResponse != null) {
                reviewList = reviewApiResponse.getReviewList();
                reviewAdapter = new ReviewAdapter(reviewList);
                binding.listOfReviews.setAdapter(reviewAdapter);
            }

            if(reviewList.size() == 0){
                binding.listOfReviews.setVisibility(View.GONE);
                binding.txtFirst.setVisibility(View.VISIBLE);
            }else {
                binding.listOfReviews.setVisibility(View.VISIBLE);
                binding.txtFirst.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txtSeeAllReviews) {
            Intent allReviewIntent = new Intent(DetailsActivity.this, AllReviewsActivity.class);
            allReviewIntent.putExtra(PRODUCT_ID,product.getProductId());
            startActivity(allReviewIntent);
        } else if (view.getId() == R.id.writeReview) {
            Intent allReviewIntent = new Intent(DetailsActivity.this, WriteReviewActivity.class);
            allReviewIntent.putExtra(PRODUCT_ID,product.getProductId());
            startActivity(allReviewIntent);
        }else if(view.getId() == R.id.addToCart){
            if(product.getColorSelect() == ""){
                Toast.makeText(this, "Please Select Color", Toast.LENGTH_SHORT).show();
            }else if(product.getSizeSelect() == "" ) {
                Toast.makeText(this, "Please Select Size", Toast.LENGTH_SHORT).show();
            }else{
                insertToCart(() -> product.setIsInCart(true));
                Intent cartIntent = new Intent(DetailsActivity.this, CartActivity.class);
                startActivity(cartIntent);

            }

        }
    }

    private void insertToCart(RequestCallback callback) {
        Cart cart = new Cart(
                LoginUtils.getInstance(this).getUserInfo().getId(),
                product.getProductId(),
                product.getColorSelect(),
                product.getSizeSelect()
        );
        toCartViewModel.addToCart(cart, callback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getReviewsOfProduct();
    }
}
