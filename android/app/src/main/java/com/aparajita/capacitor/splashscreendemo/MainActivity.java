package com.aparajita.capacitor.splashscreendemo;

import android.os.Bundle;

import com.getcapacitor.BridgeActivity;
import com.getcapacitor.Plugin;
import com.aparajita.capacitor.splashscreen.WSSplashScreen;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends BridgeActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.launch_screen);

    // Initializes the Bridge
    this.init(savedInstanceState, new ArrayList<Class<? extends Plugin>>() {{
      // Additional plugins you've installed go here
      add(WSSplashScreen.class);
    }});
  }

  public void onSplashScreenEvent(String event, HashMap<String, Object> params) {
    SplashScreen splashscreen = SplashScreen.getInstance();
    splashscreen.onSplashScreenEvent(event, params);
  }
}
