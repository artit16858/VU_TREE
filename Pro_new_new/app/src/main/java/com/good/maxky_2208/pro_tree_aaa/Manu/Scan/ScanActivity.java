package com.good.maxky_2208.pro_tree_aaa.Manu.Scan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.good.maxky_2208.pro_tree_aaa.R;
import com.good.maxky_2208.pro_tree_aaa.Session;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);


            Intent i = new Intent(this, DecoderActivity.class);
            startActivity(i);
            finish();

    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
