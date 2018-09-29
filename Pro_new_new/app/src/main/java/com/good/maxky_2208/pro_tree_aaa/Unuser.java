package com.good.maxky_2208.pro_tree_aaa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.good.maxky_2208.pro_tree_aaa.Login.Login;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Unuser extends AppCompatActivity {
    Button login;

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unuser);
        session = new Session(this);
        login = (Button) findViewById(R.id.bc_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setUnuser(false);
                session.setLoggedin(false);
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        });
    }
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("คุณต้องการปิดโปรแกรม")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Unuser.super.onBackPressed();
                    }
                }).create().show();
    }
}
