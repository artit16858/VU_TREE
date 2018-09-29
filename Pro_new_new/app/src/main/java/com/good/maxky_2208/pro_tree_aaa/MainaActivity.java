package com.good.maxky_2208.pro_tree_aaa;

import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.good.maxky_2208.pro_tree_aaa.Login.Login;
import com.good.maxky_2208.pro_tree_aaa.Manu.New.Games;
import com.good.maxky_2208.pro_tree_aaa.Manu.New.NewActivity;
import com.good.maxky_2208.pro_tree_aaa.Manu.News.NewsActivity;
import com.good.maxky_2208.pro_tree_aaa.Manu.Scan.ScanActivity;
import com.good.maxky_2208.pro_tree_aaa.Manu.Trees.TreeActivity;
import com.good.maxky_2208.pro_tree_aaa.Manu.User.ProfileActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@SuppressWarnings("deprecation")
public class MainaActivity extends AppCompatActivity {
    LocalActivityManager mLocalActivityManager;
    private Session session;
    public static final String FIEFS = "ex";

    private String userString;

   // private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_maina);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


                session = new Session(this);
                setSupportActionBar(toolbar);
                View content = getLayoutInflater().inflate(R.layout.nav_header_maina, null, true);
                TextView t_name = (TextView) content.findViewById(R.id.txt_sss);
                SharedPreferences message = getSharedPreferences(FIEFS, 0);
                userString = message.getString("userMessage", "not fond");

                Log.d("name", userString);
                t_name.setText(userString);


                mLocalActivityManager = new LocalActivityManager(this, false);
                mLocalActivityManager.dispatchCreate(savedInstanceState);

                if (!session.Unuser()) {
                    TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
                    tabHost.setup(mLocalActivityManager);
                    tabHost.setup();
                    TabHost.TabSpec tabSpec = tabHost.newTabSpec("tab1")
                            .setIndicator(getString(R.string.news), getResources().getDrawable(R.drawable.ic_action_name))
                            .setContent(new Intent(this, NewsActivity.class));
                    TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("tab2")
                            .setIndicator(getString(R.string.tree), getResources().getDrawable(R.drawable.ic_action_tree))
                            .setContent(new Intent(this, TreeActivity.class));
                    TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("tab3")
                            .setIndicator("", getResources().getDrawable(R.drawable.ic_action_barcode))
                            .setContent(new Intent(this, ScanActivity.class));
                    TabHost.TabSpec tabSpec4 = tabHost.newTabSpec("tab4")
                            .setIndicator(getString(R.string.game), getResources().getDrawable(R.drawable.ic_action_game))
                            .setContent(new Intent(this, Games.class));
                    TabHost.TabSpec tabSpec5 = tabHost.newTabSpec("tab5")
                            .setIndicator(getString(R.string.me), getResources().getDrawable(R.drawable.ic_action_pro))
                            .setContent(new Intent(this, ProfileActivity.class));
                    tabHost.addTab(tabSpec);
                    tabHost.addTab(tabSpec2);
                    tabHost.addTab(tabSpec3);
                    tabHost.addTab(tabSpec4);
                    tabHost.addTab(tabSpec5);
                } else {

                    final android.support.v7.app.AlertDialog.Builder adb = new android.support.v7.app.AlertDialog.Builder(this);
                    TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
                    tabHost.setup(mLocalActivityManager);
                    tabHost.setup();
                    TabHost.TabSpec tabSpec = tabHost.newTabSpec("tab1")
                            .setIndicator(getString(R.string.news), getResources().getDrawable(R.drawable.ic_action_name))
                            .setContent(new Intent(this, NewsActivity.class));
                    TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("tab2")
                            .setIndicator(getString(R.string.tree), getResources().getDrawable(R.drawable.ic_action_tree))
                            .setContent(new Intent(this, TreeActivity.class));
                    TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("tab3")
                            .setIndicator("", getResources().getDrawable(R.drawable.ic_action_barcode))
                            .setContent(new Intent(this, Unuser.class));
                    TabHost.TabSpec tabSpec4 = tabHost.newTabSpec("tab4")
                            .setIndicator(getString(R.string.game), getResources().getDrawable(R.drawable.ic_action_game))
                            .setContent(new Intent(this, Unuser.class));
                    TabHost.TabSpec tabSpec5 = tabHost.newTabSpec("tab5")
                            .setIndicator(getString(R.string.me), getResources().getDrawable(R.drawable.ic_action_pro))
                            .setContent(new Intent(this, Unuser.class));
                    tabHost.addTab(tabSpec);
                    tabHost.addTab(tabSpec2);
                    tabHost.addTab(tabSpec3);
                    tabHost.addTab(tabSpec4);
                    tabHost.addTab(tabSpec5);


                }


                if (!session.loggedin()) {
                    logout();
                }

    }

    private void logout() {
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(MainaActivity.this, Login.class));
    }

    /*  @Override
      public void onBackPressed() {
          DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
          if (drawer.isDrawerOpen(GravityCompat.START)) {
              drawer.closeDrawer(GravityCompat.START);
          } else {
              super.onBackPressed();
          }
      }
  */
   /* @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
/*        View v= LayoutInflater.from(getApplicationContext()).inflate(R.layout.nav_header_maina,null);

        TextView t_name = (TextView) v.findViewById(R.id.txt_sss);
        SharedPreferences message = getSharedPreferences(FIEFS, 0);
        userString = message.getString("userMessage", "not fond");
        Log.d("name",userString);
        t_name.setText(userString);*/
    // Handle navigation view item clicks here.
    /*
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(MainaActivity.this, EditPro.class));
        } else if (id == R.id.nav_setting) {
            AlertLanguage();


        } else if (id == R.id.nav_Comment) {
            startActivity(new Intent(MainaActivity.this, Comment.class));
        } else if (id == R.id.nav_logout) {
            session.setLoggedin(false);
            session.setLoggedinface(false);
            finish();
            LoginManager.getInstance().logOut();
            startActivity(new Intent(MainaActivity.this, Login.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void AlertLanguage() {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(MainaActivity.this);
        builder.setTitle("Select  Upload Images");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(CLUBS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selected = CLUBS[which];

                if(selected=="Thai"){
                    Configuration config = new Configuration();
                    config.locale = new Locale("th");

                    getResources().updateConfiguration(config, null);
                    // showCammara();

                    finish();
                    startActivity(getIntent());
                }
                if(selected=="English"){
                    Configuration config = new Configuration();
                    config.locale = Locale.ENGLISH;
                    getResources().updateConfiguration(config, null);
                    //showFileChooser();
                    finish();
                    startActivity(getIntent());
                }

                // Toast.makeText(getApplicationContext(), "คุณชอบ " + selected, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Close", null);
        builder.create();
      builder.show();



    }
*/

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
        new AlertDialog.Builder(this)
                .setMessage("คุณต้องการปิดโปรแกรม")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainaActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}
