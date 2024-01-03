package com.marwaeltayeb.das.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.marwaeltayeb.das.model.Card;
import com.marwaeltayeb.das.model.LoginApiResponse;
import com.marwaeltayeb.das.model.User;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class LoginUtils {

    private static final String SHARED_PREF_NAME = "shared_preference";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String TOKEN = "token";
    private static final String IS_ADMIN = "isAdmin";
    private static final String PIN = "pin";

    private static LoginUtils mInstance;
    private final Context mCtx;

    private LoginUtils(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized LoginUtils getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new LoginUtils(mCtx);
        }
        return mInstance;
    }

    public void saveUserInfo(LoginApiResponse response) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(ID, response.getId());
        editor.putString(NAME, response.getName());
        editor.putString(EMAIL, response.getEmail());
        editor.putString(PASSWORD, response.getPassword());
        editor.putString(TOKEN, response.getToken());
        editor.putBoolean(IS_ADMIN, response.isAdmin());
        editor.putString("cardName", response.getCardName());
        editor.putString("cardNumber", response.getCardNumber());
        editor.putString("cardCvc", response.cardCvc());
        editor.putString("month", response.getCardMonth());
        editor.putString("year", response.getCardYear());
        editor.apply();
    }
    public void savePin(String PINCode) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] result = digest.digest(PINCode.getBytes("UTF-8"));

        StringBuilder sb = new StringBuilder();

        for (byte b : result)
        {
            sb.append(String.format("%02X", b));
        }

        String messageDigest = sb.toString();

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(PIN, messageDigest);
        editor.apply();
    }

    public boolean checkPin(String PINCode) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] result = digest.digest(PINCode.getBytes("UTF-8"));

        StringBuilder sb = new StringBuilder();

        for (byte b : result) // This is your byte[] result..
        {
            sb.append(String.format("%02X", b));
        }

        String messageDigest = sb.toString();

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString("pin", "").equals(messageDigest);
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }
    public boolean isHasPin() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return !sharedPreferences.getString("pin", "").equals("");
    }

    public void saveUserInfo(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(ID, user.getId());
        editor.putString(NAME, user.getName());
        editor.putString(EMAIL, user.getEmail());
        editor.putString(PASSWORD, user.getPassword());
        editor.putBoolean(IS_ADMIN, user.isAdmin());
        editor.apply();
    }
    public void saveCardInfo(Card card) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("cardName", card.getName());
        editor.putString("cardNumber", card.getNumber());
        editor.putString("cardCvc", card.getCvc());
        editor.putString("month", card.getMonth()+"");
        editor.putString("year", card.getYear()+"");

        editor.apply();
    }

    public User getUserInfo() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(ID, -1),
                sharedPreferences.getString(NAME, null),
                sharedPreferences.getString(EMAIL, null),
                sharedPreferences.getString(PASSWORD, null),
                sharedPreferences.getBoolean(IS_ADMIN, false)
        );
    }
    public Card getCardInfo() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Card(
                sharedPreferences.getString("cardName", null),
                sharedPreferences.getString("cardNumber", null),
                sharedPreferences.getString("cardCvc", null),
                Integer.parseInt(sharedPreferences.getString("month", null)),
                Integer.parseInt(sharedPreferences.getString("year", null)),
                sharedPreferences.getString(PASSWORD, null)
        );
    }


    public String getUserToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TOKEN, "");
    }

    public void clearAll() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
        editor.apply();
    }

}
