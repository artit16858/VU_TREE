package com.good.maxky_2208.pro_tree_aaa.Manu.News;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


/**
 * Created by Maxky_2208 on 27/9/2560.
 */

public class MySingleton_News {
    private static MySingleton_News mlsingleton;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private MySingleton_News(Context context){
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        return requestQueue;
    }


    public static  synchronized MySingleton_News getMlsingleton(Context context){
        if(mlsingleton==null){
            mlsingleton = new MySingleton_News(context);
        }
        return  mlsingleton;
    }
    public <T> void addToRequestQue(Request<T> request){
        getRequestQueue().add(request);
    }
}


