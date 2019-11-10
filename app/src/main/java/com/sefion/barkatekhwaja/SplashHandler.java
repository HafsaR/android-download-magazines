package com.sefion.barkatekhwaja;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Hafsa on 2/4/2018.
 */

public class SplashHandler extends Activity {
    private static int SPLASH_TIME_OUT = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashHandler.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        }, SPLASH_TIME_OUT);
    }
}
