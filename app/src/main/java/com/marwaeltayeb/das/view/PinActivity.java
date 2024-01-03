package com.marwaeltayeb.das.view;

import static android.content.ContentValues.TAG;
import static com.google.android.material.internal.ContextUtils.getActivity;
import static com.marwaeltayeb.das.storage.LanguageUtils.loadLocale;
import static com.marwaeltayeb.das.utils.Constant.ACTIVITY_NAME;
import static com.marwaeltayeb.das.utils.Constant.CARTID;
import static com.marwaeltayeb.das.utils.Constant.IS_CHECK_PIN;
import static com.marwaeltayeb.das.utils.Constant.VALUE_INTENT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.marwaeltayeb.das.R;
import com.marwaeltayeb.das.databinding.ActivityPinBinding;
import com.marwaeltayeb.das.storage.LoginUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

// Trong PinActivity
public class PinActivity extends AppCompatActivity {
    // ... Khai báo biến và các thành phần UI cần thiết ...
    private ActivityPinBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        Intent intent = getIntent();
        String activityName = intent.getStringExtra(ACTIVITY_NAME);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pin);

        binding.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý logic kiểm tra mã PIN
                try {
                    if (isCorrectPIN(String.valueOf(binding.pinEditText.getText()))) {
                        // Nếu mã PIN đúng, chuyển sang ShippingAddressActivity
//                        Log.d(TAG,"đúng");
                        switch(activityName) {
                            case "ShippingAddressActivity":
                                Intent intentShippingAddress = new Intent(PinActivity.this, ShippingAddressActivity.class);
                                intentShippingAddress.putExtra(IS_CHECK_PIN,"true");
                                int valueIntent = intent.getIntExtra(CARTID, 0);
                                intentShippingAddress.putExtra(CARTID,valueIntent);
                                startActivity(intentShippingAddress);
                                break;
                            case "AccountActivity":
                                Intent intentAccount = new Intent(PinActivity.this, AccountActivity.class);
                                intentAccount.putExtra(IS_CHECK_PIN,"true");
                                startActivity(intentAccount);
                                break;
                            default:
                                Intent intentDefault = new Intent(PinActivity.this, ProductActivity.class);
                                intentDefault.putExtra(IS_CHECK_PIN,"true");
                                startActivity(intentDefault);

                        }
                        finish();

                    } else {
    //                    Log.d(TAG,"sai");
                        Toast.makeText(PinActivity.this, "Wrong PIN Code", Toast.LENGTH_SHORT).show();
                    }
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // Hàm kiểm tra mã PIN
    private boolean isCorrectPIN(String PIN) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return LoginUtils.getInstance(PinActivity.this).checkPin(PIN);
    }
}
