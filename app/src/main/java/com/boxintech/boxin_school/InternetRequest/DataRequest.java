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
    private static final String person_run_info = "/bxyp/getruninfo.php";
    private static final String url = "http://summercode.cn";
    private static final String addRunRoomUrl = "http://summercode.cn/bxyp/addruninfo.php";
    private static final String createRoomUrl = url+"/bxyp/createroom.php";
    private static final String timeUrl = "http://summercode.cn/bxyp/time.php";
    private static final String allRoomUrl = url+"/bxyp/getallroom.php";
    private static final String room_info_url = url+"/bxyp/getoneroom.php";
    private static final String join_room_url = url+"/bxyp/join_run.php";
    private static final String get_One_Person_Room_Url = "http://summercode.cn/bxyp/get_per_room.php";
    private static final String suggest_url = "http://summercode.cn/bxyp/sugges.php";
    private static final String join_us_url = "http://summercode.cn/bxyp/join_us.php";

    public void joinUs(String user_id,String job,String phone_num,String introduce,Callback callback)
    {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("user_id",user_id)
                .add("job",job)
                .add("phone_num",phone_num)
                .add("introduce",introduce);
        FormBody formBody = builder.build();
        Request.Builder builder1 = new Request.Builder();
        builder1.url(join_us_url)
                .post(formBody);
        Call call = okHttpClient.newCall(builder1.build());
        call.enqueue(callback);
    }

    public void sendSuggest(String user_id,String suggest,Callback callback)
    {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("user_id",user_id)
                .add("sug",suggest);
        FormBody formBody = builder.build();
        Request.Builder builder1 = new Request.Builder();
        builder1.url(suggest_url)
                .post(formBody);
        Call call = okHttpClient.newCall(builder1.build());
        call.enqueue(callback);
    }

    public void getOnePersonRoom(String user_id,Callback callback)
    {
        Request.Builder builder = new Request.Builder();
        FormBody.Builder builder1 = new FormBody.Builder();
        builder1.add("user_id",user_id);
        FormBody formBody = builder1.build();
        builder.post(formBody)
                .url(get_One_Person_Room_Url);
        Request request = builder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public void joinRoom(String user_id,String room_id,Callback callback)
    {
        Request.Builder builder = new Request.Builder();
        FormBody.Builder builder1 = new FormBody.Builder();
        builder1.add("room_id",room_id)
                .add("user_id",user_id);
        FormBody formBody = builder1.build();
        builder.post(formBody)
                .url(join_room_url);
        Request request = builder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public void getRoomInfo(String room_id,Callback callback)
    {
        Request.Builder builder = new Request.Builder();
        FormBody.Builder builder1 = new FormBody.Builder();
        builder1.add("room_id",room_id);
        FormBody formBody = builder1.build();
        builder.post(formBody)
                .url(room_info_url);
        Request request = builder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

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
