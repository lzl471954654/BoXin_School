package com.boxintech.boxin_school.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boxintech.boxin_school.Adapter.RunDateListAdapter;
import com.boxintech.boxin_school.DataClass.AppCache;
import com.boxintech.boxin_school.DataClass.AppLogonData;
import com.boxintech.boxin_school.DataClass.RunHistoryDataItem;
import com.boxintech.boxin_school.DataClass.RunRoomData;
import com.boxintech.boxin_school.InternetRequest.DataRequest;
import com.boxintech.boxin_school.OtherClass.ParseDataFromJSON;
import com.boxintech.boxin_school.R;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by LZL on 2017/4/5.
 */

public class MyDateRunFragment extends Fragment {
    RecyclerView date_list;
    RecyclerView together_list;
    View rootview;
    TextView sum_people;
    TextView sum_hours;
    TextView sum_times;
    TextView sum_km;
    final int REFRESH_INFO = 1;
    final int REFRESH_ROOM = 2;
    List<RunRoomData> runRoomDataList = null;
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case REFRESH_INFO:
                {
                    setData();
                    break;
                }
                case REFRESH_ROOM:
                {
                    refreshAllRoom();
                    break;
                }
            }
        }
    };

    DataRequest dataRequest = new DataRequest();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.date_run_layout,container,false);
        rootview = view;
        init();
        return view;
    }
    public void init()
    {
        sum_people = (TextView)rootview.findViewById(R.id.date_run_sum_people);
        sum_hours = (TextView)rootview.findViewById(R.id.date_run_sum_hours);
        sum_times = (TextView)rootview.findViewById(R.id.date_run_sports_times);
        sum_km = (TextView)rootview.findViewById(R.id.date_run_sum_miles);

        date_list = (RecyclerView)rootview.findViewById(R.id.date_run_my_date_list);
        together_list = (RecyclerView)rootview.findViewById(R.id.date_run_date_together_list);

        //RunDateListAdapter dateListAdapter,togetherAdapter;
        //dateListAdapter = new RunDateListAdapter(new LinkedList<RunData>(),true,getContext());
        //togetherAdapter = new RunDateListAdapter(new LinkedList<RunData>(),false,getContext());
        //date_list.setAdapter(dateListAdapter);
        //together_list.setAdapter(togetherAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        date_list.setLayoutManager(layoutManager);
        together_list.setLayoutManager(layoutManager1);
        refreshData();
    }

    public void refreshData()
    {
        if(AppCache.getPerson_run_data_list()!=null)
            setData();
        else
        {
            dataRequest.getPersonRunInfoList(AppLogonData.getStudent().getXh(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String s = response.body().string();
                    List<RunHistoryDataItem> list = ParseDataFromJSON.getPersonRunDataList(s);
                    if(list!=null&list.size()!=0)
                    {
                        AppCache.setPerson_run_data_list(list);
                        AppCache.setPerson_run_times(list.size());
                    }
                    int hour = 0;
                    int km = 0;
                    int people = 0;
                    for(int i = 0;i<list.size();i++)
                    {
                        hour+=Integer.valueOf(list.get(i).getRun_time());
                        km+=Double.valueOf(list.get(i).getRun_km());
                        people+=Integer.valueOf(list.get(i).getRun_person_num());
                    }
                    AppCache.setPerson_run_all_km(km);
                    AppCache.setPerson_run_all_people_count(people);
                    AppCache.setPerson_run_sum_hours(hour);
                    Message message = new Message();
                    message.what = REFRESH_INFO;
                    handler.sendMessage(message);
                }
            });
        }

        dataRequest.getAllRoom(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runRoomDataList = ParseDataFromJSON.getRoomList(response.body().string());
                if(runRoomDataList!=null)
                {
                    Message message = new Message();
                    message.what = REFRESH_ROOM;
                    handler.sendMessage(message);
                }
            }
        });
    }

    public void refreshAllRoom()
    {
        RunDateListAdapter runDateListAdapter = new RunDateListAdapter(runRoomDataList,false,getContext());
        together_list.setAdapter(runDateListAdapter);
    }

    public void setData()
    {
        String poeple = ""+AppCache.getPerson_run_all_people_count();
        String hours = ""+AppCache.getPerson_run_sum_hours();
        String km = ""+AppCache.getPerson_run_all_km();
        String times = ""+AppCache.getPerson_run_times();
        sum_people.setText(poeple);
        sum_km.setText(km);
        sum_hours.setText(hours);
        sum_times.setText(times);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
