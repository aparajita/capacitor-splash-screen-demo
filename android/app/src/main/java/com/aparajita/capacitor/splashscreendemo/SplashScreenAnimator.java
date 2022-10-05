package com.aparajita.capacitor.splashscreendemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.HashMap;

import com.aparajita.capacitor.splashscreen.Config;
import com.aparajita.capacitor.splashscreen.AnimationCallbacks;
import com.aparajita.capacitor.splashscreen.SplashScreen;
import com.getcapacitor.JSObject;

public class SplashScreenAnimator {
  static final double DEFAULT_ANIMATION_DURATION = 0.7;

  // Triplets of scaling parameters: scale factor, relative start delay, relative duration
  static final float[] SCALE_DOWN_ANIMATION = {0.7f, 0f, 0.3f};
  static final float[] SCALE_UP_ANIMATION = {5f, 0.3f, 0.7f};

  // Pair of animation parameters: relative start delay, relative duration
  static final float[] FADE_OUT_ANIMATION = {0.3f, 0.5f};

  public void onSplashScreenEvent(SplashScreen.HookEventType event, HashMap<String, Object> params) {
    animate(params);
  }

  private void animate(HashMap<String, Object> params) {
    AnimationCallbacks callbacks = null;
    View splashView = null;
    View iconView = null;
    long duration = SplashScreen.toMilliseconds(DEFAULT_ANIMATION_DURATION);

    try {
      splashView = (View) params.get("splashView");
      iconView = (View) params.get("iconView");
      callbacks = (AnimationCallbacks) params.get("callbacks");
      JSObject options = (JSObject) params.get("options");
      Config config = (Config) params.get("config");

      if (options != null && config != null) {
        duration = SplashScreen.toMilliseconds(config.getDoubleOption("animationDuration", options, DEFAULT_ANIMATION_DURATION));

        // Prove that we can pass arbitrary data to animate()
        Log.i(
            "SplashScreenDemo",
            String.format("foo = %s", options.optString("foo", "null"))
        );
      }
    } catch (Exception ex) {
      // Handle it below
    }

    if (splashView == null || iconView == null) {
      if (callbacks != null) {
        callbacks.error("Null splash or icon view", SplashScreen.ErrorType.NO_SPLASH);
      }

      return;
    }

    // First scale the logo down a bit
    AnimatorSet scaleDown = makeScaleAnimation(iconView, duration, SCALE_DOWN_ANIMATION);

    // Then scale it way up
    AnimatorSet scaleUp = makeScaleAnimation(iconView, duration, SCALE_UP_ANIMATION);

    // While it's scaling up, fade out the whole splash
    Animator fade = ObjectAnimator.ofFloat(splashView, "alpha", 1f, 0f);
    fade.setStartDelay((long) (duration * FADE_OUT_ANIMATION[0]));
    fade.setDuration((long) (duration * FADE_OUT_ANIMATION[1]));

    // Now put them all together and start the animation
    AnimatorSet set = new AnimatorSet();
    set.setInterpolator(new LinearInterpolator());
    set.playTogether(scaleDown, scaleUp, fade);

    final AnimationCallbacks finalCallbacks = callbacks;
    final View finalIconView = iconView;

    set.addListener(new Animator.AnimatorListener() {
      private void done() {
        // Restore the logo to it's original scale for the next run
        finalIconView.setScaleX(1f);
        finalIconView.setScaleY(1f);

        if (finalCallbacks != null) {
          finalCallbacks.done();
        }
      }

      @Override
      public void onAnimationStart(Animator animation) {
        if (finalCallbacks != null) {
          finalCallbacks.showStatusBar(fade.getStartDelay(), fade.getDuration(), null);
          finalCallbacks.showNavigationBar(fade.getStartDelay(), fade.getDuration(), null);
        }
      }

      @Override
      public void onAnimationEnd(Animator animation) {
        done();
      }

      @Override
      public void onAnimationCancel(Animator animation) {
        done();
      }

      @Override
      public void onAnimationRepeat(Animator animation) {
      }
    });

    set.start();
  }

  private AnimatorSet makeScaleAnimation(View iconView, float duration, float[] animationParams) {
    float scaleFactor = animationParams[0];
    Animator scaleDownX = ObjectAnimator.ofFloat(iconView, "scaleX", scaleFactor);
    Animator scaleDownY = ObjectAnimator.ofFloat(iconView, "scaleY", scaleFactor);

    float startDelay = animationParams[1];
    float relativeDuration = animationParams[2];

    AnimatorSet scale = new AnimatorSet();
    scale.playTogether(scaleDownX, scaleDownY);
    scale.setStartDelay((long) (duration * startDelay));
    scale.setDuration((long) (duration * relativeDuration));

    return scale;
  }
}
