package com.boxintech.boxin_school.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boxintech.boxin_school.Adapter.RunDateListAdapter;
import com.boxintech.boxin_school.DataClass.RunData;
import com.boxintech.boxin_school.R;

import java.util.LinkedList;

/**
 * Created by LZL on 2017/4/5.
 */

public class MyDateRunFragment extends Fragment {
    RecyclerView date_list;
    RecyclerView together_list;
    View rootview;
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
        date_list = (RecyclerView)rootview.findViewById(R.id.date_run_my_date_list);
        together_list = (RecyclerView)rootview.findViewById(R.id.date_run_date_together_list);
        RunDateListAdapter dateListAdapter,togetherAdapter;
        dateListAdapter = new RunDateListAdapter(new LinkedList<RunData>(),true,getContext());
        togetherAdapter = new RunDateListAdapter(new LinkedList<RunData>(),false,getContext());
        date_list.setAdapter(dateListAdapter);
        together_list.setAdapter(togetherAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        date_list.setLayoutManager(layoutManager);
        together_list.setLayoutManager(layoutManager1);
    }
}
