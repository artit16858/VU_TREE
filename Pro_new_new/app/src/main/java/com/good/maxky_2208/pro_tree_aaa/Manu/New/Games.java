package com.good.maxky_2208.pro_tree_aaa.Manu.New;

import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.good.maxky_2208.pro_tree_aaa.Howtoplay;

import com.good.maxky_2208.pro_tree_aaa.Manu.New.Game.GameOneActivity;
import com.good.maxky_2208.pro_tree_aaa.Manu.New.Game.GameTwoActivity;
import com.good.maxky_2208.pro_tree_aaa.Manu.New.TOP5.Adapter_tree;
import com.good.maxky_2208.pro_tree_aaa.Manu.New.TOP5.Adapter_user;
import com.good.maxky_2208.pro_tree_aaa.Manu.New.TOP5.contacts_tree;
import com.good.maxky_2208.pro_tree_aaa.Manu.New.TOP5.contacts_user;
import com.good.maxky_2208.pro_tree_aaa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Games extends AppCompatActivity {
    Adapter_user adapter_user;
    Adapter_tree adapter_tree;
    ListView listuser, listtree;
    String name, name2;
    Button btn_game1, btn_game2, howtoplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        TabHost tabHost =(TabHost)findViewById(R.id.hot);
        tabHost.setup();
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(getResources().getString(R.string.game));
        tabSpec.setContent(R.id.tab1);
      tabSpec.setIndicator("",getResources().getDrawable(R.drawable.ic_action_game));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(getResources().getString(R.string.topfive));
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("",getResources().getDrawable(R.drawable.ic_action_top));
        tabHost.addTab(tabSpec);
        btn_game1 = (Button) findViewById(R.id.btn_game_1);
        btn_game2 = (Button) findViewById(R.id.btn_game_2);
        howtoplay = (Button) findViewById(R.id.howto);
        btn_game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Games.this, GameOneActivity.class);
                startActivity(i);
            }
        });
        btn_game2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Games.this, GameTwoActivity.class);
                startActivity(i);
            }
        });
        howtoplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Games.this, Howtoplay.class);
                startActivity(i);
            }
        });

        adapter_user = new Adapter_user(this, R.layout.grid_item);
        listuser = (ListView) findViewById(R.id.list_user_top);
        listuser.setAdapter(adapter_user);
        adapter_tree = new Adapter_tree(this, R.layout.row);
        listtree = (ListView) findViewById(R.id.list_tree_top);
        listtree.setAdapter(adapter_tree);
        uu();
        kk();
    }

    private void kk() {
        final String Host = getResources().getString(R.string.host);
        final String URL = Host + "showtreetop.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //  progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("tree");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);

                                name = o.getString("tree_name");
                                name2 = o.getString("tree_rating");

                                contacts_tree contacts = new contacts_tree(name, name2);
                                adapter_tree.add(contacts);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }


                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void uu() {
        //final ProgressDialog progressDialog = new ProgressDialog(this);
        //progressDialog.setMessage("Loading data...");
        //progressDialog.show();
        final String Host = getResources().getString(R.string.host);
        final String URL = Host + "showusertop.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //  progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("tree");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);

                                name = o.getString("user_username");
                                name2 = o.getString("user_score");

                                contacts_user contacts = new contacts_user(name, "Score : " + name2);
                                adapter_user.add(contacts);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }


                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

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
                        Games.super.onBackPressed();
                    }
                }).create().show();
    }
}
