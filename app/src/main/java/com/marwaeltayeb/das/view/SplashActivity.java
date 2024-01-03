package com.marwaeltayeb.das.view;

import static com.marwaeltayeb.das.storage.LanguageUtils.loadLocale;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.marwaeltayeb.das.R;
import com.marwaeltayeb.das.model.Malware;
import com.marwaeltayeb.das.storage.LoginUtils;
import com.marwaeltayeb.das.utils.AppListFetcher;
import com.marwaeltayeb.das.viewmodel.SplashViewModel;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 1000;
    private static final String CHANNEL_ID = "Your_Channel_Id";
    private SplashViewModel splashViewModel;
    private List<String> listMalware;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        setContentView(R.layout.activity_splash);
        splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        listMalware = new ArrayList();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            checkMalware();
            Intent i = new Intent(SplashActivity.this, ProductActivity.class);
            startActivity(i);

//             Close this activity
            finish();
            // If user does not log in before, go to LoginActivity
            if(!LoginUtils.getInstance(SplashActivity.this).isLoggedIn()) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }

        }, SPLASH_TIME_OUT);
    }

    private void checkMalware() {
        PackageManager packageManager = getPackageManager();
        AppListFetcher appListFetcher = new AppListFetcher();
        List<String> installedApps = appListFetcher.getAllInstalledApps(packageManager);

        splashViewModel.getMalware().observe(this, malwareApiResponse -> {
            if (malwareApiResponse != null) {
                List<Malware> malwarePackageNames = malwareApiResponse.getMalware();
                Log.d("malwarePackageNames", String.valueOf(malwarePackageNames.get(0)));
                for (Malware malware: malwarePackageNames) {
                    Log.d("malware", String.valueOf(malware));
                    listMalware.add(malware.getPackageName());
                }
                Log.d("listMalware", String.valueOf(listMalware));
                for (String packageName : installedApps) {
                    if (packageName.contains("com.android") || packageName.contains("com.google") || packageName.contains("com.marwaeltayeb")){
                        continue;
                    }
                    Log.d("InstalledApp", "Package Name: " + packageName);
                    if (isUnsafeApp(packageName)) {
                        notifyUser("Unsafe App Detected", "The app with package name " + packageName + " is considered unsafe.");
                    }
                }
            }
        });

    }
    private boolean isUnsafeApp(String packageName) {
        // Replace this with your logic to determine if an app is unsafe
        return listMalware.contains(packageName);
    }

    private void notifyUser(String title, String content) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel for devices running Android Oreo (API 26) and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Your_Channel_Name",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        // Build the notification
        Notification.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_info)
                    .setContentTitle(title)
                    .setContentText(content);
        }

        // Show the notification
        notificationManager.notify(1, builder.build());
    }
}
