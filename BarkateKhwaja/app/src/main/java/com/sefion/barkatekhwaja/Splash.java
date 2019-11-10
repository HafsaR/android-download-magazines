package com.sefion.barkatekhwaja;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Window;

/**
 * Created by Hafsa on 2/4/2018.
 */

public class Splash extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);

        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent openMain = new Intent(Splash.this, MainActivity.class);
                    startActivity(openMain);
                    finish();
                }

            }
        };
        timer.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
