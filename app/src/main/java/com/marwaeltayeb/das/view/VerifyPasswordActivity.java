package com.marwaeltayeb.das.view;

import static android.service.controls.ControlsProviderService.TAG;
import static com.marwaeltayeb.das.storage.LanguageUtils.loadLocale;
import static com.marwaeltayeb.das.utils.Constant.ACTIVITY_NAME;
import static com.marwaeltayeb.das.utils.Constant.AMOUNT;
import static com.marwaeltayeb.das.utils.Constant.CARTID;
import static com.marwaeltayeb.das.utils.Constant.IS_CHECK_PIN;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.marwaeltayeb.das.R;
import com.marwaeltayeb.das.databinding.ActivityPasswordVerifyBinding;
import com.marwaeltayeb.das.databinding.ActivityPinBinding;
import com.marwaeltayeb.das.model.Card;
import com.marwaeltayeb.das.model.Ordering;
import com.marwaeltayeb.das.model.Payment;
import com.marwaeltayeb.das.repository.CardRepository;
import com.marwaeltayeb.das.storage.LoginUtils;
import com.marwaeltayeb.das.viewmodel.CardViewModel;
import com.marwaeltayeb.das.viewmodel.OrderingViewModel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

// Trong PinActivity
public class VerifyPasswordActivity extends AppCompatActivity {
    // ... Khai báo biến và các thành phần UI cần thiết ...
    private ActivityPasswordVerifyBinding binding;
    private CardViewModel cardViewModel;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        intent = getIntent();
        String activityName = intent.getStringExtra(ACTIVITY_NAME);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password_verify);
        cardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);

        binding.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý logic kiểm tra mã PIN
                if (!binding.passwordEditText.getText().equals("")) {
                    switch(activityName) {
                        case "CardActivity":
                            try {
                                sendCard();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "ShippingAddressActivity":
                            try {
                                payment();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        default:
                            Intent intentDefault = new Intent(VerifyPasswordActivity.this, ProductActivity.class);
                            intentDefault.putExtra(IS_CHECK_PIN,"true");
                            startActivity(intentDefault);

                    }
//                    finish();

                } else {
//                    Log.d(TAG,"sai");
                    Toast.makeText(VerifyPasswordActivity.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendCard() throws Exception {

        String nameOnCard = intent.getStringExtra("nameOnCard");
        String cardNumber = intent.getStringExtra("cardNumber");
        String cardCvc = intent.getStringExtra("cardCvc");
        int year = Integer.parseInt(intent.getStringExtra("year"));
        int month = Integer.parseInt(intent.getStringExtra("month"));

        byte[] hashedKey = sha256(String.valueOf(binding.passwordEditText.getText()));
        SecretKeySpec secretKeySpec = new SecretKeySpec(hashedKey, "AES");
        byte[] cardNumberEncrypt = encrypt(cardNumber.getBytes(), secretKeySpec);
        byte[] cardCvcEncrypt = encrypt(cardCvc.getBytes(), secretKeySpec);

        Card card = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            card = new Card(nameOnCard, Base64.getEncoder().encodeToString(cardNumberEncrypt), Base64.getEncoder().encodeToString(cardCvcEncrypt),month,year, binding.passwordEditText.getText()+"");
            Log.d("cardNumberEncrypt", Base64.getEncoder().encodeToString(cardNumberEncrypt));
            Log.d("cardCvcEncrypt", Base64.getEncoder().encodeToString(cardCvcEncrypt));
        }
        String token = LoginUtils.getInstance(VerifyPasswordActivity.this).getUserToken();
        Card finalCard = card;
        cardViewModel.sendCard(card, token).observe(this, responseBody -> {
            try {
                String responseString = responseBody.string();
                Log.d("cardViewModel", responseString);
                Toast.makeText(VerifyPasswordActivity.this, responseString, Toast.LENGTH_SHORT).show();
                if(responseString.equals("Add Card Successful")){
                    LoginUtils.getInstance(VerifyPasswordActivity.this).saveCardInfo(finalCard);
                    Intent homeIntent = new Intent(VerifyPasswordActivity.this, AccountActivity.class);
                    startActivity(homeIntent);
                }
                finish();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private void payment() throws Exception {

        int cartID = intent.getIntExtra(CARTID, 0);
        double amount = intent.getDoubleExtra(AMOUNT,0);


        String token = LoginUtils.getInstance(VerifyPasswordActivity.this).getUserToken();
//        Log.d("token", token);
        Payment payment = new Payment(cartID, amount, ""+binding.passwordEditText.getText());
        cardViewModel.payment(payment, token).observe(this, responseBody -> {
            try {
                String responseString = responseBody.string();
                Log.d(TAG, responseString);
                Toast.makeText(VerifyPasswordActivity.this, responseString, Toast.LENGTH_SHORT).show();
                if(responseString.equals("Payment Successful")){
                    Intent homeIntent = new Intent(VerifyPasswordActivity.this, OrdersActivity.class);
                    startActivity(homeIntent);
                }else{
                    finish();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static byte[] sha256(String text) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(text.getBytes("UTF-8"));
    }

    private static byte[] encrypt(byte[] input, SecretKeySpec key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(input);
    }

    private static byte[] decrypt(byte[] input, SecretKeySpec key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(input);
    }

}
