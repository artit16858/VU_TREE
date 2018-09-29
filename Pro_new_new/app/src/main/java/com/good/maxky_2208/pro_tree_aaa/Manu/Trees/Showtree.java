package com.good.maxky_2208.pro_tree_aaa.Manu.Trees;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.good.maxky_2208.pro_tree_aaa.MainaActivity;
import com.good.maxky_2208.pro_tree_aaa.Manu.Map.Maps;
import com.good.maxky_2208.pro_tree_aaa.Manu.News.NewsActivity;
import com.good.maxky_2208.pro_tree_aaa.Manu.News.ShowNews;
import com.good.maxky_2208.pro_tree_aaa.R;
import com.good.maxky_2208.pro_tree_aaa.Session;
import com.good.maxky_2208.pro_tree_aaa.ShowImage;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Showtree extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "";
    private OkHttpClient client = new OkHttpClient();
    RatingBar rating;
    ImageView tomap, add_image;
    TextView value, btn_vote;
    private Session session;
    public static final String FIEFS = "ex";
    private String userString;
    private ImageView img01, img02, img03;
    private String img1, img2, img3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_showtree);
        session = new Session(this);
        Intent intent = getIntent();
        String message = intent.getStringExtra(Showtree.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);
        final String name = message;
        //TextView vv = (TextView)findViewById(R.id.dd);
        //  vv.setText(name);
        img01 = (ImageView) findViewById(R.id.img01);
        img02 = (ImageView) findViewById(R.id.img02);
        img03 = (ImageView) findViewById(R.id.img03);
        tomap = (ImageView) findViewById(R.id.tomap);
        add_image   = (ImageView) findViewById(R.id.add_image_tree);
        rating = (RatingBar) findViewById(R.id.ratingBar);
        value = (TextView) findViewById(R.id.value);
      /*  im_vote = (ImageView)findViewById(R.id.im_vote);
        im_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // ShowDialog();

            }
        });*/


        SharedPreferences message2 = getSharedPreferences(FIEFS, 0);
        userString = message2.getString("userMessage", "not fond");
        Log.d("name", userString);
        tomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), Maps.class);
                i.putExtra(EXTRA_MESSAGE, name);
                startActivity(i);
            }
        });

        if (!session.Unuser()) {
            checkrating(name, userString);


            rating.setMax(5);
            rating.setNumStars(5);
            rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                                    @Override
                                                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                                                        if (v == 1) {
                                                            value.setText(R.string.Elementary);
                                                        }
                                                        if (v == 2) {
                                                            value.setText(R.string.Mediocre);
                                                        }
                                                        if (v == 3) {
                                                            value.setText(R.string.Satisfactory);
                                                        }
                                                        if (v == 4) {
                                                            value.setText(R.string.Good);
                                                        }
                                                        if (v == 5) {
                                                            value.setText(R.string.Excellent);
                                                        }
                                                    }
                                                }
            );
            btn_vote = (TextView) findViewById(R.id.bt_vote);
            final AlertDialog.Builder adb = new AlertDialog.Builder(this);
            btn_vote.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {


                    if (rating.getRating() < 1) {

                        AlertDialog ad = adb.create();
                        ad.setMessage("Please select rating point 1-5");
                        ad.show();
                    } else {
                        rating.setEnabled(false);
                        Log.d("ssss", rating.toString());
                        final String Host = getResources().getString(R.string.host);
                        final String URL_R = Host + "uprating.php";
                        RequestQueue requestQueue = Volley.newRequestQueue(Showtree.this);
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
                                params.put("tree_name", name);
                                params.put("rating", String.valueOf(rating.getRating()));

                                return params;
                            }
                        };
                        requestQueue.add(request);
                        score_user();
                        //finish();
                        Toast.makeText(Showtree.this, "ยินดีด้วยคุณได้คะแนน เพิ่ม 1 คะแนน", Toast.LENGTH_SHORT).show();
                        if (!session.scan()) {
                            finish();
                        } else {
                            Intent i = new Intent(getApplicationContext(), MainaActivity.class);
                            startActivity(i);
                            session.setscan(false);
                            finish();
                        }
                        //Toast.makeText(getApplicationContext(), "ขอบคุณครับ", Toast.LENGTH_SHORT).show();
                        // Intent i = new Intent(Showtree.this, MainaActivity.class);
                        // startActivity(i);
                    }


                }
            });

        } else {
            final AlertDialog.Builder adb = new AlertDialog.Builder(this);
            rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    AlertDialog ad = adb.create();
                    ad.setMessage("คุณไม่สามารถโหวดได้ กรุณาเข้าสู่ระบบ");
                    ad.show();

                }
            });

        }

        ShowTree(name);
    }

    private void checkrating(String name, String userString) {
        final String Host = getResources().getString(R.string.host);
        final String URL_R = Host + "checkrating.php";
        RequestBody body = new FormEncodingBuilder()
                .add("tree_name", name)
                .add("username", userString)
                .build();
        Request request = new Request.Builder().url(URL_R).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.v("fail", "Registration Error" + e.getMessage());

            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {

                    final String resp = response.body().string();
                    Log.v("chdd", resp);
                    System.out.println(resp);
                    if (response.isSuccessful()) {


                        // Log.d("anw====",jObject.getString("Detail"));
                        Showtree.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jObject = new JSONObject(resp);

                                    if (jObject.getString("StatusID").equals("1")) {
                                        String numrating = jObject.getString("rating");
                                        rating.setRating(Float.parseFloat(numrating));
                                        value.setText("ให้คะแนนวันที่ " + jObject.getString("date"));
                                        rating.setIsIndicator(true);
                                        btn_vote.setVisibility(View.GONE);
                                    } else {


                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });


                    } else {

                    }
                } catch (IOException e) {
                    // Log.e(TAG_REGISTER, "Exception caught: ", e);
                    System.out.println("Exception caught" + e.getMessage());
                }
            }
        });
    }


    private void score_user() {
        final String Host = getResources().getString(R.string.host);
        final String URL_R = Host + "upscore.php";
        RequestQueue requestQueue = Volley.newRequestQueue(Showtree.this);
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


    private void ShowTree(String name) {
        final String Host = getResources().getString(R.string.host);
        final String BASE_URL = Host + "showtree.php";
        RequestBody body = new FormEncodingBuilder()
                .add("name", name)
                .build();
        Request request = new Request.Builder().url(BASE_URL).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.v("fail", "Registration Error" + e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    final String resp = response.body().string();
                    Log.v("ddddddddddddd", resp);
                    System.out.println(resp);
                    if (response.isSuccessful()) {


                        // Log.d("anw====",jObject.getString("Detail"));
                        Showtree.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    final String Host = getResources().getString(R.string.host);
                                    final String urlimg = getResources().getString(R.string.urltreeimg);
                                    Double rating;
                                    JSONObject jObject = new JSONObject(resp);


                                    TextView tv_name2 = (TextView) findViewById(R.id.txt_name);
                                    TextView tv_name_sc = (TextView) findViewById(R.id.txt_name_sc);
                                    TextView tv_name_v = (TextView) findViewById(R.id.txt_name_v);
                                    TextView tv_name_c = (TextView) findViewById(R.id.txt_name_c);
                                    TextView tv_name_l = (TextView) findViewById(R.id.txt_name_l);
                                    TextView tv_place = (TextView) findViewById(R.id.txt_place);
                                    TextView tv_name_o = (TextView) findViewById(R.id.txt_name_o);
                                    TextView tv_feature = (TextView) findViewById(R.id.txt_feature);
                                    TextView tv_flowering = (TextView) findViewById(R.id.txt_flowering);
                                    TextView tv_dis = (TextView) findViewById(R.id.txt_dis);
                                    TextView tv_avail = (TextView) findViewById(R.id.txt_avail);
                                    TextView tv_breed = (TextView) findViewById(R.id.txt_breed);

                                    final String img1 = Host + urlimg + jObject.getString("tree_img");
                                    final String img2 = Host + urlimg + jObject.getString("tree_img2");
                                    final String img3 = Host + urlimg + jObject.getString("tree_img3");
                                    img01.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent i = new Intent(Showtree.this, ShowImage.class);
                                            i.putExtra(EXTRA_MESSAGE, img1);
                                            startActivity(i);
                                        }
                                    });
                                    img02.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent i = new Intent(Showtree.this, ShowImage.class);
                                            i.putExtra(EXTRA_MESSAGE, img2);
                                            startActivity(i);
                                        }
                                    });
                                    img03.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent i = new Intent(Showtree.this, ShowImage.class);
                                            i.putExtra(EXTRA_MESSAGE, img3);
                                            startActivity(i);
                                        }
                                    });
                                    Picasso.with(getApplicationContext())
                                            .load(img1)
                                            .into(img01);
                                    Picasso.with(getApplicationContext())
                                            .load(img2)
                                            .into(img02);
                                    Picasso.with(getApplicationContext())
                                            .load(img3)
                                            .into(img03);
                                    tv_name2.setText(jObject.getString("tree_name"));

                                    tv_name_sc.setText(jObject.getString("tree_name_sc"));
                                    tv_name_v.setText(jObject.getString("tree_name_v"));
                                    tv_name_c.setText(jObject.getString("tree_name_c"));
                                    tv_name_l.setText(jObject.getString("tree_name_l"));
                                    tv_place.setText(jObject.getString("tree_place"));
                                    tv_name_o.setText(jObject.getString("tree_name_o"));
                                    tv_feature.setText(jObject.getString("tree_feature"));
                                    tv_flowering.setText(jObject.getString("tree_flowering"));
                                    tv_dis.setText(jObject.getString("tree_distribution"));
                                    tv_avail.setText(jObject.getString("tree_avail"));
                                    tv_breed.setText(jObject.getString("tree_br"));
                                    rating = jObject.getDouble("tree_rating");

                                    Log.d("ddd", rating.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });


                    } else {

                    }
                } catch (IOException e) {
                    // Log.e(TAG_REGISTER, "Exception caught: ", e);
                    System.out.println("Exception caught" + e.getMessage());
                }
            }
        });

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    public void onBackPressed() {
        if (!session.scan()) {
            finish();
        } else {
            Intent i = new Intent(getApplicationContext(), MainaActivity.class);
            startActivity(i);
            session.setscan(false);
            finish();
        }


// End check-out program
    }

}
