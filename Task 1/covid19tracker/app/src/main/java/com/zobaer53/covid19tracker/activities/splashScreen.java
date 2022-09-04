package com.zobaer53.covid19tracker.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.zobaer53.covid19tracker.R;

@SuppressLint("CustomSplashScreen")
public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        LottieAnimationView lottieAnimationView = findViewById(R.id.SplashScreenImage);
        lottieAnimationView.playAnimation();
        TextView textView = findViewById(R.id.textView2);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/carbonbl.ttf");
        textView.setTypeface(typeface);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(splashScreen.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        },5000);
    }
}