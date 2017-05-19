package com.boxintech.boxin_school.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boxintech.boxin_school.Activity.RunModule.RunDateInformation_Activity;
import com.boxintech.boxin_school.DataClass.RunData;
import com.boxintech.boxin_school.R;

import java.util.List;

/**
 * Created by LZL on 2017/4/5.
 */

        public class RunDateListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
            List<RunData> runDataList;
            Context now_context;
            boolean mydate = false;

            private static class DataViewHolder extends RecyclerView.ViewHolder
            {
                TextView status;
        public DataViewHolder(View view)
        {
            super(view);
            status = (TextView)view.findViewById(R.id.date_run_status);
        }
    }

    public RunDateListAdapter(List<RunData> list,boolean mydate,Context context)
    {
        runDataList = list;
        this.mydate = mydate;
        this.now_context = context;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(!mydate)
        {
            ((DataViewHolder)holder).status.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_run_item_layout,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(now_context, RunDateInformation_Activity.class);
                now_context.startActivity(intent);
            }
        });
        DataViewHolder viewHolder = new DataViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        //return runDataList.size();
        return 2;
    }
}
