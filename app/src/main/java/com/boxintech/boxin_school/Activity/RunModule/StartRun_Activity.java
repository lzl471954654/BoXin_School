package com.boxintech.boxin_school.Activity.RunModule;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ListMenuItemView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polygon;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.boxintech.boxin_school.Activity.MainMenu_Activity;
import com.boxintech.boxin_school.DataClass.AppLogonData;
import com.boxintech.boxin_school.DataClass.AppRunData;
import com.boxintech.boxin_school.OtherClass.ExitSystem;
import com.boxintech.boxin_school.R;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LZL on 2017/4/8.
 */

public class StartRun_Activity extends AppCompatActivity implements View.OnClickListener,AMapLocationListener,Runnable {
    boolean has_run = false;
    final int REFRESH_TIME_COUNTER = 10;
    final int REFRESH_SECOND = 11;
    int run_type = 1;
    String run_place = "";

    ImageButton start;
    ImageButton pause;
    ImageButton stop;
    Button mode_change_button;
    TextView run_distance;
    TextView run_time_counter;
    TextView run_speed;

    boolean isRun = false;
    boolean start_record = false;
    boolean counter_start = false;

    AMap aMap;
    MapView mapView;
    MyLocationStyle myLocationStyle;
    AMapLocationClient aMapLocationClient;//定位客户端
    AMapLocationClientOption option;
    List<LatLng> latLngList = new LinkedList<>();
    Polyline polyline;
    LatLng last_position, new_position;

    Thread counter_thread;
    int miniute = 0;
    int seconds = 1;
    int mode = 1;
    int run_perons_numbers = 1;
    float distance = 0;//距离

    ReentrantLock counter_lock = new ReentrantLock();

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                default:break;
                case REFRESH_TIME_COUNTER:
                    String s = miniute+"\nmin";
                    run_time_counter.setText(s);
                    break;

            }
        }
    };

    public int getRun_type() {
        return run_type;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_activity_layout);
        if(ExitSystem.getActivityList()!=null)
        {
            ExitSystem.add(this);
        }
        else
        {
            ExitSystem.init();
            ExitSystem.add(this);
        }
        init(savedInstanceState);
    }

    public void init(Bundle savedInstanceState)
    {
        counter_thread = new Thread(this);
        run_speed = (TextView)findViewById(R.id.run_activity_speed);
        start = (ImageButton)findViewById(R.id.run_activity_start_buttono);
        pause = (ImageButton)findViewById(R.id.run_activity_pause_button);
        stop = (ImageButton)findViewById(R.id.run_activity_stop_button);
        run_distance = (TextView)findViewById(R.id.run_activity_km_data);
        run_time_counter = (TextView)findViewById(R.id.run_activity_min);
        mode_change_button = (Button)findViewById(R.id.location_mode_change_button);

        mapView = (MapView)findViewById(R.id.run_activity_layout_map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();

        myLocationStyle = new MyLocationStyle();//创建定位蓝点样式
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//设置持续定位
        myLocationStyle.interval(5000);//定位间隔 ms
        aMap.setMyLocationEnabled(true);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置定位按钮


        aMapLocationClient = new AMapLocationClient(getApplicationContext());
        option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //option.setOnceLocationLatest(true);
        option.setInterval(5000);
        option.setNeedAddress(true);
        aMapLocationClient.setLocationOption(option);
        aMapLocationClient.startLocation();
        AMapLocationListener locationListener = this;
        aMapLocationClient.setLocationListener(locationListener);


        start.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        mode_change_button.setOnClickListener(this);


    }

    @Override
    public void run() {
        int s = 0;
        try
        {
            for (;;)
            {
                Thread.sleep(1000);
                counter_lock.lock();
                s++;
                seconds++;
                if(s==60)
                {
                    s = 0;
                    miniute++;
                    Message message = new Message();
                    message.what = REFRESH_TIME_COUNTER;
                    handler.sendMessage(message);
                }
                counter_lock.unlock();
            }
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public int getRun_perons_numbers() {
        return run_perons_numbers;
    }

    public void setRun_perons_numbers(int run_perons_numbers) {
        this.run_perons_numbers = run_perons_numbers;
    }

    public String getSpeed()
    {
        float speed = (distance/seconds)*3;
        float temp = (new BigDecimal(speed).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue());
        return Float.toString(temp)+"\nkm/h";
    }
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        System.out.println("location!!!");
        if (start_record)
        {
            double jingdu = aMapLocation.getLongitude();
            double weidu = aMapLocation.getLatitude();
            System.out.println("success 经度：" + jingdu + "\t维度：" + weidu);
            Toast.makeText(this, "经度：" + jingdu + "\t维度：" + weidu, Toast.LENGTH_SHORT).show();
            LatLng latLng = new LatLng(weidu, jingdu);
            latLngList.add(latLng);
            polyline = aMap.addPolyline(new PolylineOptions().width(25).color(Color.BLUE).addAll(latLngList));
            if(last_position==null)
                last_position = latLng;
            new_position = latLng;
            distance+=AMapUtils.calculateLineDistance(new_position,last_position);
            last_position = new_position;

            BigDecimal bigDecimal = new BigDecimal(distance);
            distance = bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
            run_distance.setText(Float.toString(distance));

            run_speed.setText(getSpeed());
        }

    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.run_activity_start_buttono:
                start_record = true;
                start.setVisibility(View.INVISIBLE);
                stop.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);
                if(counter_start)
                {
                    counter_lock.unlock();
                }
                else
                {
                    counter_thread.start();
                    counter_start = true;
                }
                break;
            case R.id.run_activity_pause_button:
                start_record = false;
                start.setVisibility(View.VISIBLE);
                stop.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);
                synchronized(counter_thread)
                {
                    counter_lock.lock();
                }
                break;
            case R.id.run_activity_stop_button:
                final AlertDialog.Builder builder = new AlertDialog.Builder(StartRun_Activity.this);
                builder.setMessage("你确定要停止吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = AppLogonData.getStudent().getXh();
                        String run_type = Integer.toString(getRun_type());
                        String run_time = Float.toString(((seconds+miniute*60)/3600));
                        String run_km = Float.toString((new BigDecimal(distance)).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue());
                        String run_place = getRun_place();
                        String run_person_num = Integer.toString(getRun_perons_numbers());
                        Date date = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
                        String run_date = simpleDateFormat.format(date);
                        FormBody.Builder form = new FormBody.Builder();
                        form.add("user_id",id)
                                .add("run_type",run_type)
                                .add("run_date",run_date)
                                .add("run_time",run_time)
                                .add("run_km",run_km)
                                .add("run_place",run_place)
                                .add("run_person_num",run_person_num);
                        FormBody formBody = form.build();
                        Request.Builder request_b = new Request.Builder();
                        request_b.url(AppRunData.http_person_run_info)
                                .post(formBody);
                        final Request request = request_b.build();
                        OkHttpClient client = new OkHttpClient();
                        Call call = client.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                                finish();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String s = response.toString()+"\n"+response.body().string();
                                System.out.println(s);
                                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.create().show();
                break;

            case R.id.location_mode_change_button:
                if(mode==1)
                {
                    mode_change_button.setText("模式二 使用最近最精准定位，定位间隔1s");
                    myLocationStyle.interval(1000);
                    option.setOnceLocationLatest(true);
                    option.setInterval(1000);
                    mode = 2;
                }
                else if(mode==2)
                {
                    mode_change_button.setText("模式一 定位延迟 3s,不使用最近定位");
                    myLocationStyle.interval(3000);
                    option.setOnceLocationLatest(false);
                    option.setInterval(1000);
                    mode=1;
                }
                break;
        }
    }

    public String getRun_place() {
        return run_place;
    }

    public void setRun_place(String run_place) {
        this.run_place = run_place;
    }



    protected void onResume()
    {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
