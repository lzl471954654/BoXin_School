package com.boxintech.boxin_school.InternetRequest;

import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by LZL on 2017/5/15.
 */

public class DataRequest {
    OkHttpClient okHttpClient = new OkHttpClient();
    public static final String person_run_info = "/bxyp/getruninfo.php";
    public static final String url = "http://summercode.cn";
    public static final String addRunRoomUrl = "http://summercode.cn/bxyp/addruninfo.php";
    public static final String createRoomUrl = url+"/bxyp/createroom.php";
    public static final String timeUrl = "http://summercode.cn/bxyp/time.php";
    public static final String allRoomUrl = url+"/bxyp/getallroom.php";

    public void getAllRoom(Callback callback)
    {
        Request.Builder builder = new Request.Builder();
        builder.url(allRoomUrl)
                .get();
        Request request = builder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public void getTime(Callback callback)
    {
        Request.Builder builder = new Request.Builder();
        builder.url(timeUrl)
                .get();
        Request r = builder.build();
        Call call = okHttpClient.newCall(r);
        call.enqueue(callback);
    }

    public void createRoomRequest(FormBody.Builder builder,Callback callback)
    {
        FormBody formBody = builder.build();
        Request.Builder builder1 = new Request.Builder();
        builder1.url(createRoomUrl)
                .post(builder.build());
        Request request = builder1.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public void addRunRoom(FormBody.Builder builder,Callback callback)
    {
        FormBody formBody = builder.build();
        Request.Builder builder1 = new Request.Builder();
        builder1.url(addRunRoomUrl)
                .post(formBody);
        Request request = builder1.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public void getPersonRunInfoList(String user_id,Callback callback)
    {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("user_id",user_id);
        FormBody formBody = builder.build();
        Request.Builder builder1 = new Request.Builder();
        builder1.url(url+person_run_info)
                .post(formBody);
        Request request = builder1.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }
}
