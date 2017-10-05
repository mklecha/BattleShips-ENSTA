package com.excilys.formation.battleships.android.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import battleships.formation.excilys.com.battleships.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static int TIME_OUT = 3000; //Time to launch the another activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, PlayerNameActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);
    }
}
