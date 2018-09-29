package com.good.maxky_2208.pro_tree_aaa.Manu.User;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.good.maxky_2208.pro_tree_aaa.Comment;
import com.good.maxky_2208.pro_tree_aaa.Login.Login;
import com.good.maxky_2208.pro_tree_aaa.Manu.New.Game.GameOneActivity;
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

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfileActivity extends AppCompatActivity {

    private Session session;
    public static final String FIEFS = "ex";
    private String user_name;
    private OkHttpClient client = new OkHttpClient();
    private TextView s_username, password, name, score, email, tel,sex, Logout;
    private String e_username, e_password, e_name, e_surname, e_email, e_tel, image_pro;
    private Button Edit,comment;
    private ImageButton imageButton;
    public static final String EXTRA_MESSAGE = "";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        session = new Session(this);

            imageButton = (ImageButton) findViewById(R.id.image_profile);
            Logout = (TextView) findViewById(R.id.im_logout);
            comment = (Button) findViewById(R.id.im_comment);
            Edit = (Button) findViewById(R.id.btn_edit);
            Logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(ProfileActivity.this);
                    builder.setMessage("ต้องการออกจากระบบ");
                    builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            session.setLoggedin(false);
                            session.setLoggedinface(false);
                            finish();
                            LoginManager.getInstance().logOut();
                            startActivity(new Intent(getApplicationContext(), Login.class));
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
            Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    Intent i = new Intent(getApplicationContext(), EditPro.class);
                    startActivity(i);
                }
            });
            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(getApplicationContext(), Comment.class);
                    startActivity(i);
                }
            });


            SharedPreferences message = getSharedPreferences(FIEFS, 0);
            user_name = message.getString("userMessage", "not fond");
            //Toast.makeText(getApplicationContext(),"ssss"+ user_name, Toast.LENGTH_SHORT).show();
            String username = user_name;
            Log.d("sdf", username);


            Showuser_edit(username);



    }


    private void Showuser_edit(String username) {
        final String Host = getResources().getString(R.string.host);
        final String BASE_URL = Host + "showuser.php";
        RequestBody body = new FormEncodingBuilder()
                .add("name", username)
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
                        ProfileActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    JSONObject jObject = new JSONObject(resp);
                                    s_username = (TextView) findViewById(R.id.et_e_user);
                                    password = (TextView) findViewById(R.id.et_e_pass);
                                    name = (TextView) findViewById(R.id.et_e_name);
                                    score = (TextView) findViewById(R.id.et_e_score);
                                    email = (TextView) findViewById(R.id.et_e_email);
                                    tel = (TextView) findViewById(R.id.et_e_tel);
                                    sex = (TextView)findViewById(R.id.et_e_sex);

                                    //img = (ImageView) findViewById(R.id.img03);


                                    s_username.setText(getResources().getString(R.string.usernamer)+" : " + jObject.getString("user_username"));
                                    password.setText(getResources().getString(R.string.passwordr)+" : " + jObject.getString("user_password"));
                                    name.setText(getResources().getString(R.string.name)+" : " + jObject.getString("user_name") + "  " + jObject.getString("dd"));
                                    //surname.setText();
                                    email.setText(getResources().getString(R.string.email)+" : " + jObject.getString("user_email"));
                                    tel.setText(getResources().getString(R.string.tel)+" : " + jObject.getString("user_tel"));
                                    score.setText(getResources().getString(R.string.score)+" : " + jObject.getString("user_score"));
                                    sex.setText(getResources().getString(R.string.sex)+" : "+jObject.getString("sex"));

                                    String image = jObject.getString("name_image");


                                    if (!session.loggedinface()) {
                                        image_pro = Host + "images/" + image;
                                    } else {
                                        image_pro = "https://graph.facebook.com/" + jObject.getString("user_username") + "/picture?type=large";


                                    }
                                    imageButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent i = new Intent(ProfileActivity.this, ShowImage.class);
                                            i.putExtra(EXTRA_MESSAGE, image_pro);
                                            startActivity(i);
                                        }
                                    });
                                    Log.d("imagpro", image_pro);

                                    Picasso.with(getApplicationContext())
                                            .load(image_pro)
                                            .into(imageButton);


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
    public class updaterBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            fillData(); // rebuild my listview with new database data
        }
    }

    private void fillData() {
        Showuser_edit(user_name);
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
                        ProfileActivity.super.onBackPressed();
                    }
                }).create().show();
    }

}
