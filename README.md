# Phoenix

Phoenix is a very small library (45 LOC) that offers update hooks for when a user updates your app. This is useful for when you need to handle some logic when updating from version to version.

# Usage

```java
public MyApplication extends Application implements Phoenix.Callback {
  Phoenix.rise(this, this)
}

@Override
public void onUpdate(int oldVersion, int newVersion) {
  // Handle logic here, such as migrating SharedPreferences
}
```
