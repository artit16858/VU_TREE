package com.good.maxky_2208.pro_tree_aaa.Manu.Trees;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Maxky_2208 on 26/9/2560.
 */

public class MySingleton_Tree {
    private static MySingleton_Tree mlsingleton;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private MySingleton_Tree(Context context){
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        return requestQueue;
    }


    public static  synchronized MySingleton_Tree getMlsingleton(Context context){
        if(mlsingleton==null){
            mlsingleton = new MySingleton_Tree(context);
        }
        return  mlsingleton;
    }
    public <T> void addToRequestQue(Request<T> request){
        getRequestQueue().add(request);
    }
}


