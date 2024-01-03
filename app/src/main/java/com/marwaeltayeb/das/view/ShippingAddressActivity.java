package com.marwaeltayeb.das.view;

import static android.content.ContentValues.TAG;
import static com.marwaeltayeb.das.utils.Constant.ACTIVITY_NAME;
import static com.marwaeltayeb.das.utils.Constant.AMOUNT;
import static com.marwaeltayeb.das.utils.Constant.CARTID;
import static com.marwaeltayeb.das.utils.Constant.IS_CHECK_PIN;
import static com.marwaeltayeb.das.utils.Constant.PRODUCTID;
import static com.marwaeltayeb.das.utils.Constant.VALUE_INTENT;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.marwaeltayeb.das.R;
import com.marwaeltayeb.das.databinding.ActivityShippingAddressBinding;
import com.marwaeltayeb.das.model.Card;
import com.marwaeltayeb.das.model.Shipping;
import com.marwaeltayeb.das.storage.LoginUtils;
import com.marwaeltayeb.das.viewmodel.ShippingViewModel;

import java.io.IOException;
import java.util.Objects;

public class ShippingAddressActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityShippingAddressBinding binding;

    private ShippingViewModel shippingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPin();
        Card card = LoginUtils.getInstance(ShippingAddressActivity.this).getCardInfo();
        if(card.getNumber() == null){
            Toast.makeText(ShippingAddressActivity.this, "Please Add Card To Payment", Toast.LENGTH_SHORT).show();

            Intent cardIntent = new Intent(ShippingAddressActivity.this, CardActivity.class);
            startActivity(cardIntent);
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shipping_address);

        shippingViewModel = ViewModelProviders.of(this).get(ShippingViewModel.class);

        binding.proceed.setOnClickListener(this);

        binding.txtName.setText(LoginUtils.getInstance(this).getUserInfo().getName());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.proceed) {
                addShippingAddress();

        }
    }
    private void checkPin() {
        if(LoginUtils.getInstance(ShippingAddressActivity.this).isHasPin()){
            Intent intent = getIntent();
            String isCheckPin = intent.getStringExtra(IS_CHECK_PIN);
            int cartID = intent.getIntExtra(CARTID, 0);
//            Log.d(TAG,"Kết quả: "+cartID);
            if(!Objects.equals(isCheckPin, "true")){
//                Log.d(TAG,"Đã nhập mã pin");
                Intent pinIntent = new Intent(ShippingAddressActivity.this, PinActivity.class);
                pinIntent.putExtra(ACTIVITY_NAME,"ShippingAddressActivity");
                pinIntent.putExtra(CARTID,cartID);
                startActivity(pinIntent);
                finish();
            }
        }
    }

    private void addShippingAddress() {
        String address = binding.address.getText().toString().trim();
        String city = binding.city.getText().toString().trim();
        String country = binding.country.getText().toString().trim();
        String zip = binding.zip.getText().toString().trim();
        String phone = binding.phone.getText().toString().trim();
        int userId = LoginUtils.getInstance(this).getUserInfo().getId();
        Intent intent = getIntent();
        int cartID = intent.getIntExtra(CARTID, 0);
        double amount = intent.getDoubleExtra(AMOUNT,0);
        Log.d("cartID", String.valueOf(cartID));
        Shipping shipping = new Shipping(address, city, country, zip, phone,userId, cartID);

        shippingViewModel.addShippingAddress(shipping).observe(this, responseBody -> {
            try {
                Toast.makeText(ShippingAddressActivity.this, responseBody.string()+"", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent orderProductIntent = new Intent(ShippingAddressActivity.this, VerifyPasswordActivity.class);
            orderProductIntent.putExtra(ACTIVITY_NAME,"ShippingAddressActivity");
            orderProductIntent.putExtra(CARTID,cartID);
            orderProductIntent.putExtra(AMOUNT,amount);
            startActivity(orderProductIntent);
        });
    }
}
