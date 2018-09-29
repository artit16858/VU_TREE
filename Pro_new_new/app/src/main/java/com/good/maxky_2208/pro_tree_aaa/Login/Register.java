package com.good.maxky_2208.pro_tree_aaa.Login;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;

import com.android.volley.RequestQueue;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.good.maxky_2208.pro_tree_aaa.Manu.Scan.DecoderActivity;
import com.good.maxky_2208.pro_tree_aaa.Manu.Scan.ScanActivity;
import com.good.maxky_2208.pro_tree_aaa.Manu.Trees.Showtree;
import com.good.maxky_2208.pro_tree_aaa.R;
import com.kosalgeek.android.photoutil.MainActivity;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Register extends AppCompatActivity {
    private String user, username, password, name, surname, email, re_password, tel, sex = "0";

    private OkHttpClient client = new OkHttpClient();

    private RadioButton ramela, rafemela;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register);


        FloatingActionButton back = (FloatingActionButton) findViewById(R.id.black_d);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ramela = (RadioButton) findViewById(R.id.radiomale);
        rafemela = (RadioButton) findViewById(R.id.radiofemale);
        Button bt_r = (Button) findViewById(R.id.btn_r_register);
        bt_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_user();
                // user = etusername.getText().toString();
                //

            }
        });

        ramela.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sex = "Male";
                } else {

                }
            }
        });
        rafemela.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sex = "Female";
                } else {

                }
            }
        });
       

    }


    private void register_user() {
        final String Host = getResources().getString(R.string.host);
        final String URL = Host + "register.php";
        final String BASE_URL = Host + "check_user.php";
        final EditText etusername = (EditText) findViewById(R.id.et_r_user);
        final EditText etpassword = (EditText) findViewById(R.id.et_r_pass);
        final EditText etname = (EditText) findViewById(R.id.et_r_name);
        final EditText etsurname = (EditText) findViewById(R.id.et_r_surname);
        final EditText etemail = (EditText) findViewById(R.id.et_r_email);
        final EditText ettel = (EditText) findViewById(R.id.et_r_tel);
        final EditText etre_password = (EditText) findViewById(R.id.et_r_cpass);

        username = etusername.getText().toString();
        password = etpassword.getText().toString();
        re_password = etre_password.getText().toString();
        name = etname.getText().toString();
        email = etemail.getText().toString();
        surname = etsurname.getText().toString();

        tel = ettel.getText().toString();


        if (username.length() < 6 || username.length() > 10) {
            etusername.setText("");
            etusername.requestFocus();
            Toast.makeText(Register.this, "กรุณากรอกชื้อผู้ใช้ 6-10 ตัวอักษร", Toast.LENGTH_SHORT).show();
        }else if(isValiduser(username)){
            etusername.setText("");
            etusername.requestFocus();
            Toast.makeText(Register.this, "กรุณากรอกชื้อผู้ใช้ เป็นภาษษอังกฤษ", Toast.LENGTH_SHORT).show();
        }
        else if (password.length() < 6 || password.length() > 10) {
            etpassword.setText("");
            etre_password.setText("");
            etpassword.requestFocus();
            Toast.makeText(Register.this, "กรุณากรอกชื้อผู้ใช้ 6-10 ตัวอักษร", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(re_password)) {

            etpassword.setText("");
            etre_password.setText("");
            etpassword.requestFocus();
            Toast.makeText(Register.this, "รหัสผ่านไม่ตรงกัน", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(email)) {
            Toast.makeText(Register.this, "กรุณากรอกอืเมลล์ให้ถูกต้อง", Toast.LENGTH_SHORT).show();
            etemail.setText("");
            etemail.requestFocus();
        } else if (tel.length() < 9 || tel.length() > 10) {
            ettel.setText("");
            ettel.requestFocus();
            Toast.makeText(Register.this, "กรุณากรอกเบอร์โทรให้ถูกต้อง", Toast.LENGTH_SHORT).show();
        } else if (name.isEmpty()) {
            etname.requestFocus();
            Toast.makeText(Register.this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
        } else if (surname.isEmpty()) {
            etsurname.requestFocus();
            Toast.makeText(Register.this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
        }else if (email.isEmpty()) {
            etemail.requestFocus();
            Toast.makeText(Register.this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
      }else if (sex.equals("0")) {

            Toast.makeText(Register.this, "กรุณาระบุเพศ", Toast.LENGTH_SHORT).show();
        }else {
            RequestBody body = new FormEncodingBuilder()
                    .add("username", username)
                    .add("username", username)
                    .add("password", password)
                    .add("name", name)
                    .add("email", email)
                    .add("sex", sex)
                    .add("tel", tel)
                    .add("surname", surname)
                    .add("status", "user")
                    .build();
            Request request = new Request.Builder().url(URL).post(body).build();
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
                            Register.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject jObject = new JSONObject(resp);
                                        if (jObject.getString("StatusID").equals("1")) {
                                            Toast.makeText(Register.this, "เพิ่มข้อมูลแล้ว", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else if (jObject.getString("StatusID").equals("0")) {
                                            Toast.makeText(Register.this, "ชื้อผู้ใช้งานนี้ถูกใช้แล้ว", Toast.LENGTH_SHORT).show();
                                            etusername.setText("");
                                            etpassword.setText("");

                                            etre_password.setText("");

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

    }

    public static boolean isValiduser(String user) {

        String expression = "/[^A-Za-z0-9.#\\-$]/";
        CharSequence inputString = user;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
   
    public static boolean isValidEmail(String email) {
        String expression = "^[\\w\\.]+@([\\w]+\\.)+[A-Z]{2,7}$";
        CharSequence inputString = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

}

