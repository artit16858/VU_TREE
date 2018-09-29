package com.good.maxky_2208.pro_tree_aaa.Manu.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.good.maxky_2208.pro_tree_aaa.Manu.Trees.Showtree;
import com.good.maxky_2208.pro_tree_aaa.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Maps extends FragmentActivity implements OnMapReadyCallback {

    private String nn, dd,n1,d1;
    private GoogleMap mMap;
    private LatLng piont;
    private Marker pt1;
    private LatLngBounds bounds;
    private OkHttpClient client = new OkHttpClient();
    public ArrayList<String> title;
    public ArrayList<String> des;
    public ArrayList<String> name_p;
    public ArrayList<String> date_p;
    public ArrayList<Double> lat;
    public ArrayList<Double> lng;
    Button btn_nor, btn_hey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);

        Intent intent = getIntent();
        String message = intent.getStringExtra(Showtree.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);
        final String name = message;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        title = new ArrayList<>();
        des = new ArrayList<>();
        lat = new ArrayList<>();
        lng = new ArrayList<>();
        date_p = new ArrayList<>();
        name_p = new ArrayList<>();

        final String Host = getResources().getString(R.string.host);
        final String BASE_URL = Host + "latlng.php";
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
                        Maps.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {


                                    JSONObject jObject = new JSONObject(resp);
                                    JSONArray exArray = jObject.getJSONArray("latlng");

                                    for (int i = 0; i < exArray.length(); i++) {
                                        JSONObject jsonObj = exArray.getJSONObject(i);
                                        title.add(jsonObj.getString("title"));
                                        des.add(jsonObj.getString("des"));
                                        lat.add(jsonObj.getDouble("lat"));
                                        lng.add(jsonObj.getDouble("lng"));
                                        n1=(jsonObj.getString("name_p"));
                                        d1=(jsonObj.getString("date_p"));
                                        if(n1.equals("")) {
                                            name_p.add("");
                                        } else {
                                            name_p.add("ผู้ปลูก : "+(jsonObj.getString("name_p")));
                                        }
                                        if(d1.equals("00-00-0000")){
                                            date_p.add("");
                                        }else {

                                            date_p.add("วันที่ปลูก : "+(jsonObj.getString("date_p")));
                                        }
                                        Log.d("JSON 1", title.toString());
                                        Log.d("JSON 2", des.toString());
                                        Log.d("JSON 3", lng.toString());
                                        Log.d("JSON 4", lng.toString());

                                        piont = new LatLng(lat.get(i), lng.get(i));

                                        pt1 = mMap.addMarker(new MarkerOptions()
                                                .position(piont)
                                                .title(title.get(i))
                                                .snippet(name_p.get(i)+ " " + date_p.get(i) + " " + des.get(i))
                                        );

                                        bounds = new LatLngBounds.Builder()
                                                .include(piont)
                                                .build();
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(piont, 16.8f));

                                        mMap.getUiSettings().setZoomControlsEnabled(true);
                                        btn_nor = (Button) findViewById(R.id.btn_nor);
                                        btn_hey = (Button) findViewById(R.id.btn_hey);
                                        btn_nor.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                            }
                                        });
                                        btn_hey.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                            }
                                        });
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
