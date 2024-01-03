package com.marwaeltayeb.das.view;

import static android.content.ContentValues.TAG;
import static com.marwaeltayeb.das.storage.LanguageUtils.loadLocale;
import static com.marwaeltayeb.das.utils.Constant.ACTIVITY_NAME;
import static com.marwaeltayeb.das.utils.Constant.IS_CHECK_PIN;
import static com.marwaeltayeb.das.utils.Constant.PRODUCT_ID;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.marwaeltayeb.das.R;
import com.marwaeltayeb.das.databinding.ActivityPinBinding;
import com.marwaeltayeb.das.databinding.ActivityPinChangeBinding;
import com.marwaeltayeb.das.databinding.ActivityPinCreateBinding;
import com.marwaeltayeb.das.storage.LoginUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

// Trong PinActivity
public class PinSettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        Intent intent = getIntent();
        String activityName = intent.getStringExtra(ACTIVITY_NAME);
        if(!LoginUtils.getInstance(PinSettingActivity.this).isHasPin()){
            Intent createIntent = new Intent(PinSettingActivity.this, PinCreateActivity.class);
            startActivity(createIntent);
        }else {
            Intent changeIntent = new Intent(PinSettingActivity.this, PinChangeActivity.class);
            startActivity(changeIntent);
        }
    }
}
