package com.alexfu.phoenix;

import android.content.Context;

public class Phoenix {
  public interface Callback {
    void onUpdate(int oldVersion, int newVersion);
  }

  public static void rise(final Context context, final Callback callback) {
    new UpdateAsyncTask(context, callback).execute();
  }
}
