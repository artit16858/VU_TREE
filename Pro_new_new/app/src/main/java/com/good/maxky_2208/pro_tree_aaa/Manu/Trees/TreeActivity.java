package com.good.maxky_2208.pro_tree_aaa.Manu.Trees;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.good.maxky_2208.pro_tree_aaa.MainaActivity;
import com.good.maxky_2208.pro_tree_aaa.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TreeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    Spinner az;
    private ArrayAdapter<CharSequence> as;
    RecyclerAdapter_Tree recyclerAdapterTree;
    SearchView searchView;
    List<Trees> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);
        recyclerView = (RecyclerView) findViewById(R.id.rec);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        az = (Spinner) findViewById(R.id.s);
        as = ArrayAdapter.createFromResource(this, R.array.az, android.R.layout.simple_spinner_item);
        as.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        az.setAdapter(as);
        az.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemIdAtPosition(i) == 0) {
                    //list.clear();
                    getTree();
                } else if (adapterView.getItemIdAtPosition(i) == 1) {
                    //list.clear();
                    getTree2();
                }
               // Toast.makeText(getBaseContext(), adapterView.getItemIdAtPosition(i) + "sss", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        searchView = (SearchView) findViewById(R.id.ss);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                recyclerAdapterTree.getFilter().filter(s);
                return true;
            }
        });
     //   getTree();
    }

    private void getTree() {
        final String Host = getResources().getString(R.string.host);
        final String URL = Host + "listtree.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();

                        list = Arrays.asList(gson.fromJson(response, Trees[].class));
                        recyclerAdapterTree = new RecyclerAdapter_Tree(getApplicationContext(),list);


                        recyclerView.setAdapter(recyclerAdapterTree);
                    }









                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



        MySingleton_Tree.getMlsingleton(this).addToRequestQue(stringRequest);
    }
    private void getTree2() {
        final String Host = getResources().getString(R.string.host);
        //  final String urlimg = getResources().getString(R.string.urltreeimg);
        final String URL = Host + "listtree2.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();

                        list = Arrays.asList(gson.fromJson(response, Trees[].class));
                        recyclerAdapterTree = new RecyclerAdapter_Tree(getApplicationContext(),list);


                        recyclerView.setAdapter(recyclerAdapterTree);
                    }









                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



        MySingleton_Tree.getMlsingleton(this).addToRequestQue(stringRequest);
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
                        TreeActivity.super.onBackPressed();
                    }
                }).create().show();
    }

}





