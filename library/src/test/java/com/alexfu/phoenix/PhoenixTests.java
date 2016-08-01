package com.alexfu.phoenix;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static android.content.Context.MODE_PRIVATE;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.JELLY_BEAN)
public final class PhoenixTests {

  private Phoenix.Callback callback;
  private TestActivity activity;
  private PackageInfo packageInfo;

  private static final String FILE_NAME = "com.alexfu.phoenix";
  private static final String KEY_LAST_VERSION_CODE = "lastVersionCode";

  @Before
  public void beforeEach() throws PackageManager.NameNotFoundException {
    activity = spy(Robolectric.setupActivity(TestActivity.class));
    callback = mock(Phoenix.Callback.class);

    PackageManager packageManager = mock(PackageManager.class);
    packageInfo = mock(PackageInfo.class);

    doReturn(packageInfo).when(packageManager).getPackageInfo(anyString(), anyInt());
    doReturn(packageManager).when(activity).getPackageManager();
  }

  @Test
  public void doesNotTriggerOnUpdateWithFirstCall() {
    // Arrange
    packageInfo.versionCode = 1;

    // Act
    Phoenix.rise(activity, callback);

    // Verify
    Mockito.verify(callback, never()).onUpdate(anyInt(), anyInt());
  }

  @Test
  public void triggersOnUpdate() {
    // Arrange
    packageInfo.versionCode = 2;
    SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
    prefs.edit().putInt(KEY_LAST_VERSION_CODE, 1).commit();

    // Act
    Phoenix.rise(activity, callback);

    // Verify
    Mockito.verify(callback, times(1)).onUpdate(anyInt(), anyInt());
  }

  @Test
  public void doesNotTriggerOnUpdateWithNullCallback() {
    // Arrange
    packageInfo.versionCode = 2;
    SharedPreferences prefs = activity.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
    prefs.edit().putInt(KEY_LAST_VERSION_CODE, 1).commit();

    // Act
    Phoenix.rise(activity, null);

    // Verify
    Mockito.verify(callback, never()).onUpdate(anyInt(), anyInt());
  }
}
