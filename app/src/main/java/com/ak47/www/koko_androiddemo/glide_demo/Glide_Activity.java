package com.ak47.www.koko_androiddemo.glide_demo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ak47.www.koko_androiddemo.R;


/**
 * 关于glide的demo
 */
public class Glide_Activity extends AppCompatActivity {
    public Context context;
    public ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_layout);
        context = this;
        imageView = (ImageView) findViewById(R.id.my_image_view);
        Button button = (Button) findViewById(R.id.button_glide);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GlideApp.with(context)
                        .load("https://vignette.wikia.nocookie.net/fategrandorder/images/4/43/Artoria1.png/revision/latest?cb=20170206150711")
                        .into(imageView);
            }
        });


    }


}
