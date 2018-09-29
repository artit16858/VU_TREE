package com.good.maxky_2208.pro_tree_aaa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.good.maxky_2208.pro_tree_aaa.Manu.User.ProfileActivity;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Comment extends AppCompatActivity {
    private String username, comment;
    Button btcomm;
    private Session session;
    public static final String FIEFS = "ex";
    private String userString;
    public static EditText etcomm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);



        etcomm = (EditText) findViewById(R.id.et_comment);
        SharedPreferences message = getSharedPreferences(FIEFS, 0);
        userString = message.getString("userMessage", "not fond");
        Log.d("name",userString);
        btcomm = (Button) findViewById(R.id.bt_comment);
        btcomm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(Comment.this);
                builder.setMessage("ต้องการแสดงความคิดเห็น");
                builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        comment_send();
                    }
                });
                builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dialog.dismiss();
                    }
                });
                builder.create();
                builder.show();



            }
        });

    }

    private void score_user() {

        SharedPreferences message = getSharedPreferences(FIEFS, 0);
        userString = message.getString("userMessage", "not fond");
        Log.d("name",userString);

        final String Host = getResources().getString(R.string.host);
        final String URL_R = Host + "upscore.php";
        RequestQueue requestQueue = Volley.newRequestQueue(Comment.this);
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, URL_R, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", userString);
                return params;
            }
        };
        requestQueue.add(request);



    }




    private void comment_send() {
        final AlertDialog.Builder adb = new AlertDialog.Builder(this);



        comment  = etcomm.getText().toString();
        username = userString;
        if (username.isEmpty() || comment.isEmpty()) {
            etcomm.setText("");
            Toast.makeText(Comment.this, "กรุณาเขียนความคิดเห็น", Toast.LENGTH_SHORT).show();

        } else {
            final String Host = getResources().getString(R.string.host);
            final String URL = Host+"comment.php";

            RequestQueue requestQueue = Volley.newRequestQueue(Comment.this);
            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {

                    Log.d("onResponse", response);
                    etcomm.setText("");


                    Toast.makeText(Comment.this, "บันทึกความคิดเห็นของท่านเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("onError", error.toString());
                    etcomm.setText("");

                    Toast.makeText(Comment.this, "เกิดข้อผิดพลาดโปรดลองอีกครั้ง", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", username);
                    params.put("comment", comment);

                    return params;
                }
            };
            requestQueue.add(request);
            score_user();
            Toast.makeText(Comment.this, "ยินดีด้วยคุณได้คะแนน เพิ่ม 1 คะแนน", Toast.LENGTH_SHORT).show();
            //Intent i = new Intent(Comment.this,ProfileActivity.class);
            //startActivity(i);
            finish();

        }
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

}


