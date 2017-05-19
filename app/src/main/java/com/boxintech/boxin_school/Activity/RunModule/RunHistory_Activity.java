package com.boxintech.boxin_school.Activity.RunModule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.boxintech.boxin_school.Adapter.RunHistoryListAdapter;
import com.boxintech.boxin_school.OtherClass.ExitSystem;
import com.boxintech.boxin_school.OtherClass.SpaceItemDecoration;
import com.boxintech.boxin_school.R;

/**
 * Created by LZL on 2017/4/7.
 */

public class RunHistory_Activity extends AppCompatActivity {
    RecyclerView history_list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_history_layout);
        ExitSystem.add(this);
        init();
    }

    public void init()
    {
        history_list = (RecyclerView)findViewById(R.id.run_history_layout_list);
        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(10);
        history_list.addItemDecoration(spaceItemDecoration);
        RunHistoryListAdapter adapter = new RunHistoryListAdapter();
        history_list.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RunHistory_Activity.this);
        history_list.setLayoutManager(linearLayoutManager);
    }
}
