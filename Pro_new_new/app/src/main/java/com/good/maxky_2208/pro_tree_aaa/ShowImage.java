package com.good.maxky_2208.pro_tree_aaa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.good.maxky_2208.pro_tree_aaa.Manu.News.ShowNews;
import com.good.maxky_2208.pro_tree_aaa.Manu.Trees.Showtree;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ShowImage extends AppCompatActivity {
    ImageView image;
    Button done,save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        Intent intent = getIntent();
        String message = intent.getStringExtra(Showtree.EXTRA_MESSAGE);

       // Toast.makeText(ShowImage.this, message, Toast.LENGTH_SHORT).show();
        final String name = message;
        image = (ImageView) findViewById(R.id.image);
        Picasso.with(getApplicationContext())
                .load(name)
                .into(image);
        done = (Button) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
