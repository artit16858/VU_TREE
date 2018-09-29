package com.good.maxky_2208.pro_tree_aaa.Manu.News;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.good.maxky_2208.pro_tree_aaa.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class NewsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayAdapter<CharSequence> as;
    RecyclerAdapter_News recyclerAdapterNews;

    List<News> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recyclerView = (RecyclerView) findViewById(R.id.recNews);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        getNews();
    }

    private void getNews() {
        final String Host = getResources().getString(R.string.host);

        final String URL = Host + "listnews.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();

                        list = Arrays.asList(gson.fromJson(response, News[].class));
                        recyclerAdapterNews = new RecyclerAdapter_News(getApplicationContext(),list);


                        recyclerView.setAdapter(recyclerAdapterNews);
                    }









                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



        MySingleton_News.getMlsingleton(this).addToRequestQue(stringRequest);
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
                        NewsActivity.super.onBackPressed();
                    }
                }).create().show();
    }

}
