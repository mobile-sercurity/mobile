package com.marwaeltayeb.das.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.marwaeltayeb.das.net.RetrofitClient;
import com.marwaeltayeb.das.utils.RequestCallback;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FromCartRepository {

    private static final String TAG = FromCartRepository.class.getSimpleName();

    public LiveData<ResponseBody> removeFromCart(int userId, int cartId, RequestCallback callback) {
        final MutableLiveData<ResponseBody> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().removeFromCart(userId, cartId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG,"onResponse" + response.code());

                if(response.code() == 200){
                    callback.onCallBack();
                }

                ResponseBody responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG,"onFailure" + t.getMessage());
            }
        });

        return mutableLiveData;
    }
}
