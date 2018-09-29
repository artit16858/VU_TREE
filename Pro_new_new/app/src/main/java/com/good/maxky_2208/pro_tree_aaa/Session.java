package com.good.maxky_2208.pro_tree_aaa;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Maxky_2208 on 12/5/2560.
 */

public class Session {
    SharedPreferences prefes;
    SharedPreferences.Editor editor;
    Context ctx;


    public Session(Context ctx) {
        this.ctx = ctx;
        prefes = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = prefes.edit();

    }


    public void setLoggedinface(boolean loggedinfae) {
        editor.putBoolean("loggedInmodeface", loggedinfae);
        editor.commit();
    }

    public boolean loggedinface() {
        return prefes.getBoolean("loggedInmodeface", false);
    }

    public void setLoggedin(boolean loggedin) {
        editor.putBoolean("loggedInmode", loggedin);
        editor.commit();
    }

    public boolean loggedin() {
        return prefes.getBoolean("loggedInmode", false);
    }

    public void setUnuser(boolean Unuser) {
        editor.putBoolean("Unuser", Unuser);
        editor.commit();
    }

    public boolean Unuser() {
        return prefes.getBoolean("Unuser", false);
    }

    public void setscan(boolean scan) {
        editor.putBoolean("scan", scan);
        editor.commit();
    }
    public boolean scan() {
        return prefes.getBoolean("scan", false);
    }

    public void setscanch(boolean scanch) {
        editor.putBoolean("scanch", scanch);
        editor.commit();
    }

    public boolean scanch() {
        return prefes.getBoolean("scanch", false);
    }

    public void setimagech(boolean imagech) {
        editor.putBoolean("imagech", imagech);
        editor.commit();
    }

    public boolean imagech() {
        return prefes.getBoolean("imagech", false);
    }



}
