package com.good.maxky_2208.pro_tree_aaa.Manu.Scan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.good.maxky_2208.pro_tree_aaa.MainaActivity;
import com.good.maxky_2208.pro_tree_aaa.Manu.Trees.Showtree;
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
import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DecoderActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback, QRCodeReaderView.OnQRCodeReadListener {
    private static final int MY_PERMISSION_REQUEST_CAMERA = 0;
    private static final String EXTRA_MESSAGE = "";
    String message;
    private ViewGroup mainLayout;
    private Session session;
    public static final String FIEFS = "ex";
    private String userString;
    private TextView resultTextView;
    private QRCodeReaderView qrCodeReaderView;
    private OkHttpClient client = new OkHttpClient();
    //private CheckBox flashlightCheckBox;
    private Switch on_of;
    private PointsOverlayView pointsOverlayView;
    private Button sendto;
    // private PointsOverlayView pointsOverlayView;
    private int l = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoder);
        mainLayout = (ViewGroup) findViewById(R.id.main_layout);
        session = new Session(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            initQRCodeReaderView();
        } else {
            requestCameraPermission();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (qrCodeReaderView != null) {
            qrCodeReaderView.startCamera();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (qrCodeReaderView != null) {
            qrCodeReaderView.stopCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSION_REQUEST_CAMERA) {
            return;
        }

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(mainLayout, "Camera permission was granted.", Snackbar.LENGTH_SHORT).show();
            initQRCodeReaderView();
        } else {
            Snackbar.make(mainLayout, "Camera permission request was denied.", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        resultTextView.setText(text);
        pointsOverlayView.setPoints(points);
        l++;
        // Toast.makeText(getApplicationContext(), "" + text,Toast.LENGTH_SHORT).show();
        // pointsOverlayView.setPoints(points);
       /* Intent intent = new Intent(getApplicationContext(), Showtree.class);
        message = text;
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        session.setscan(true);
        score_user();
        finish();*/
        if (l == 1) {
            session.setscanch(true);
            checktree(text);
            //finish();
        }
    }

    private void checktree(String text) {
        final String Host = getResources().getString(R.string.host);
        final String BASE_URL = Host + "checktreeqr.php";

        if (!session.scanch()) {
            // finish();
        } else {
            final AlertDialog.Builder adb = new AlertDialog.Builder(this);
            RequestBody body = new FormEncodingBuilder()
                    .add("tree_name", text)

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
                            DecoderActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject jObject = new JSONObject(resp);
                                        if (jObject.getString("StatusID").equals("1")) {
                                            message = jObject.getString("tree_ID");
                                            //sendto.setVisibility(View.VISIBLE);
                                            //sendto.setOnClickListener(new View.OnClickListener() {

                                            // @Override
                                            // public void onClick(View v) {
                                            Intent intent = new Intent(getApplicationContext(), Showtree.class);

                                            intent.putExtra(EXTRA_MESSAGE, message);
                                            startActivity(intent);
                                            session.setscan(true);
                                            session.setscanch(false);
                                            score_user();
                                            Toast.makeText(DecoderActivity.this, "ยินดีด้วยคุณได้คะแนน เพิ่ม 1 คะแนน", Toast.LENGTH_SHORT).show();
                                            finish();
                                            // }
                                            // });

                                        } else if (jObject.getString("StatusID").equals("0")) {
                                            AlertDialog ad = adb.create();
                                            ad.setMessage("QR CODE ไม่ถูกต้อง กรุณาสแกนใหม่อีกครั้ง");
                                            ad.show();


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

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Snackbar.make(mainLayout, "Camera access is required to display the camera preview.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(DecoderActivity.this, new String[]{
                            Manifest.permission.CAMERA
                    }, MY_PERMISSION_REQUEST_CAMERA);
                }
            }).show();
        } else {
            Snackbar.make(mainLayout, "Permission is not available. Requesting camera permission.",
                    Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA
            }, MY_PERMISSION_REQUEST_CAMERA);
        }
    }

    private void initQRCodeReaderView() {
        View content = getLayoutInflater().inflate(R.layout.content_decoder, mainLayout, true);

        sendto = (Button) findViewById(R.id.sendto);
        sendto.setVisibility(View.GONE);

        qrCodeReaderView = (QRCodeReaderView) content.findViewById(R.id.qrdecoderview);
        resultTextView = (TextView) content.findViewById(R.id.result_text_view);

        //flashlightCheckBox = (CheckBox) content.findViewById(R.id.flashlight_checkbox);
        on_of = (Switch) content.findViewById(R.id.switch2);
        pointsOverlayView = (PointsOverlayView) content.findViewById(R.id.points_overlay_view);

        qrCodeReaderView.setAutofocusInterval(100L);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setBackCamera();
        on_of.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                qrCodeReaderView.setTorchEnabled(b);
            }
        });
        /*flashlightCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                qrCodeReaderView.setTorchEnabled(isChecked);
            }
        });*/

        qrCodeReaderView.setQRDecodingEnabled(true);

        qrCodeReaderView.startCamera();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    private void score_user() {

        SharedPreferences message = getSharedPreferences(FIEFS, 0);
        userString = message.getString("userMessage", "not fond");
        Log.d("name", userString);

        final String Host = getResources().getString(R.string.host);
        final String URL_R = Host + "upscore.php";
        RequestQueue requestQueue = Volley.newRequestQueue(DecoderActivity.this);
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

    public void onBackPressed() {
        finish();
        Intent i = new Intent(DecoderActivity.this, MainaActivity.class);
        startActivity(i);


// End check-out program
    }
}