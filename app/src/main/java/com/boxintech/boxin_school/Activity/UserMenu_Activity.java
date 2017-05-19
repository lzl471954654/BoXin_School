package com.boxintech.boxin_school.Activity;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boxintech.boxin_school.Activity.SettingModule.Setting_Activity;
import com.boxintech.boxin_school.DataClass.AppLogonData;
import com.boxintech.boxin_school.DataClass.Student;
import com.boxintech.boxin_school.OtherClass.ExitSystem;
import com.boxintech.boxin_school.R;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * Created by LZL on 2017/3/25.
 */

public class UserMenu_Activity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout settings;
    RelativeLayout admission_ticket;
    ImageView backbutton;
    ImageView qr_code;
    TextView username;
    TextView xh;
    TextView classes;
    TextView xy;
    TextView school;
    TextView setting_text;
    Student student = AppLogonData.getStudent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExitSystem.add(this);
        init();
    }

    public void init()
    {
        qr_code = (ImageView)findViewById(R.id.my_info_person_qr_code);
        setting_text = (TextView)findViewById(R.id.my_info_setting_layout_text);
        admission_ticket = (RelativeLayout)findViewById(R.id.my_info_admission_ticket_layout);
        settings = (RelativeLayout)findViewById(R.id.my_info_setting_layout);
        backbutton = (ImageView)findViewById(R.id.my_info_title_back_button);
        username = (TextView)findViewById(R.id.my_info_person_name);
        xh = (TextView)findViewById(R.id.my_info_person_id);
        classes = (TextView)findViewById(R.id.my_info_class_text);
        xy = (TextView)findViewById(R.id.my_info_college_text);
        school = (TextView)findViewById(R.id.my_info_school_text);
        backbutton.setOnClickListener(this);
        settings.setOnClickListener(this);
        admission_ticket.setOnClickListener(this);
        qr_code.setOnClickListener(this);
        username.setText(student.getName());
        xh.setText("学号："+student.getXh());
        xy.setText(student.getXy());
        classes.setText(student.getClasses());
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.my_info_setting_layout:
            {
                Intent intent = new Intent(UserMenu_Activity.this,Setting_Activity.class);
                //startActivity(intent);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,setting_text,"setting_title").toBundle());
                break;
            }
            case R.id.my_info_admission_ticket_layout:
            {
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.admission_ticket_layout);
                dialog.setCancelable(true);

                TextView name = (TextView) dialog.findViewById(R.id.admission_layout_name);
                TextView data = (TextView) dialog.findViewById(R.id.admission_layout_data);
                name.setText(AppLogonData.getStudent().getName());
                String data_string = "班级："+AppLogonData.getStudent().getClasses()+"\n学号："+AppLogonData.getStudent().getXh()+"\n学院："+AppLogonData.getStudent().getXy();
                data.setText(data_string);

                dialog.show();
                break;
            }
            case R.id.my_info_title_back_button:
            {
                finish();
                break;
            }
            case R.id.my_info_person_qr_code:
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                break;
        }
    }
}
