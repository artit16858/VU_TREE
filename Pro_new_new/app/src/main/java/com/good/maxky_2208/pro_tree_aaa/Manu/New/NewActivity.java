package com.good.maxky_2208.pro_tree_aaa.Manu.New;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import com.good.maxky_2208.pro_tree_aaa.Manu.New.Game.GameActivity;
import com.good.maxky_2208.pro_tree_aaa.Manu.New.Game.GameTwoActivity;
import com.good.maxky_2208.pro_tree_aaa.Manu.New.TOP5.TopFive;
import com.good.maxky_2208.pro_tree_aaa.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@SuppressWarnings("deprecation")
public class NewActivity extends AppCompatActivity {
    LocalActivityManager mLocalActivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_home);


            mLocalActivityManager = new LocalActivityManager(this, false);
            mLocalActivityManager.dispatchCreate(savedInstanceState);


            TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
            tabHost.setup(mLocalActivityManager);
            tabHost.setup();
            TabHost.TabSpec tabSpec = tabHost.newTabSpec("Game")
                    .setIndicator(getResources().getString(R.string.game))
                    .setContent(new Intent(this, GameActivity.class));
            TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("TOP 5")
                    .setIndicator(getResources().getString(R.string.topfive))
                    .setContent(new Intent(this, TopFive.class));


            tabHost.addTab(tabSpec);
            tabHost.addTab(tabSpec2);




    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocalActivityManager.dispatchPause(!isFinishing());

    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocalActivityManager.dispatchResume();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
    @Override
    public void onBackPressed() {
        new android.app.AlertDialog.Builder(this)
                .setMessage("คุณต้องการออกจากเกม")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        NewActivity.super.onBackPressed();
                    }
                }).create().show();
    }

}
