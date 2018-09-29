package com.good.maxky_2208.pro_tree_aaa.Manu.News;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.good.maxky_2208.pro_tree_aaa.R;
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

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ShowNews extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "";
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);

        Intent intent = getIntent();
        String message = intent.getStringExtra(ShowNews.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);
        final String name = message;
        getNews(name);
    }

    private void getNews(String name) {
        final String Host = getResources().getString(R.string.host);
        final String BASE_URL = Host + "shownews.php";
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
                        ShowNews.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    final String Host = getResources().getString(R.string.host);
                                    final String urlimg = "imagenews/";

                                    JSONObject jObject = new JSONObject(resp);

                                    TextView tv_name = (TextView) findViewById(R.id.tx_name_news);
                                    TextView tv_des = (TextView) findViewById(R.id.tx_des_news);
                                    TextView tv_date = (TextView) findViewById(R.id.tx_date_news);
                                    ImageView img01 = (ImageView) findViewById(R.id.imageNews);
                                    ImageView img02 = (ImageView) findViewById(R.id.imageNews2);
                                    ImageView img03 = (ImageView) findViewById(R.id.imageNews3);
                                    final String img1 =Host + urlimg + jObject.getString("image_news");
                                    final String img2 =Host + urlimg +  jObject.getString("image_news_2");
                                    final   String img3 =Host + urlimg +  jObject.getString("image_news_3");
                                    img01.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent i = new Intent(ShowNews.this, ShowImage.class);
                                            i.putExtra(EXTRA_MESSAGE, img1);
                                            startActivity(i);
                                        }
                                    });
                                    img02.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent i = new Intent(ShowNews.this, ShowImage.class);
                                            i.putExtra(EXTRA_MESSAGE, img2);
                                            startActivity(i);
                                        }
                                    });
                                    img03.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent i = new Intent(ShowNews.this, ShowImage.class);
                                            i.putExtra(EXTRA_MESSAGE, img3);
                                            startActivity(i);
                                        }
                                    });
                                    if (jObject.getString("image_news_2").isEmpty()){
                                        img02.setVisibility(View.GONE);
                                    }
                                    if(jObject.getString("image_news_3").isEmpty()){
                                        img03.setVisibility(View.GONE);
                                    }
                                    Picasso.with(getApplicationContext())
                                            .load(img1)
                                            .into(img01);
                                    Picasso.with(getApplicationContext())
                                            .load(img2)
                                            .into(img02);
                                    Picasso.with(getApplicationContext())
                                            .load(img3)
                                            .into(img03);

                                    tv_name.setText(jObject.getString("name_news"));
                                    tv_des.setText(jObject.getString("des_news"));
                                    tv_date.setText(jObject.getString("date_news"));


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

    public void onBackPressed() {
        finish();
       // Intent i = new Intent(ShowNews.this, MainaActivity.class);
       // startActivity(i);

    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}