package com.good.maxky_2208.pro_tree_aaa.Manu.New.Game;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.good.maxky_2208.pro_tree_aaa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class GameTwoActivity extends AppCompatActivity {
    Button btch1, btch2, btch3, btch4;
    ImageButton BtSpeaker;
    ImageView Img;
    TextView title;
    int questionConut = 15;
    ArrayList<Integer> IDchoice = new ArrayList<Integer>();
    String Answer;
    int score = 0, num = 1;
    //final String Host = " http://192.168.43.205/vu_tree/";

    // final String Host = getResources().getString(R.string.host);

    public String c, c1, c2, c3, c4;
    public static final String FIEFS = "ex";
    private String userString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_two);
        title = (TextView) findViewById(R.id.title);
        btch1 = (Button) findViewById(R.id.Btn1);
        btch2 = (Button) findViewById(R.id.Btn2);
        btch3 = (Button) findViewById(R.id.Btn3);
        btch4 = (Button) findViewById(R.id.Btn4);
        //BtSpeaker=(ImageButton) findViewById(R.id.imageBut);


        for (int i = 1; i <= questionConut; i++)
            IDchoice.add(i);
        Collections.shuffle(IDchoice);
        setQuestion(IDchoice.remove(0));
    }


    private void setQuestion(Integer IDchoice) {
        final String Host = getResources().getString(R.string.host);
        final String URL = Host + "listgame_two.php";

        int n;
        for (n = 0; n < 20; n++) {

            if (IDchoice == n + 1) {
                final int finalN = n;
                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                //  progressDialog.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    JSONArray array = jsonObject.getJSONArray("listgame");

                                    JSONObject o = array.getJSONObject(finalN);

                                    c = o.getString("Answer_t");
                                    c1 = o.getString("Answer_1");
                                    c2 = o.getString("Answer_2");
                                    c3 = o.getString("Answer_3");
                                    c4 = o.getString("Answer_4");
                                    // imag = o.getString("question_image");

                                    title.setText("ข้อที่ " + num + " : " + o.getString("question"));
                                    Answer = c;

                                    //mPlayer=MediaPlayer.create(this,R.raw.buwdang_a);
                                    ArrayList<String> choice = new ArrayList<String>();
                                    choice.add(c1);
                                    choice.add(c2);
                                    choice.add(c3);
                                    choice.add(c4);
                                    Collections.shuffle(choice);
                                    btch1.setText(choice.remove(0));
                                    btch2.setText(choice.remove(0));
                                    btch3.setText(choice.remove(0));
                                    btch4.setText(choice.remove(0));
                                    //  mPlayer.start();
                                    num++;

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


        }
    }


    public void choiceAns(View v) {
        Button b = (Button) v;
        String buttonText = b.getText().toString();
        if (buttonText.equals(Answer)) {
            Toast.makeText(GameTwoActivity.this, "ถูกแล้ว", Toast.LENGTH_SHORT).show();
            score++;
        } else {
            Toast.makeText(GameTwoActivity.this, "ผิด แล้ว", Toast.LENGTH_SHORT).show();
        }
        if (IDchoice.isEmpty()) {
            DialogBox();


        } else {

            setQuestion(IDchoice.remove(0));
        }
    }

    private void DialogBox() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GAME OVER!!");
        builder.setMessage("คุณได้คะแนน " + score + "คะแนน")
                .setCancelable(false)
                .setPositiveButton("ออก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        if(score>=10) {
                            score_user();
                            Toast.makeText(GameTwoActivity.this, "ยินดีด้วยคุณได้คะแนน เพิ่ม 1 คะแนน", Toast.LENGTH_SHORT).show();

                        }
                    }
                })
                .setNegativeButton("เล่นใหม่", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(score>=10){
                            score_user();
                            Toast.makeText(GameTwoActivity.this, "ยินดีด้วยคุณได้คะแนน เพิ่ม 1 คะแนน", Toast.LENGTH_SHORT).show();

                        }
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }


                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void score_user() {

        SharedPreferences message = getSharedPreferences(FIEFS, 0);
        userString = message.getString("userMessage", "not fond");
        Log.d("name", userString);

        final String Host = getResources().getString(R.string.host);
        final String URL_R = Host + "upscore.php";
        RequestQueue requestQueue = Volley.newRequestQueue(GameTwoActivity.this);
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
                        GameTwoActivity.super.onBackPressed();
                    }
                }).create().show();
    }

}