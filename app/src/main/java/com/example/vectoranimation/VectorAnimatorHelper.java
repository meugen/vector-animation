package com.example.vectoranimation;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

public class VectorAnimatorHelper {

    private final ImageView imageView;
    private final int[] vectorAnimationIds;

    private AnimationCallback callback;
    private int currentAnimationIndex = -1;
    private AnimatedVectorDrawableCompat currentDrawable;

    public VectorAnimatorHelper(
            final ImageView imageView,
            final int... vectorAnimationIds) {
        this.imageView = imageView;
        this.vectorAnimationIds = vectorAnimationIds;
    }

    private void checkAnimationStarted() {
        if (currentAnimationIndex >= 0) {
            throw new IllegalArgumentException("Animation is already started");
        }
    }

    public void setCallback(final AnimationCallback callback) {
        checkAnimationStarted();
        this.callback = callback;
    }

    private void postAnimationStart() {
        if (callback != null) {
            callback.onAnimationStart(this);
        }
    }

    private void postAnimationEnd() {
        if (callback != null) {
            callback.onAnimationEnd(this);
        }
    }

    public void animate() {
        checkAnimationStarted();
        startNextAnimation();
    }

    public void stop() {
        if (currentDrawable == null) {
            return;
        }
        currentDrawable.stop();
    }

    private boolean startNextAnimation() {
        currentAnimationIndex++;
        if (currentAnimationIndex >= vectorAnimationIds.length) {
            return false;
        }
        currentDrawable = AnimatedVectorDrawableCompat.create(
                imageView.getContext(), vectorAnimationIds[currentAnimationIndex]);
        if (currentDrawable == null) {
            return false;
        }
        imageView.setImageDrawable(currentDrawable);
        currentDrawable.registerAnimationCallback(new VectorAnimationCallback());
        currentDrawable.start();
        return true;
    }

    private class VectorAnimationCallback extends Animatable2Compat.AnimationCallback {

        @Override
        public void onAnimationStart(final Drawable drawable) {
            super.onAnimationStart(drawable);
            if (currentAnimationIndex == 0) {
                postAnimationStart();
            }
        }

        @Override
        public void onAnimationEnd(final Drawable drawable) {
            super.onAnimationEnd(drawable);
            if (!startNextAnimation()) {
                postAnimationEnd();
            }
        }
    }

    public interface AnimationCallback {

        void onAnimationStart(VectorAnimatorHelper helper);

        void onAnimationEnd(VectorAnimatorHelper helper);
    }
}
