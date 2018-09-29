package com.good.maxky_2208.pro_tree_aaa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Splash_Screen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                }

                    Intent intent = new Intent(Splash_Screen.this, Notconnect.class);
                    startActivity(intent);
                    finish();


            }
        }).start();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
