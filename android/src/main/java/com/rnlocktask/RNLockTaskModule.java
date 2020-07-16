
package com.rnlocktask;

import android.app.Activity;
import android.view.WindowManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNLockTaskModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private static final String REMOTE_ASSIST_PACKAGE = "com.logmein.rescueassist";


  public RNLockTaskModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNLockTask";
  }

  @ReactMethod
  public void clearDeviceOwnerApp() {
    try {
      Activity mActivity = reactContext.getCurrentActivity();
      if (mActivity != null) {
        DevicePolicyManager myDevicePolicyManager = (DevicePolicyManager) mActivity.getSystemService(Context.DEVICE_POLICY_SERVICE);
        myDevicePolicyManager.clearDeviceOwnerApp(mActivity.getPackageName());
      }
    } catch (Exception e) {
    }
  }

  @ReactMethod
  public void startLockTask() {
    try {
      Activity mActivity = reactContext.getCurrentActivity();
      if (mActivity != null) {
        DevicePolicyManager myDevicePolicyManager = (DevicePolicyManager) mActivity.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName mDPM = new ComponentName(mActivity, MyAdmin.class);

        if (myDevicePolicyManager.isDeviceOwnerApp(mActivity.getPackageName())) {
          String[] packages = {mActivity.getPackageName(), REMOTE_ASSIST_PACKAGE};
          myDevicePolicyManager.setLockTaskPackages(mDPM, packages);
          mActivity.startLockTask();
        } else {
          mActivity.startLockTask();
        }
      }
    } catch (Exception e) {
    }
  }

  @ReactMethod
  public void stopLockTask() {
    try {
      Activity mActivity = reactContext.getCurrentActivity();
      if (mActivity != null) {
        mActivity.stopLockTask();
      }
    } catch (Exception e) {
    }
  }
}
