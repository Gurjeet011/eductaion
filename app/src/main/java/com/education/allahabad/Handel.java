package com.education.allahabad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

public class Handel extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
    private static final int SPLASH_DURATION = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*startActivity(new Intent(getApplicationContext(), Login.class));
                finish();*/

                SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
                String user = sharedPreferences.getString("userid",null);
                if(user==null){
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                }
                else {
                    startActivity(new Intent(getApplicationContext(), Homepage.class));
                    finish();
                }
            }
        },SPLASH_DURATION);
    }

}