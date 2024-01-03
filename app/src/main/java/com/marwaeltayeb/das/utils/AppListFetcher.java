package com.marwaeltayeb.das.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import java.util.ArrayList;
import java.util.List;

public class AppListFetcher {

    public List<String> getAllInstalledApps(PackageManager packageManager) {
        List<String> appList = new ArrayList<>();

        // Get a list of all installed applications
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            // Add the package name to the appList
            appList.add(packageInfo.packageName);
        }

        return appList;
    }
}

