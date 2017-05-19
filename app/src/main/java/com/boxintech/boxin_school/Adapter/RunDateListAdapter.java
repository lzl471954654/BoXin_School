package com.boxintech.boxin_school.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boxintech.boxin_school.Activity.RunModule.RunDateInformation_Activity;
import com.boxintech.boxin_school.DataClass.RunRoomData;
import com.boxintech.boxin_school.R;

import java.util.List;

/**
 * Created by LZL on 2017/4/5.
 */

public class RunDateListAdapter extends RecyclerView.Adapter<RunDateListAdapter.DataViewHolder> {
    List<RunRoomData> runDataList;
    Context now_context;
    boolean mydate = false;

    static class DataViewHolder extends RecyclerView.ViewHolder
    {
        View root;
        TextView status;
        TextView title;
        TextView target;
        TextView place;
        TextView people;
        TextView date;
        public DataViewHolder(View view)
        {
            super(view);
            root = view;
            status = (TextView)view.findViewById(R.id.date_run_status);
            title = (TextView)view.findViewById(R.id.date_run_item_title);
            place = (TextView)view.findViewById(R.id.date_run_item_place);
            target = (TextView)view.findViewById(R.id.date_run_people_count);
            date = (TextView)view.findViewById(R.id.date_run_item_time);
        }
    }

    public RunDateListAdapter(List<RunRoomData> list,boolean mydate,Context context)
    {
        runDataList = list;
        this.mydate = mydate;
        this.now_context = context;
    }
    @Override
    public void onBindViewHolder(RunDateListAdapter.DataViewHolder holder, final int position) {
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(now_context, RunDateInformation_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("room_data",runDataList.get(position));
                intent.putExtras(bundle);
                now_context.startActivity(intent);
            }
        });
        if(!mydate)
        {
            holder.status.setVisibility(View.INVISIBLE);
            holder.title.setText(runDataList.get(position).getTitle());
            holder.place.setText("地点 "+runDataList.get(position).getRun_place());
            holder.date.setText("时间 "+runDataList.get(position).getYp_date());
        }
    }

    @Override
    public RunDateListAdapter.DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_run_item_layout,parent,false);
        DataViewHolder viewHolder = new DataViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        //return runDataList.size();
        return runDataList.size();
    }
}
