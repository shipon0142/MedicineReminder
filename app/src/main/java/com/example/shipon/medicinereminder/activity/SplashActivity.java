package com.example.shipon.medicinereminder.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.shipon.medicinereminder.R;

import java.util.Timer;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView  im=findViewById(R.id.sp);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.myanim);
        im.startAnimation(animation);
        new Thread() {
            public void run() {
                try {
                    sleep(4000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    Intent iuu = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(iuu);
                    finish();
                }
            }
        }.start();
    }
}
