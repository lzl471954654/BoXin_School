package com.boxintech.boxin_school.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boxintech.boxin_school.R;

import java.util.List;
import java.util.Map;

/**
 * Created by LZL on 2017/3/30.
 */

public class LessonListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Map<String,String>> lesson_list;

    public LessonListAdapter(List<Map<String,String>> lesson_list)
    {
        this.lesson_list = lesson_list;
    }

    private class LessonLIstVIewHolder extends RecyclerView.ViewHolder
    {
        TextView lesson_name;
        TextView lesson_techer;
        TextView lesson_place;
        public LessonLIstVIewHolder(View view,boolean haslesson)
        {
            super(view);
            if(haslesson)
            {
                lesson_name = (TextView)view.findViewById(R.id.lesson_name);
                lesson_techer = (TextView)view.findViewById(R.id.lesson_teacher);
                lesson_place = (TextView)view.findViewById(R.id.lesson_place);
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       // System.out.println("onBindViewHolder!");
        LessonLIstVIewHolder vIewHolder = (LessonLIstVIewHolder)holder;
        if(getItemViewType(position)==1)
        {
            vIewHolder.lesson_name.setText(lesson_list.get((position-1)/2).get("lesson_name"));
            vIewHolder.lesson_techer.setText(lesson_list.get((position-1)/2).get("lesson_teacher"));
            String place = lesson_list.get((position-1)/2).get("lesson_place");
            if(place!=null)
                vIewHolder.lesson_place.setText(place);
            else
                vIewHolder.lesson_place.setText("");
        }
       // System.out.println("onBindViewHolder!Finish!");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //System.out.println("onCreateViewHolder!");
        View view;
        LessonLIstVIewHolder vIewHolder;
        if(viewType==1)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_list_item,parent,false);
            vIewHolder = new LessonLIstVIewHolder(view,true);
        }
        else if(viewType==0)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_table_no_item,parent,false);
            vIewHolder = new LessonLIstVIewHolder(view,false);
        }
        else if(viewType==-1)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_list_morning,parent,false);
            vIewHolder = new LessonLIstVIewHolder(view,false);
        }
        else
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_list_afternoon,parent,false);
            vIewHolder = new LessonLIstVIewHolder(view,false);
        }
        return vIewHolder;
    }

    /*@Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_list_item,parent,false);
        return new LessonLIstVIewHolder(view,true);
    }*/

    @Override
    public int getItemCount() {
        return lesson_list.size()*2;

    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return -1;
        else if(position==5)
            return -2;
        else if(lesson_list.get((position-1)/2).get("empty").equals("0"))
            return 1;
        else
            return 0;
    }
}
