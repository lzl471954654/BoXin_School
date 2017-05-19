package com.boxintech.boxin_school.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.boxintech.boxin_school.DataClass.AppCache;
import com.boxintech.boxin_school.R;

import java.util.List;
import java.util.Map;

/**
 * Created by LZL on 2017/4/24.
 */

public class ChooseLessonListAdapter extends RecyclerView.Adapter<ChooseLessonListAdapter.ChooseLessonViewHolder> {
    List<Map<String,String>> list;

    public static class ChooseLessonViewHolder extends RecyclerView.ViewHolder
    {
        View root;
        TextView lesson_name;
        TextView lesson_teacher;
        TextView lesson_counter;
        TextView lesson_credit;
        ImageView choose_flag_image;

        public ChooseLessonViewHolder(View view)
        {
            super(view);
            root = view;
            lesson_name = (TextView)view.findViewById(R.id.choose_lesson_item_name);
            lesson_teacher = (TextView)view.findViewById(R.id.choose_lesson_item_lesson_teacher);
            lesson_counter = (TextView)view.findViewById(R.id.choose_lesson_item_lesson_counter);
            lesson_credit = (TextView)view.findViewById(R.id.choose_lesson_item_credit);
            choose_flag_image = (ImageView)view.findViewById(R.id.choose_lesson_item_choose_flag_image);
        }
    }

    @Override
    public void onBindViewHolder(ChooseLessonViewHolder holder, int position) {
        final ChooseLessonViewHolder viewHolder = holder;
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppCache.lesson_edit)
                {
                    if(viewHolder.choose_flag_image.getVisibility()==View.INVISIBLE)
                    {
                        viewHolder.choose_flag_image.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        viewHolder.choose_flag_image.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }

    @Override
    public ChooseLessonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_lesson_list_item,parent,false);
        ChooseLessonViewHolder viewHolder = new ChooseLessonViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return 4;
    }

}
