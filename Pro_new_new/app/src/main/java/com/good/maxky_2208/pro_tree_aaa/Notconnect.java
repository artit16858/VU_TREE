package com.good.maxky_2208.pro_tree_aaa;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Notconnect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notconnect);
        Button code2 = (Button) findViewById(R.id.code);
        code2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                Intent i = new Intent(Notconnect.this, MainaActivity.class);
                startActivity(i);
                finish();


            } else {


            }

        }
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Notconnect.this);
        View mView = getLayoutInflater().inflate(R.layout.notconnect, null);
        final    Button code = (Button)mView. findViewById(R.id.code);



        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
