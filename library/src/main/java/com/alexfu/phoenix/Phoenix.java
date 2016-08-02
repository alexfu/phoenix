package com.alexfu.phoenix;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class Phoenix {
  private static final String FILE_NAME = "com.alexfu.phoenix";
  private static final String KEY_LAST_VERSION_CODE = "lastVersionCode";

  public interface Callback {
    void onUpdate(int oldVersion, int newVersion);
  }

  public static <T extends Context & Callback> void rise(T activity) {
        SharedPreferences preferences = getPreferences(activity);

        int lastVersionCode = preferences.getInt(KEY_LAST_VERSION_CODE, -1);
        int currentVersionCode = getVersionCode(activity);

        if (currentVersionCode != lastVersionCode) {
            if (lastVersionCode != -1 && activity != null) {
                activity.onUpdate(lastVersionCode, currentVersionCode);
            }
            preferences.edit()
                    .putInt(KEY_LAST_VERSION_CODE, currentVersionCode)
                    .apply();
        }
    }

  private static SharedPreferences getPreferences(Context context) {
    return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
  }

  private static int getVersionCode(Context context) {
    try {
      PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      return packageInfo.versionCode;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return -1;
  }
}
