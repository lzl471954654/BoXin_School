package com.boxintech.boxin_school.Activity.RunModule;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.boxintech.boxin_school.DataClass.AppCache;
import com.boxintech.boxin_school.DataClass.AppLogonData;
import com.boxintech.boxin_school.DataClass.RunRoomData;
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
 * Created by LZL on 2017/4/7.
 */

public class RunDateInformation_Activity extends AppCompatActivity implements View.OnClickListener {
    ImageView back;
    Button date_button;
    Button start_run;
    Bundle bundle;
    RunRoomData roomData;

    TextView room_title;
    TextView room_date;
    TextView room_target;
    TextView room_people;
    TextView room_place;

    boolean isStart = false;
    boolean dateSuccess = false;
    DataRequest dataRequest = new DataRequest();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_date_information_layout);
        ExitSystem.add(this);
        bundle = getIntent().getExtras();
        roomData = (RunRoomData) bundle.get("room_data");
        init();
    }

    public void init()
    {
        back = (ImageView)findViewById(R.id.run_date_information_layout_back_button);
        date_button = (Button)findViewById(R.id.run_date_information_date_button);
        start_run = (Button)findViewById(R.id.run_date_information_layout_run_button);
        room_title = (TextView)findViewById(R.id.run_date_information_include_item).findViewById(R.id.date_run_item_title);
        room_place = (TextView)findViewById(R.id.run_date_information_include_item).findViewById(R.id.date_run_item_place);
        room_date = (TextView)findViewById(R.id.run_date_information_include_item).findViewById(R.id.date_run_item_time);
        room_target = (TextView)findViewById(R.id.run_date_information_include_item).findViewById(R.id.date_run_target);
        room_people = (TextView)findViewById(R.id.run_date_information_include_item).findViewById(R.id.date_run_people_count);
        room_title.setText(roomData.getTitle());
        room_place.setText("地点 "+roomData.getRun_place());
        back.setOnClickListener(this);
        date_button.setOnClickListener(this);
        start_run.setOnClickListener(this);

        if(getIntent().getBooleanExtra("mydate",false))
        {
            date_button.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            default:break;
            case R.id.run_date_information_layout_back_button:
                finish();
                break;
            case R.id.run_date_information_layout_run_button:
                String room_date = roomData.getYp_date();
                break;
            case R.id.run_date_information_date_button:
                if(dateSuccess)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("你已经预约成功，无需重复预约。");
                    builder.setPositiveButton("确定",null);
                    builder.create().show();
                }
                else
                {
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("请稍后正在尝试加入房间. . .");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    dataRequest.joinRoom(AppLogonData.getStudent().getXh(), roomData.getId(), new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    Snackbar.make(back,"对不起网络连接异常",Snackbar.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String s = response.body().string();
                            try
                            {
                                JSONObject jsonObject = new JSONObject(s);
                                final int result = jsonObject.getInt("res");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        if(result==1)
                                        {
                                            dateSuccess=true;
                                            date_button.setText("成功预约");
                                            AlertDialog.Builder builder = new AlertDialog.Builder(RunDateInformation_Activity.this);
                                            builder.setMessage("加入房间成功！"+s);
                                            builder.setPositiveButton("确定",null);
                                            builder.show();
                                        }
                                        else
                                        {
                                            dateSuccess = false;
                                            AlertDialog.Builder builder = new AlertDialog.Builder(RunDateInformation_Activity.this);
                                            builder.setMessage("对不起加入房间失败失败，请重试"+s);
                                            builder.setPositiveButton("确定",null);
                                            builder.show();
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
                break;
        }
    }
}
