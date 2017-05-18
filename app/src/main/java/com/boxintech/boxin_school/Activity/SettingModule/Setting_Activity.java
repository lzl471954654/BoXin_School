package com.boxintech.boxin_school.Activity.SettingModule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boxintech.boxin_school.OtherClass.ExitSystem;
import com.boxintech.boxin_school.R;

/**
 * Created by LZL on 2017/3/23.
 */

public class Setting_Activity extends AppCompatActivity implements View.OnClickListener {

    ImageView back_button;
    RelativeLayout clear_data_button;
    RelativeLayout feedback_button;
    RelativeLayout about_us;
    Button exit_logon;
    TextView clear_data_size;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        ExitSystem.add(this);
        init();
    }

    public void init()
    {
        bindingView();
        setListener();
    }

    public void setListener()
    {
        back_button.setOnClickListener(this);
        clear_data_button.setOnClickListener(this);
        feedback_button.setOnClickListener(this);
        about_us.setOnClickListener(this);
        exit_logon.setOnClickListener(this);
        clear_data_size = (TextView)findViewById(R.id.setting_clear_data_size);
    }

    public void bindingView()
    {
        back_button = (ImageView)findViewById(R.id.setting_title_back_button);
        clear_data_button = (RelativeLayout)findViewById(R.id.setting_clear_data_layout);
        feedback_button = (RelativeLayout)findViewById(R.id.setting_feedback_layout);
        about_us = (RelativeLayout)findViewById(R.id.setting_about_us_layout);
        exit_logon = (Button)findViewById(R.id.setting_exit_logon);
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.setting_title_back_button:
            {
                finish();
                break;
            }
            case R.id.setting_clear_data_layout:
            {
                final ProgressDialog progressDialog = new ProgressDialog(Setting_Activity.this);
                progressDialog.setMessage("正在清除缓存，请稍后");
                progressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            Thread.sleep(1000);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                Snackbar.make(clear_data_button,"缓存已清除！",Snackbar.LENGTH_SHORT).show();
                                clear_data_size.setText("0M");
                            }
                        });
                    }
                }).start();
                break;
            }
            case R.id.setting_exit_logon:
            {
                exit_logon.setVisibility(View.INVISIBLE);
                Snackbar.make(exit_logon,"您已成功退出登录！",Snackbar.LENGTH_SHORT).show();
                ExitSystem.exit();
                break;
            }
            case R.id.setting_feedback_layout:
            {
                Intent intent = new Intent(this,FeedBack_Activity.class);
                startActivity(intent);
                break;
            }
            case R.id.setting_about_us_layout:
            {
                Intent intent = new Intent(this,AboutBoxin_Activity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
