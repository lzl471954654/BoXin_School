package com.boxintech.boxin_school.Activity.SettingModule;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.boxintech.boxin_school.DataClass.AppLogonData;
import com.boxintech.boxin_school.InternetRequest.DataRequest;
import com.boxintech.boxin_school.OtherClass.ExitSystem;
import com.boxintech.boxin_school.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by LZL on 2017/3/24.
 */

public class FeedBack_Activity extends AppCompatActivity{
    EditText feedback_data;
    Button send_button;
    ImageView back_button;

    DataRequest dataRequest = new DataRequest();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fedd_back_layout);
        ExitSystem.add(this);
        init();
    }

    public void init()
    {
        bindingView();
        setListener();
    }

    public void bindingView()
    {
        feedback_data = (EditText)findViewById(R.id.feedback_data);
        send_button = (Button)findViewById(R.id.feedback_send_button);
        back_button = (ImageView)findViewById(R.id.feedback_back_button);
    }

    public void setListener()
    {
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        feedback_data.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0)
                    send_button.setBackgroundResource(R.color.exit_button);
                else
                    send_button.setBackgroundResource(R.color.send_button_on_focus);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feedback_data.getText().length()==0)
                {
                    Snackbar.make(send_button,"请输入内容再提交！",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                final ProgressDialog progressDialog = new ProgressDialog(FeedBack_Activity.this);
                progressDialog.setMessage("正在提交，请稍后");
                progressDialog.show();
                dataRequest.sendSuggest(AppLogonData.getStudent().getXh(), feedback_data.getText().toString(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String s = response.body().string();
                        progressDialog.dismiss();
                        try
                        {
                            JSONObject jsonObject = new JSONObject(s);
                            final int result = jsonObject.getInt("res");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(FeedBack_Activity.this);
                                    if(result==1)
                                    {
                                        builder.setMessage("提交成功！");
                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        });
                                        builder.create().show();
                                    }
                                    else
                                    {
                                        builder.setMessage("对不起网路异常，提交失败，请重试。");
                                        builder.setPositiveButton("确定",null);
                                        builder.create().show();
                                    }
                                }
                            });
                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
