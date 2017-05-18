package com.boxintech.boxin_school.Adapter;

import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.boxintech.boxin_school.DataClass.RunHistoryDataItem;
import com.boxintech.boxin_school.R;
import java.util.List;

/**
 * Created by LZL on 2017/4/8.
 */

public class RunHistoryListAdapter extends RecyclerView.Adapter<RunHistoryListAdapter.HistoryListViewHodler> {
    List<RunHistoryDataItem> list;

    public RunHistoryListAdapter(List<RunHistoryDataItem> list1)
    {
        list = list1;
    }
    @Override
    public void onBindViewHolder(HistoryListViewHodler holder, int position) {
        holder.km.setText("路程 "+list.get(position).getRun_km());
        holder.people_count.setText("人数 "+list.get(position).getRun_person_num());
        holder.use_min.setText("用时 "+list.get(position).getRun_time()+"min");
        holder.time.setText(list.get(position).getRun_date());
    }
    public static class HistoryListViewHodler extends RecyclerView.ViewHolder
    {
        ImageView head;
        TextView title;
        TextView km;
        TextView use_min;
        TextView time;
        TextView people_count;
        public HistoryListViewHodler(View view)
        {
            super(view);
            head = (ImageView)view.findViewById(R.id.run_history__item_image);
            title = (TextView)view.findViewById(R.id.run_history_item_title);
            km = (TextView)view.findViewById(R.id.run_history_item_km);
            use_min = (TextView)view.findViewById(R.id.run_history_item_use_min);
            time = (TextView)view.findViewById(R.id.run_history_item_time);
            people_count = (TextView)view.findViewById(R.id.run_history_item_people_count);
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public HistoryListViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.run_history_item,parent,false);
        HistoryListViewHodler viewHodler = new HistoryListViewHodler(view);
        return viewHodler;
    }
}
