package com.boxintech.boxin_school.Activity.SettingModule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.boxintech.boxin_school.OtherClass.ExitSystem;
import com.boxintech.boxin_school.R;

/**
 * Created by LZL on 2017/3/25.
 */

public class AboutBoxin_Activity extends AppCompatActivity {

    RelativeLayout join_us;
    ImageView back_button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_boxin_layout);
        ExitSystem.add(this);
        init();
    }

    public void init()
    {
        bidingView();
    }

    public void bidingView()
    {

        back_button = (ImageView)findViewById(R.id.about_boxin_title_back_button);
        join_us = (RelativeLayout)findViewById(R.id.about_boxin_join_us);
        join_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutBoxin_Activity.this,JoinUs_Activity.class);
                startActivity(intent);
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
