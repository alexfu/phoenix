package com.alexfu.phoenix;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

class UpdateAsyncTask extends AsyncTask<Void, Void, VersionHistory> {
    private static final String FILE_NAME = "com.alexfu.phoenix";
    private static final String KEY_LAST_VERSION_CODE = "lastVersionCode";

    private final Context context;
    private final Phoenix.Callback callback;
    private final SharedPreferences preferences;

    UpdateAsyncTask(Context context, Phoenix.Callback callback) {
        this.context = context;
        this.callback = callback;
        preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    @Override protected VersionHistory doInBackground(Void... voids) {
        int lastVersionCode = preferences.getInt(KEY_LAST_VERSION_CODE, -1);
        int currentVersionCode = getVersionCode(context);
        return new VersionHistory(lastVersionCode, currentVersionCode);
    }

    @Override protected void onPostExecute(VersionHistory versionHistory) {
        int oldVersion = versionHistory.lastVersionCode;
        int newVersion = versionHistory.currentVersionCode;
        if (newVersion != oldVersion) {
            preferences.edit()
                    .putInt(KEY_LAST_VERSION_CODE, newVersion)
                    .apply();
            if (callback != null) {
                callback.onUpdate(oldVersion, newVersion);
            }
        }
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
