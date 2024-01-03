package com.marwaeltayeb.das.view;

import static com.marwaeltayeb.das.storage.LanguageUtils.loadLocale;
import static com.marwaeltayeb.das.utils.Constant.ACTIVITY_NAME;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.marwaeltayeb.das.R;
import com.marwaeltayeb.das.databinding.ActivityPinChangeBinding;
import com.marwaeltayeb.das.databinding.ActivityPinCreateBinding;
import com.marwaeltayeb.das.storage.LoginUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

// Trong PinActivity
public class PinChangeActivity extends AppCompatActivity {
    // ... Khai báo biến và các thành phần UI cần thiết ...
    private ActivityPinChangeBinding binding;

    private static final String SHARED_PREF_NAME = "shared_preference";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        Intent intent = getIntent();
        String activityName = intent.getStringExtra(ACTIVITY_NAME);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pin_change);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Change PIN");
        binding.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    changePIN(String.valueOf(binding.newPin.getText()));
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
        return LoginUtils.getInstance(PinChangeActivity.this).checkPin(PIN);
    }

    private void changePIN(String PIN) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String newPin = String.valueOf(binding.newPin.getText());
        String reNewPin = String.valueOf(binding.reNewPin.getText());
        if(!isCorrectPIN(String.valueOf(binding.oldPin.getText()))){
            Toast.makeText(this, "Old PIN Is Incorrect", Toast.LENGTH_SHORT).show();
        }else if(!newPin.equals(reNewPin)) {
            Toast.makeText(this, "Re-enter PIN Is Incorrect", Toast.LENGTH_SHORT).show();
        }else if(newPin.length() != 4){
            Toast.makeText(this, "The Pin Code Needs 4 Numbers", Toast.LENGTH_SHORT).show();
        }else{
            LoginUtils.getInstance(PinChangeActivity.this).savePin(PIN);
            Intent accountIntent = new Intent(PinChangeActivity.this, AccountActivity.class);
            startActivity(accountIntent);
        }
    }
}
