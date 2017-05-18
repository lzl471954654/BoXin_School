package com.boxintech.boxin_school.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.boxintech.boxin_school.DataClass.AppLogonData;
import com.boxintech.boxin_school.R;

import java.util.LinkedList;
import java.util.List;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity{

    List<LatLng> latLngList = new LinkedList<>();
    RelativeLayout settings;
    RelativeLayout admission_ticket;
    MapView mapView;
    Polyline polyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout_test);
        AppLogonData.setOkHttpClient(new OkHttpClient());
        Intent intent = new Intent(this,FirstPage.class);
        startActivity(intent);
        finish();


        /*mapView = (MapView)findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        final AMap aMap = mapView.getMap();
        MyLocationStyle myLocationStyle = new MyLocationStyle();//创建定位蓝点样式
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//设置持续定位
        myLocationStyle.interval(1000);//定位间隔 ms
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        AMapLocationClient aMapLocationClient = new AMapLocationClient(getApplicationContext());
        AMapLocationListener locationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if(aMapLocation!=null) {
                    double jingdu = aMapLocation.getLongitude();
                    double weidu = aMapLocation.getLatitude();
                    System.out.println("success 经度：" + jingdu + "\t维度：" + weidu);
                    Toast.makeText(getApplicationContext(), "经度：" + jingdu + "\t维度：" + weidu, Toast.LENGTH_SHORT).show();
                    LatLng latLng = new LatLng(weidu, jingdu);
                    latLngList.add(latLng);
                    polyline = aMap.addPolyline(new PolylineOptions().width(25).color(Color.BLUE).addAll(latLngList));
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"定位失败！",Toast.LENGTH_SHORT).show();
                }
            }
        };
        aMapLocationClient.setLocationListener(locationListener);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocationLatest(true);
        option.setInterval(1000);
        option.setNeedAddress(true);
        aMapLocationClient.setLocationOption(option);
        aMapLocationClient.startLocation();*/
    }


    /*protected void onResume()
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
    }*/
}
