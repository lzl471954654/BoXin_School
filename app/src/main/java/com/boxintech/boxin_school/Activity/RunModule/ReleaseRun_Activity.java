package com.boxintech.boxin_school.Activity.RunModule;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    final int RELEASE_SUCCESS = 1;
    final int RELEASE_FAILED = 0;

    ProgressDialog progressDialog;

     Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case RELEASE_SUCCESS:
                {
                    progressDialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReleaseRun_Activity.this);
                    builder.setMessage("发布成功！"+(String)msg.obj);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.create().show();
                    break;
                }
                case RELEASE_FAILED:
                {
                    progressDialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReleaseRun_Activity.this);
                    builder.setMessage("确定");
                    builder.setPositiveButton("确定",null);
                    builder.create().show();
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_run);
        ExitSystem.add(this);
        init();
    }

    public void init()
    {
        backbutton = (ImageView)findViewById(R.id.release_run_cancel);
        release = (ImageView)findViewById(R.id.release_run_release);
        title = (EditText) findViewById(R.id.release_data_title);
        run_place = (EditText) findViewById(R.id.release_place);

        backbutton.setOnClickListener(this);
        release.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
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
                progressDialog.setMessage("请稍后正在发布");
                progressDialog.setCancelable(false);
                progressDialog.show();

                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                String now = format.format(date);
                now = "2-"+now;
                FormBody.Builder builder =  new FormBody.Builder();
                builder.add("user_id", AppLogonData.getStudent().getXh())
                        .add("run_place",run_place.getText().toString())
                        .add("date",now)
                        .add("title",title.getText().toString());

                dataRequest.createRoomRequest(builder, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                        Message message = new Message();
                        message.what = RELEASE_FAILED;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String s = response.body().string();
                        Message message = new Message();
                        message.what = RELEASE_SUCCESS;
                        message.obj = s;
                        handler.sendMessage(message);
                    }
                });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
