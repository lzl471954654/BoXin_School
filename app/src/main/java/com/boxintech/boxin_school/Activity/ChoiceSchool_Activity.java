package com.boxintech.boxin_school.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.boxintech.boxin_school.Adapter.SchoolListAdapter;
import com.boxintech.boxin_school.OtherClass.ExitSystem;
import com.boxintech.boxin_school.OtherClass.SpaceItemDecoration;
import com.boxintech.boxin_school.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by LZL on 2017/3/28.
 */

public class ChoiceSchool_Activity extends AppCompatActivity {
    RecyclerView recyclerView ;
    List<String> school_list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_school_layout);
        ExitSystem.add(this);
        init();
    }

    public void init()
    {
        appendList();
        recyclerView = (RecyclerView)findViewById(R.id.choice_school_list);
        SchoolListAdapter adapter = new SchoolListAdapter(school_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        SpaceItemDecoration itemDecoration = new SpaceItemDecoration(5);
        recyclerView.addItemDecoration(itemDecoration);
    }

    public void appendList()
    {
        school_list = new LinkedList<>();
        for(int i =0;i<15;i++)
            school_list.add("西安邮电大学");
    }
}
