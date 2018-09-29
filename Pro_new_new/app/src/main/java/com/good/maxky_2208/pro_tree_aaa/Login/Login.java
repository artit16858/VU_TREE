package com.good.maxky_2208.pro_tree_aaa.Login;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Login extends AppCompatActivity {
    private Session session;
    //public static String EXTRA_MESSAGE;
    public static final String FIEFS = "ex";
    private OkHttpClient client = new OkHttpClient();
    EditText userNameTextEdit, passwordTextEdit;
    String unshared, msStatus;
    LocalActivityManager mLocalActivityManager;
    private String user_staus;
    //LoginFacebooks
    CheckBox checkpass;
    private Button btnLogin;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private String facebook_id, facebook_name, facebook_email, facebook_surname;
    private static final String EXTRA_MESSAGE = "";
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private Button TH, EN;
    private TextView unuser;

    //    final String Host = getResources().getString(R.string.host);
    //   final String URL = Host + "register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        checkpass = (CheckBox) findViewById(R.id.checkpass);
        checkpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked) {
                    passwordTextEdit.setInputType(129);
                } else {
                    passwordTextEdit.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                }
            }
        });
        sp = getSharedPreferences(FIEFS, 0);
        editor = sp.edit();
        TH = (Button) findViewById(R.id.btn_th);
        EN = (Button) findViewById(R.id.btn_en);
        TH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration config = new Configuration();
                config.locale = new Locale("th");
                getResources().updateConfiguration(config, null);
                finish();
                startActivity(getIntent());
            }
        });
        EN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration config = new Configuration();
                config.locale = Locale.ENGLISH;
                getResources().updateConfiguration(config, null);
                finish();
                startActivity(getIntent());
            }
        });
        session = new Session(this);
        SharedPreferences message = getSharedPreferences(FIEFS, 0);
        user_staus = message.getString("StatusMessage", "not fond");
        if (session.loggedin()) {
            if (user_staus.equals("admin")) {
                finish();
                startActivity(new Intent(Login.this, MainaActivity.class));

            } else if (user_staus.equals("user")) {
                finish();
                startActivity(new Intent(Login.this, MainaActivity.class));

            }
        }
        userNameTextEdit = (EditText) findViewById(R.id.et_user);
        passwordTextEdit = (EditText) findViewById(R.id.et_pass);
        Button login = (Button) findViewById(R.id.btn_login);
        Button register = (Button) findViewById(R.id.btn_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userNameTextEdit.getText().toString();
                String password = passwordTextEdit.getText().toString();

                if (username.isEmpty()) {
                    userNameTextEdit.requestFocus();
                    passwordTextEdit.setText("");

                    Toast.makeText(Login.this, "กรุณากรอก ชื่อผู้ใช้", Toast.LENGTH_SHORT).show();

                } else if (password.isEmpty()) {
                    passwordTextEdit.requestFocus();
                    passwordTextEdit.setText("");

                    Toast.makeText(Login.this, "กรุณากรอก รหัสผ่าน", Toast.LENGTH_SHORT).show();
                } else {
                    ChUser(username, password);
                }
            }
        });
        callbackManager = CallbackManager.Factory.create();
        btnLogin = (Button) findViewById(R.id.btn_loginfb);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("user_photos", "email", "public_profile"));
                processFacebookLogin();
            }
        });
        unuser = (TextView) findViewById(R.id.unuser);
        unuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLoggedin(true);
                session.setUnuser(true);
                Intent i = new Intent(getApplicationContext(), MainaActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void processFacebookLogin() {
        if (accessToken != null) {
            accessToken = com.facebook.AccessToken.getCurrentAccessToken();
            LoginManager.getInstance().logOut();
        }
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //cher ofr Granted permission
                System.out.println("Granted permission" + loginResult.getRecentlyGrantedPermissions());
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        String userDatil = response.getRawResponse();
                        try {
                            JSONObject jsonObject = new JSONObject(userDatil);
                            System.out.println("jsonObject" + jsonObject);
                            facebook_id = jsonObject.getString("id");
                            facebook_name = jsonObject.getString("first_name");
                            facebook_surname = jsonObject.getString("last_name");
                            facebook_email = jsonObject.getString("email");
                            // facebook_iamge = "https://graph.facebook.com/" + facebook_id + "/picture?type=large";
                            //Glide.with(getApplicationContext()).load(facebook_iamge).into(f_image);
                            adduserfacebook();
                            session.setLoggedin(true);
                            session.setLoggedinface(true);
                            session.setUnuser(false);
                            editor = sp.edit();
                            editor.putString("userMessage", facebook_id);
                            //Toast.makeText(getApplicationContext(), facebook_id, Toast.LENGTH_SHORT).show();
                            editor.putString("StatusMessage", "user");
                            editor.commit();
                            finish();
                            Intent intent = new Intent(getApplicationContext(), MainaActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name,email");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();


            }

            @Override
            public void onCancel() {
                System.out.println("LOGIN CANCEL");
            }

            @Override
            public void onError(FacebookException error) {
                System.out.println("LOGIN ERROR");

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.d("face_ddd", facebook_id + "name" + facebook_name + "sur" + facebook_surname + "email" + facebook_email);

    }

    private void adduserfacebook() {


        final String Host = getResources().getString(R.string.host);
        final String URL = Host + "register.php";
        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, URL, new com.android.volley.Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                Log.d("onResponse", response);


                // Toast.makeText(Login.this, "เพิ่มข้อมูลแล้ว", Toast.LENGTH_SHORT).show();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onError", error.toString());
                // Toast.makeText(Login.this, "เกิดข้อผิดพลาดโปรดลองอีกครั้ง", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username_facebook", facebook_id);
                params.put("name_facebook", facebook_name);
                params.put("surname_facebook", facebook_surname);
                params.put("email_facebook", facebook_email);
                params.put("status", "user");
                return params;
            }
        };
        requestQueue.add(request);
    }


    private void ChUser(String username, String password) {
        final String Host = getResources().getString(R.string.host);
        final String BASE_URL = Host + "login.php";
        RequestBody body = new FormEncodingBuilder()
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder().url(BASE_URL).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.v("fail", "Registration Error" + e.getMessage());
                //userNameTextEdit.setText("");
                //userNameTextEdit.setText("");

                //
                // Toast.makeText(Login.this, "ชื่อผู้ใช้หรือรหัสผ่าน ไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {

                    final String resp = response.body().string();
                    Log.v("ddddddddddddd", resp);
                    System.out.println(resp);
                    if (response.isSuccessful()) {


                        // Log.d("anw====",jObject.getString("Detail"));
                        Login.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jObject = new JSONObject(resp);

                                    if (jObject.getString("StatusID").equals("1")) {
                                        session.setLoggedin(true);
                                        session.setUnuser(false);
                                        // Toast.makeText(Loginok.this,"เข้าสู่ระบบสำเร็จ",Toast.LENGTH_SHORT).show();
                                        // EditText editText = (EditText) findViewById(R.id.edit_message);
                                        unshared = jObject.getString("Username");
                                        msStatus = jObject.getString("Status");
                                        String message = unshared;
                                        String message2 = msStatus;
                                        /*Intent intent = new Intent(Login.this, MainaActivity.class);
                                        intent.putExtra(EXTRA_MESSAGE, message);
                                        startActivity(intent);*/
                                        editor = sp.edit();
                                        editor.putString("userMessage", message);
                                        //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                        editor.putString("StatusMessage", message2);
                                        editor.commit();

                                        if (jObject.getString("Status").equals("user")) {
                                            Intent intent = new Intent(getApplicationContext(), MainaActivity.class);
                                            startActivity(intent);
                                        } else if (jObject.getString("Status").equals("admin")) {
                                            Intent intent = new Intent(getApplicationContext(), MainaActivity.class);
                                            startActivity(intent);
                                        }
                                        finish();
                                    } else {
                                        Toast.makeText(Login.this, "ชื่อผู้ใช้ หรือ รหัสผ่าน ไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                                        userNameTextEdit.setText("");
                                        passwordTextEdit.setText("");
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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

}
