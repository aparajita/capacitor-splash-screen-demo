package com.aparajita.capacitor.splashscreendemo;

import android.os.Bundle;

import com.aparajita.capacitor.splashscreen.SplashScreen;
import com.getcapacitor.BridgeActivity;

import java.util.HashMap;

public class MainActivity extends BridgeActivity {
  private final SplashScreenAnimator animator = new SplashScreenAnimator();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    SplashScreen.initLaunchTime();
    super.onCreate(savedInstanceState);
  }

  public void onSplashScreenEvent(SplashScreen.HookEventType event, HashMap<String, Object> params) {
    animator.onSplashScreenEvent(event, params);
  }
}
