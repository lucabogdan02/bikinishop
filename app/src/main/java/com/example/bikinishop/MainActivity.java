package com.example.bikinishop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final int key = 69;
    private ImageView imageView;
    Animation zoomout, zoomin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        imageView = findViewById(R.id.imageView);

        zoomout = AnimationUtils.loadAnimation(this, R.anim.zoomout);
        zoomin = AnimationUtils.loadAnimation(this, R.anim.zoomin);

        zoomin.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.startAnimation(zoomout);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        zoomout.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.startAnimation(zoomin);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });

        imageView.startAnimation(zoomin);
    }

    public void onLoginButtonPushed(View view) {
        Intent logInIntent = new Intent(this, LogInActivity.class);
        logInIntent.putExtra("youknowwhatitis", key);
        startActivity(logInIntent);
    }

    public void onSignUpButtonPushed(View view) {
        Intent signUpIntent = new Intent(this, SignUpActivity.class);
        signUpIntent.putExtra("youknowwhatitis", key);
        startActivity(signUpIntent);
    }

    @Override
    protected void onPause() {
        imageView.clearAnimation();
        super.onPause();
    }

    @Override
    protected void onResume() {
        imageView.startAnimation(zoomin);
        super.onResume();
    }
}