package com.good.maxky_2208.pro_tree_aaa.Manu.New.Game;

import android.content.Context;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import com.good.maxky_2208.pro_tree_aaa.Howtoplay;


import com.good.maxky_2208.pro_tree_aaa.Manu.New.TOP5.Adapter_tree;
import com.good.maxky_2208.pro_tree_aaa.Manu.New.TOP5.Adapter_user;
import com.good.maxky_2208.pro_tree_aaa.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class GameActivity extends AppCompatActivity {
    Button btn_game1, btn_game2, howtoplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        btn_game1 = (Button) findViewById(R.id.btn_game_1);
        btn_game2 = (Button) findViewById(R.id.btn_game_2);
        howtoplay = (Button) findViewById(R.id.howto);
        btn_game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(GameActivity.this, GameOneActivity.class);
                startActivity(i);
            }
        });
        btn_game2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(GameActivity.this, GameTwoActivity.class);
                startActivity(i);
            }
        });
        howtoplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameActivity.this, Howtoplay.class);
                startActivity(i);
            }
        });


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }


}
