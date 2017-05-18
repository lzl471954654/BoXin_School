package com.boxintech.boxin_school.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boxintech.boxin_school.Activity.Logon_Activity;
import com.boxintech.boxin_school.R;

import java.util.List;

/**
 * Created by LZL on 2017/3/28.
 */

public class SchoolListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<String> school_list;

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView school_name;

        public MyViewHolder(View view)
        {
            super(view);
            school_name = (TextView) view.findViewById(R.id.school_list_school_name);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder)holder;
        myViewHolder.school_name.setText(school_list.get(position));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.school_list_item,null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), Logon_Activity.class);
                parent.getContext().startActivity(intent);
                Activity activity = (Activity)parent.getContext();
                activity.finish();
            }
        });
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public int getItemCount() {
        return school_list.size();
    }

    public SchoolListAdapter(List school_list)
    {
        this.school_list = school_list;
    }
}
