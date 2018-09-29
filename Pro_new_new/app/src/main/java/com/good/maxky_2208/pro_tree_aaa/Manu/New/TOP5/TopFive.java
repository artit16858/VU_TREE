package com.good.maxky_2208.pro_tree_aaa.Manu.New.TOP5;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.good.maxky_2208.pro_tree_aaa.Manu.New.NewActivity;
import com.good.maxky_2208.pro_tree_aaa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TopFive extends AppCompatActivity {
    JSONObject jsonObject;
    JSONObject jsonObject2;
    JSONArray jsonArray;
    JSONArray jsonArray2;
    Adapter_user adapter_user;
    Adapter_tree adapter_tree;
    ListView listuser, listtree;
    String name, name2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        adapter_user = new Adapter_user(this, R.layout.grid_item);
        listuser = (ListView) findViewById(R.id.list_user_top);
        listuser.setAdapter(adapter_user);
        adapter_tree = new Adapter_tree(this, R.layout.row);
        listtree = (ListView) findViewById(R.id.list_tree_top);
        listtree.setAdapter(adapter_tree);


        //  final ProgressDialog progressDialog = new ProgressDialog(this);
        // progressDialog.setMessage("Loading data...");
        //progressDialog.show();


        uu();
        kk();
    }

    private void kk() {
        final String Host = getResources().getString(R.string.host);
        final String URL = Host + "showtreetop.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //  progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("tree");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);

                                name = o.getString("tree_name");
                                name2 = o.getString("tree_rating");

                                contacts_tree contacts = new contacts_tree(name, name2);
                                adapter_tree.add(contacts);
                            }


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

    private void uu() {
        //final ProgressDialog progressDialog = new ProgressDialog(this);
        //progressDialog.setMessage("Loading data...");
        //progressDialog.show();
        final String Host = getResources().getString(R.string.host);
        final String URL = Host + "showusertop.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                      //  progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("tree");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);

                                name = o.getString("user_username");
                                name2 = o.getString("user_score");

                                contacts_user contacts = new contacts_user(name, "Score : " + name2);
                                adapter_user.add(contacts);
                            }


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

    /*private void uu() {
        final AsyncTask<Void, Void, String> execute = new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    final String Host = getResources().getString(R.string.host);
                    //final String URL_R = Host + "vu_tree/upscore.php";
                    URL url = new URL(Host + "showusertop.php");
                    // URL url = new  URL("http://thaicfp.com/webservices/json-example.php");

                    URLConnection urlConnection = url.openConnection();

                    HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
                    httpURLConnection.setAllowUserInteraction(false);
                    httpURLConnection.setInstanceFollowRedirects(true);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.connect();

                    InputStream inputStream = null;

                    if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                        inputStream = httpURLConnection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");

                    }
                    inputStream.close();
                    Log.d("JSON Result", stringBuilder.toString());

                    jsonObject = new JSONObject(stringBuilder.toString());
                    jsonArray = jsonObject.getJSONArray("tree");

                    int count = 0;


                    while (count < jsonArray.length()) {
                        JSONObject JO = jsonArray.getJSONObject(count);
                        name = JO.getString("user_username");
                        name2 = JO.getString("user_score");

                        contacts_user contacts = new contacts_user(name, "Score : " + name2);
                        adapter_user.add(contacts);
                        count++;


                    }
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;

            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                // TextView textView = (TextView) findViewById(R.id.txt_ss);
                // textView.setText(result);


            }


        }.execute();


    }

   }*/
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));

    }


}