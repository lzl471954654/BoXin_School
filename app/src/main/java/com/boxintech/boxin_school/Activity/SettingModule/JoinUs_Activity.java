package com.boxintech.boxin_school.Activity.SettingModule;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.boxintech.boxin_school.DataClass.AppLogonData;
import com.boxintech.boxin_school.InternetRequest.DataRequest;
import com.boxintech.boxin_school.OtherClass.ExitSystem;
import com.boxintech.boxin_school.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.EOFException;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by LZL on 2017/3/25.
 */

public class JoinUs_Activity extends AppCompatActivity implements View.OnClickListener {
    ImageView back_button;
    RadioButton production_operation;
    RadioButton background_development;
    RadioButton production_design;
    RadioButton front_design;
    RadioButton view_design;
    RadioGroup radioGroup;

    EditText joinUsPhone;
    EditText introduceMmyslef;
    Button commitButton;

    final int COMMIT_SUCCESS = 1;
    final int COMMIT_FAILED = 0;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    ProgressDialog progressDialog;
    DataRequest dataRequest = new DataRequest();
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case COMMIT_SUCCESS: {
                    progressDialog.dismiss();
                    builder = new AlertDialog.Builder(JoinUs_Activity.this);
                    builder.setMessage("提交成功！");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.show();
                    break;
                }
                case COMMIT_FAILED: {
                    progressDialog.dismiss();
                    builder = new AlertDialog.Builder(JoinUs_Activity.this);
                    builder.setMessage("提交失败！");
                    builder.setPositiveButton("确定", null);
                    builder.show();
                    break;
                }
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_us_layout);
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
        joinUsPhone = (EditText)findViewById(R.id.join_us_phone);
        introduceMmyslef = (EditText)findViewById(R.id.join_us_introduce_myself);
        commitButton = (Button)findViewById(R.id.join_us_commit_button);

        radioGroup = (RadioGroup)findViewById(R.id.join_us_radio_group);
        radioGroup.clearCheck();
        back_button = (ImageView)findViewById(R.id.join_us_back_button);
        production_design = (RadioButton)findViewById(R.id.join_us_production_design);
        production_operation = (RadioButton)findViewById(R.id.join_us_production_operation);
        front_design = (RadioButton)findViewById(R.id.join_us_frontend_design);
        view_design = (RadioButton)findViewById(R.id.join_us_view_design);
        background_development = (RadioButton)findViewById(R.id.join_us_background_development);
    }
    public void setListener()
    {
        production_design.setOnClickListener(this);
        production_operation.setOnClickListener(this);
        front_design.setOnClickListener(this);
        view_design.setOnClickListener(this);
        background_development.setOnClickListener(this);
        back_button.setOnClickListener(this);
        commitButton.setOnClickListener(this);
    }
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.join_us_commit_button:
            {
                String phone = joinUsPhone.getText().toString();
                String intrduce = introduceMmyslef.getText().toString();
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("请稍后，正在提交");
                progressDialog.setCancelable(false);
                progressDialog.show();

                dataRequest.joinUs(AppLogonData.getStudent().getXh(), "产品运营", phone, intrduce, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        Message message = new Message();
                        message.what = COMMIT_FAILED;
                        mhandler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String s = response.body().string();
                        try
                        {
                            System.out.println(s);
                            JSONObject jsonObject = new JSONObject(s);
                            String result = jsonObject.getString("res");
                            if(result.equals("1"))
                            {
                                Message message = new Message();
                                message.what = COMMIT_SUCCESS;
                                mhandler.sendMessage(message);
                            }
                            else
                            {
                                Message message = new Message();
                                message.what = COMMIT_FAILED;
                                mhandler.sendMessage(message);
                            }
                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            }
            case R.id.join_us_back_button:
            {
                finish();
                break;
            }
            case R.id.join_us_frontend_design:
            {
                if(front_design.isChecked())
                {
                    front_design.setChecked(false);
                }
                else
                    front_design.setChecked(true);
                break;
            }
            case R.id.join_us_view_design:
            {
                if(view_design.isChecked())
                    view_design.setChecked(false);
                else
                    view_design.setChecked(true);
                break;
            }
            case R.id.join_us_background_development:
            {
                if(background_development.isChecked())
                    background_development.setChecked(false);
                else
                    background_development.setChecked(true);
                break;
            }
            case R.id.join_us_production_design:
            {
                if(production_design.isChecked())
                    production_design.setChecked(false);
                else
                    production_design.setChecked(true);
                break;
            }
            case R.id.join_us_production_operation:
            {
                System.out.println("checked!!\t"+production_operation.isChecked());
                if(production_operation.isChecked())
                {
                    //production_operation.setChecked(false);
                    radioGroup.clearCheck();
                    System.out.println("checked false");
                }
                else
                {
                    radioGroup.check(R.id.join_us_production_operation);
                    //production_operation.setChecked(true);
                    System.out.println("checked true");
                }
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mhandler.removeCallbacksAndMessages(null);
    }
}
