package com.good.maxky_2208.pro_tree_aaa.Manu.User;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.good.maxky_2208.pro_tree_aaa.MainaActivity;
import com.good.maxky_2208.pro_tree_aaa.R;
import com.good.maxky_2208.pro_tree_aaa.Session;
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

public class EditPro extends AppCompatActivity {

    private Session session;
    public static final String FIEFS = "ex";
    private String user_name;
    private OkHttpClient client = new OkHttpClient();
    private EditText s_username, password, conpassword, name, surname, email, tel;
    private String e_username, e_conpassword, e_password, e_name, e_surname, e_email, e_tel, image_pro;
    private Button save_edit, loadimage;
    private ImageButton imageButton;
    private static final String[] CLUBS = {"Cammara", "Gallery",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_edit_pro);
        SharedPreferences message = getSharedPreferences(FIEFS, 0);
        user_name = message.getString("userMessage", "not fond");
        //Toast.makeText(getApplicationContext(),"ssss"+ user_name, Toast.LENGTH_SHORT).show();
        String username = user_name;
        Log.d("sdf", username);

        session = new Session(this);

        imageButton = (ImageButton) findViewById(R.id.image_profile);
        /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.black_e);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
        if (!session.loggedinface()) {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(EditPro.this, CropImage.class);
                    startActivity(i);
                    finish();
                }
            });
        } else {


        }
        /*crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        save_edit = (Button) findViewById(R.id.save_edit);
        save_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(EditPro.this);
                builder.setMessage("ต้องการแก้ไขข้อมูลส่วนตัว");
                builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        pro_send();
                        // uploadImage();
                        finish();
                        Intent i = new Intent(getApplicationContext(), MainaActivity.class);
                        startActivity(i);
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


        Showuser_edit(username);

    }


    private void pro_send() {
        final EditText ed_username = (EditText) findViewById(R.id.et_e_user);
        final EditText ed_conpassword = (EditText) findViewById(R.id.et_e_conpass);
        final EditText ed_password = (EditText) findViewById(R.id.et_e_pass);
        final EditText ed_name = (EditText) findViewById(R.id.et_e_name);
        final EditText ed_surname = (EditText) findViewById(R.id.et_e_surname);
        final EditText ed_email = (EditText) findViewById(R.id.et_e_email);
        final EditText ed_tel = (EditText) findViewById(R.id.et_e_tel);
        final String Host = getResources().getString(R.string.host);
        final String URL = Host + "edit_user.php";
        e_conpassword = ed_conpassword.getText().toString();
        e_username = ed_username.getText().toString();
        e_password = ed_password.getText().toString();
        e_name = ed_name.getText().toString();
        e_email = ed_email.getText().toString();
        e_surname = ed_surname.getText().toString();
        e_tel = ed_tel.getText().toString();


        if (e_username.isEmpty() || e_password.isEmpty() || e_name.isEmpty() || e_email.isEmpty() || e_surname.isEmpty() || e_tel.isEmpty()) {

            Toast.makeText(EditPro.this, "แก้ไขข้อdfdfมูลแล้ว", Toast.LENGTH_SHORT).show();

        } else if (!e_password.equals(e_conpassword)) {

            Toast.makeText(EditPro.this, "แก้ไขข้อมูaaลแล้ว", Toast.LENGTH_SHORT).show();
        } else {
            RequestQueue requestQueue = Volley.newRequestQueue(EditPro.this);
            StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, URL, new com.android.volley.Response.Listener<String>() {


                @Override
                public void onResponse(String response) {

                    Log.d("onResponse", response);

                    Toast.makeText(EditPro.this, "แก้ไขข้อมูลแล้ว", Toast.LENGTH_SHORT).show();
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("onError", error.toString());
                    Toast.makeText(EditPro.this, "เกิดข้อผิดพลาดโปรดลองอีกครั้ง", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", e_username);
                    params.put("password", e_password);
                    params.put("name", e_name);
                    params.put("email", e_email);
                    params.put("tel", e_tel);
                    params.put("surname", e_surname);
                    return params;
                }
            };
            requestQueue.add(request);

        }
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
                        EditPro.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    JSONObject jObject = new JSONObject(resp);
                                    s_username = (EditText) findViewById(R.id.et_e_user);
                                    password = (EditText) findViewById(R.id.et_e_pass);
                                    conpassword = (EditText) findViewById(R.id.et_e_conpass);
                                    name = (EditText) findViewById(R.id.et_e_name);
                                    surname = (EditText) findViewById(R.id.et_e_surname);
                                    email = (EditText) findViewById(R.id.et_e_email);
                                    tel = (EditText) findViewById(R.id.et_e_tel);
                                    //img = (ImageView) findViewById(R.id.img03);


                                    s_username.setText(jObject.getString("user_username"));
                                    password.setText(jObject.getString("user_password"));
                                    conpassword.setText(jObject.getString("user_password"));
                                    name.setText(jObject.getString("user_name"));
                                    surname.setText(jObject.getString("dd"));
                                    email.setText(jObject.getString("user_email"));
                                    tel.setText(jObject.getString("user_tel"));
                                    String image = jObject.getString("name_image");

                                    if (!session.loggedinface()) {
                                        image_pro = Host + "images/" + image;
                                    } else {
                                        image_pro = "https://graph.facebook.com/" + jObject.getString("user_username") + "/picture?type=large";


                                    }
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

    public void onBackPressed() {
        finish();
        startActivity(getIntent());


// End check-out program
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}

