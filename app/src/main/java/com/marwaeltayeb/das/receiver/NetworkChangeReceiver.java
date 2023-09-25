package com.marwaeltayeb.das.receiver;

import static com.marwaeltayeb.das.utils.InternetUtils.isNetworkConnected;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.marwaeltayeb.das.utils.OnNetworkListener;

public class NetworkChangeReceiver extends BroadcastReceiver {

    OnNetworkListener onNetworkListener;

    public void setOnNetworkListener(OnNetworkListener onNetworkListener) {
        this.onNetworkListener = onNetworkListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!isNetworkConnected(context)) {
            onNetworkListener.onNetworkDisconnected();
        } else {
            onNetworkListener.onNetworkConnected();
        }
    }
}
