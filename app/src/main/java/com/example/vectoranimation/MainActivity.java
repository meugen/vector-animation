package com.example.vectoranimation;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, VectorAnimatorHelper.AnimationCallback {

    private ImageView imageView;

    private VectorAnimatorHelper animatorHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image);
        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        animatorHelper = new VectorAnimatorHelper(imageView,
                R.drawable.animated_splash_step1,
                R.drawable.animated_splash_step2,
                R.drawable.animated_splash_step3,
                R.drawable.animated_splash_step4);
        animatorHelper.setCallback(this);
        animatorHelper.animate();
    }

    @Override
    public void onAnimationStart(final VectorAnimatorHelper helper) { }

    @Override
    public void onAnimationEnd(final VectorAnimatorHelper helper) {
        animatorHelper = null;
    }

    @Override
    protected void onDestroy() {
        if (animatorHelper != null) {
            animatorHelper.stop();
        }
        super.onDestroy();
    }
}
