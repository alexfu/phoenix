# Phoenix

Phoenix is a small library that offers update hooks for when a user updates your app. This is useful for when you need to handle some logic when updating from version to version.

**NOTE**: Since v1.0.0, Phoenix operates asynchronously, however, `Phoenix.Callback` is still executed on the main thread.

# Usage

```java
public MyApplication extends Application implements Phoenix.Callback {
  @Override
  public void onCreate() {
    super.onCreate();
    Phoenix.rise(this, this);
  }

  @Override
  public void onUpdate(int oldVersion, int newVersion) {
    // Handle logic here, such as migrating SharedPreferences
  }
}
```

# Installation

```groovy
buildscript {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}

dependencies {
  compile 'com.github.alexfu:Phoenix:1.0.0'
}
```
