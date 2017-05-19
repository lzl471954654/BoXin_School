package com.boxintech.boxin_school.Activity.RunModule;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.boxintech.boxin_school.DataClass.AppLogonData;
import com.boxintech.boxin_school.InternetRequest.DataRequest;
import com.boxintech.boxin_school.OtherClass.ExitSystem;
import com.boxintech.boxin_school.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

/**
 * Created by 刘智林 on 2017/4/17.
 */

public class ReleaseRun_Activity extends AppCompatActivity implements View.OnClickListener{

    ImageView backbutton;
    ImageView release;
    EditText title;
    EditText run_place;

    DataRequest dataRequest = new DataRequest();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_run);
        ExitSystem.add(this);
        init();
    }

    public void init()
    {
        title = (EditText)findViewById(R.id.release_title);
        run_place = (EditText)findViewById(R.id.release_place);
        backbutton = (ImageView)findViewById(R.id.release_run_cancel);
        release = (ImageView)findViewById(R.id.release_run_release);


        backbutton.setOnClickListener(this);
        release.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            default:break;
            case R.id.release_run_cancel:
                finish();
                break;
            case R.id.release_run_release:
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("请稍后正在发布");
                progressDialog.setCancelable(false);
                progressDialog.show();
                final Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String s = format.format(date);
                final FormBody.Builder builder = new FormBody.Builder();
                builder.add("user_id", AppLogonData.getStudent().getXh())
                        .add("title",title.getText().toString())
                        .add("run_place",run_place.getText().toString())
                        .add("date",s);
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                dataRequest.createRoomRequest(builder, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        builder1.setMessage("对不起同步数据失败，请重新尝试同步。");
                        builder1.setPositiveButton("确定",null);
                        builder1.show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        progressDialog.dismiss();

                    }
                });
                break;
        }
    }
}
