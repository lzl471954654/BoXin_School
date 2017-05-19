package com.boxintech.boxin_school.Fragment;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.boxintech.boxin_school.Activity.ScoresActivity;
import com.boxintech.boxin_school.Activity.SettingModule.Setting_Activity;
import com.boxintech.boxin_school.Activity.UserMenu_Activity;
import com.boxintech.boxin_school.DataClass.AppLogonData;
import com.boxintech.boxin_school.DataClass.Student;
import com.boxintech.boxin_school.OtherClass.QrCoder;
import com.boxintech.boxin_school.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import static android.app.Activity.RESULT_OK;

/**
 * Created by LZL on 2017/5/8.
 */

public class newMe_fragment extends Fragment implements View.OnClickListener{
    View root;

    RelativeLayout settings;
    RelativeLayout admission_ticket;
    RelativeLayout my_scores_layout;
    ImageView qr_code;
    TextView username;
    TextView xh;
    TextView classes;
    TextView xy;
    TextView school;
    TextView setting_text;
    ImageView photo = null;
    Student student;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.new_person_page,container,false);
        initView();
        return root;
    }

    public void initView()
    {
        student = AppLogonData.getStudent();
        qr_code = (ImageView)root.findViewById(R.id.my_info_person_qr_code);
        setting_text = (TextView)root.findViewById(R.id.my_info_setting_layout_text);
        admission_ticket = (RelativeLayout)root.findViewById(R.id.my_info_admission_ticket_layout);
        my_scores_layout = (RelativeLayout)root.findViewById(R.id.my_info_scores_layout);
        settings = (RelativeLayout)root.findViewById(R.id.my_info_setting_layout);
        username = (TextView)root.findViewById(R.id.my_info_person_name);
        xh = (TextView)root.findViewById(R.id.my_info_person_id);
        classes = (TextView)root.findViewById(R.id.my_info_class_text);
        xy = (TextView)root.findViewById(R.id.my_info_college_text);
        school = (TextView)root.findViewById(R.id.my_info_school_text);

        settings.setOnClickListener(this);
        admission_ticket.setOnClickListener(this);
        my_scores_layout.setOnClickListener(this);
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
            case R.id.my_info_scores_layout:
            {
                Intent intent = new Intent(getContext(), ScoresActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.my_info_setting_layout:
            {
                Intent intent = new Intent(getContext(),Setting_Activity.class);
                //startActivity(intent);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(),setting_text,"setting_title").toBundle());
                break;
            }
            case R.id.my_info_admission_ticket_layout:
            {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.admission_ticket_layout);
                dialog.setCancelable(true);

                TextView name = (TextView) dialog.findViewById(R.id.admission_layout_name);
                TextView data = (TextView) dialog.findViewById(R.id.admission_layout_data);
                ImageView take_photo = (ImageView)dialog.findViewById(R.id.admission_take_photo);
                photo = (ImageView)dialog.findViewById(R.id.admission_layout_photo_images);
                name.setText(AppLogonData.getStudent().getName());
                String data_string = "班级："+AppLogonData.getStudent().getClasses()+"\n学号："+AppLogonData.getStudent().getXh()+"\n学院："+AppLogonData.getStudent().getXy();
                data.setText(data_string);

                take_photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,1);  //1代表 缩略图 返回 压缩过后的图片
                    }

                });
                dialog.show();
                break;
            }
            case R.id.my_info_person_qr_code:
                Bitmap bitmap = QrCoder.generateBitmap(student.toString(),400,400);
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.qr_code_layout);
                TextView title =(TextView) dialog.findViewById(R.id.qr_code_layout_title);
                ImageView qrcode = (ImageView)dialog.findViewById(R.id.qr_code_layout_image);
                if(bitmap!=null)
                {
                    qrcode.setImageBitmap(bitmap);
                }
                else
                {
                    title.setText("对不起二维码生成失败！");
                }
                dialog.setCancelable(true);
                dialog.create();
                dialog.show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            if(requestCode==1)
            {
                Bundle bundle  = data.getExtras();
                Bitmap pic = (Bitmap)bundle.get("data");
                photo.setImageBitmap(pic);
                photo.setVisibility(View.VISIBLE);
            }
        }
    }
}
