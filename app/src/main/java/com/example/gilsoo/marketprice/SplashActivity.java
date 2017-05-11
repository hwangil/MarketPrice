package com.example.gilsoo.marketprice;

import android.app.Activity;
import android.os.Bundle;

public class SplashActivity extends Activity {
    public static Activity splashActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashActivity = SplashActivity.this;
    }
}
