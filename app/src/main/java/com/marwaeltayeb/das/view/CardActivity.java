package com.marwaeltayeb.das.view;

import static com.marwaeltayeb.das.storage.LanguageUtils.loadLocale;
import static com.marwaeltayeb.das.utils.Constant.ACTIVITY_NAME;
import static com.marwaeltayeb.das.utils.Constant.IS_CHECK_PIN;
import static com.marwaeltayeb.das.utils.Constant.PRODUCTID;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.marwaeltayeb.das.R;
import com.marwaeltayeb.das.databinding.ActivityCardBinding;
import com.marwaeltayeb.das.databinding.ActivityOrderProductBinding;
import com.marwaeltayeb.das.databinding.ActivityPinCreateBinding;
import com.marwaeltayeb.das.model.Card;
import com.marwaeltayeb.das.model.Ordering;
import com.marwaeltayeb.das.storage.LoginUtils;
import com.marwaeltayeb.das.viewmodel.OrderingViewModel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

// Trong PinActivity
public class CardActivity extends AppCompatActivity{
    // ... Khai báo biến và các thành phần UI cần thiết ...
    private ActivityCardBinding binding;
    private OrderingViewModel orderingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_card);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Card");
        orderingViewModel = ViewModelProviders.of(this).get(OrderingViewModel.class);

        binding.addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderProduct();
            }
        });

        populateSpinner();
        setValue();
    }

    private void orderProduct() {
        String nameOnCard = binding.nameOnCard.getText().toString().trim();
        String cardNumber = binding.cardNumber.getText().toString().trim();
        String cardCvc = binding.cardCvc.getText().toString().trim();

        String year = binding.spinnerYearMenu.getEditableText().toString().toLowerCase();
        String month = binding.spinnerMonthMenu.getEditableText().toString().toLowerCase();

        Intent intentDefault = new Intent(CardActivity.this, VerifyPasswordActivity.class);
        intentDefault.putExtra("nameOnCard",nameOnCard);
        intentDefault.putExtra("cardNumber",cardNumber);
        intentDefault.putExtra("cardCvc",cardCvc);
        intentDefault.putExtra("year",year);
        intentDefault.putExtra("month",month);
        intentDefault.putExtra(ACTIVITY_NAME,"CardActivity");
        startActivity(intentDefault);
        finish();
    }

    private void populateSpinner() {
        String[] years = {"2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030"};
        ArrayAdapter<CharSequence> yearsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, years );
        binding.spinnerYearMenu.setAdapter(yearsAdapter);

        String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        ArrayAdapter<CharSequence> monthsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, months );
        binding.spinnerMonthMenu.setAdapter(monthsAdapter);
    }
    private void setValue() {
        Card card = LoginUtils.getInstance(CardActivity.this).getCardInfo();
        Log.d("card", String.valueOf(card.getName()));
        if (card.getName() != null){
            binding.nameOnCard.setText(card.getName());
        }
        if (card.getNumber() != null){
            binding.cardNumber.setText(card.getNumber());
        }
        if (card.getCvc() != null){
            binding.cardCvc.setText(card.getCvc());
        }
        if (card.getMonth() != 0){
            binding.spinnerMonthMenu.setText(card.getMonth()+"");
        }
        if (card.getYear() != 0){
            binding.spinnerYearMenu.setText(card.getYear()+"");
        }
    }
}
