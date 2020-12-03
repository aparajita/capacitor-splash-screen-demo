package com.aparajita.capacitor.splashscreendemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.HashMap;

import com.getcapacitor.JSObject;

public class SplashScreen {
    static final int DEFAULT_ANIMATION_DURATION = 700;

    // Triplets of scaling parameters: scale factor, relative start delay, relative duration
    static final float[] SCALE_DOWN_ANIMATION = {0.7f, 0f, 0.3f};
    static final float[] SCALE_UP_ANIMATION = {5f, 0.3f, 0.7f};

    // Pair of animation parameters: relative start delay, relative duration
    static final float[] FADE_OUT_ANIMATION = {0.8f, 0.2f};

    @SuppressLint("StaticFieldLeak")
    static SplashScreen instance = null;

    Runnable done = null;
    View logo = null;

    static SplashScreen getInstance() {
        if (instance == null) {
            instance = new SplashScreen();
        }

        return instance;
    }

    public void onSplashScreenEvent(String event, HashMap<String, Object> params) {
        switch (event) {
            case "beforeShow":
                onBeforeShow(params);
                break;

            case "afterShow":
                onAfterShow(params);
                break;

            case "animate":
                animate(params);
                break;
        }
    }

    public void onBeforeShow(HashMap<String, Object> params) {
        View splashView = null;
        View spinner = null;

        try {
            splashView = (View) params.get("splashView");
            spinner = (View) params.get("spinner");
        } catch (Exception ex) {
            // Handle it below
        }

        if (splashView != null) {
            logo = splashView.findViewById(R.id.logo);
        }

        // If the splash screen is full screen, we set the background of the theme to white.
        // But Android messes up our views in that case, giving them a white background.
        // So we have to remove the logo and spinner background color.
        if (logo != null) {
            logo.setBackgroundColor(0);
        }

        if (spinner != null) {
            spinner.setBackgroundColor(0);
        }
    }

    public void onAfterShow(HashMap<String, Object> params) {
    }

    public void animate(HashMap<String, Object> params) {
        View splashView = null;
        JSObject options = null;

        try {
            options = (JSObject) params.get("options");
            splashView = (View) params.get("splashView");
            done = (Runnable) params.get("done");
        } catch (Exception ex) {
            // Handle it below
        }

        if (splashView != null) {
            logo = splashView.findViewById(R.id.logo);
        }

        if (logo == null) {
            if (done != null) {
                done.run();
            }

            return;
        }

        int duration = DEFAULT_ANIMATION_DURATION;

        if (options != null) {
            options.optInt("duration", DEFAULT_ANIMATION_DURATION);
        }

        // First scale the logo down a bit
        AnimatorSet scaleDown = makeScaleAnimation(duration, SCALE_DOWN_ANIMATION);

        // Then scale it way up
        AnimatorSet scaleUp = makeScaleAnimation(duration, SCALE_UP_ANIMATION);

        // While it's scaling up, fade out the whole splash
        Animator fade = ObjectAnimator.ofFloat(splashView, "alpha", 1f, 0f);
        fade.setStartDelay((long) (duration * FADE_OUT_ANIMATION[0]));
        fade.setDuration((long) (duration * FADE_OUT_ANIMATION[1]));

        // Now put them all together and start the animation
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new LinearInterpolator());
        set.playTogether(scaleDown, scaleUp, fade);

        set.addListener(new Animator.AnimatorListener() {
            private void done() {
                // Restore the logo to it's original scale for the next run
                logo.setScaleX(1f);
                logo.setScaleY(1f);
                done.run();
            }

            @Override
            public void onAnimationStart(Animator animation) {

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

    private AnimatorSet makeScaleAnimation(float duration, float[] animationParams) {
        float scaleFactor = animationParams[0];
        Animator scaleDownX = ObjectAnimator.ofFloat(logo, "scaleX", scaleFactor);
        Animator scaleDownY = ObjectAnimator.ofFloat(logo, "scaleY", scaleFactor);

        float startDelay = animationParams[1];
        float relativeDuration = animationParams[2];

        AnimatorSet scale = new AnimatorSet();
        scale.playTogether(scaleDownX, scaleDownY);
        scale.setStartDelay((long) (duration * startDelay));
        scale.setDuration((long) (duration * relativeDuration));

        return scale;
    }
}
