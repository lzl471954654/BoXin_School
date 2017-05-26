package com.boxintech.boxin_school.Activity.RunModule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.boxintech.boxin_school.Adapter.RunHistoryListAdapter;
import com.boxintech.boxin_school.DataClass.AppCache;
import com.boxintech.boxin_school.DataClass.AppLogonData;
import com.boxintech.boxin_school.DataClass.RunHistoryDataItem;
import com.boxintech.boxin_school.InternetRequest.DataRequest;
import com.boxintech.boxin_school.OtherClass.ExitSystem;
import com.boxintech.boxin_school.OtherClass.ParseDataFromJSON;
import com.boxintech.boxin_school.OtherClass.SpaceItemDecoration;
import com.boxintech.boxin_school.R;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by LZL on 2017/4/7.
 */

public class RunHistory_Activity extends AppCompatActivity implements Runnable{
    RecyclerView history_list;
    ImageView backButton;
    DataRequest dataRequestor = new DataRequest();
    List<RunHistoryDataItem> runHistoryDataItemList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_history_layout);
        ExitSystem.add(this);
        init();
    }


    public void init()
    {
        backButton = (ImageView)findViewById(R.id.run_history_layout_back_button);

        history_list = (RecyclerView)findViewById(R.id.run_history_layout_list);
        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(10);
        history_list.addItemDecoration(spaceItemDecoration);


        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RunHistory_Activity.this);
        history_list.setLayoutManager(linearLayoutManager);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if(AppCache.getPerson_run_data_list()!=null)    //检查数据是否存在
        {
            runHistoryDataItemList = AppCache.getPerson_run_data_list();
            refreshData();
        }
        else
        {
            dataRequestor.getPersonRunInfoList(AppLogonData.getStudent().getXh(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String s = response.body().string();
                    List<RunHistoryDataItem> list = ParseDataFromJSON.getPersonRunDataList(s);
                    runHistoryDataItemList = list;
                    if(list!=null&list.size()!=0)
                        AppCache.setPerson_run_data_list(list);
                    runOnUiThread(RunHistory_Activity.this);
                }
            });
        }
    }
    public void refreshData()
    {
        RunHistoryListAdapter adapter = new RunHistoryListAdapter(runHistoryDataItemList);
        history_list.setAdapter(adapter);
    }
    public void run()
    {
        refreshData();
    }
}
