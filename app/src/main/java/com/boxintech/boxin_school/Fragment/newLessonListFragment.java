package com.boxintech.boxin_school.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boxintech.boxin_school.Adapter.LessonListAdapter;
import com.boxintech.boxin_school.DataClass.AppCache;
import com.boxintech.boxin_school.OtherClass.SpaceItemDecoration;
import com.boxintech.boxin_school.R;

import java.util.List;
import java.util.Map;

/**
 * Created by LZL on 2017/4/19.
 */

public class newLessonListFragment extends Fragment {
    RecyclerView recyclerView;
    View root;
    int position;

    public static newLessonListFragment newInstace(int postion)
    {
        newLessonListFragment fragment = new newLessonListFragment();
        fragment.setPosition(postion);
        return fragment;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Fragment on create ! ID:"+position);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.new_lesson_list_layout,container,false);
        System.out.println("fragment createView ! ID:"+position);
        //initView();
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public void initView()
    {
        recyclerView = (RecyclerView)root.findViewById(R.id.lesson_table_list_new);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(35);
        recyclerView.addItemDecoration(spaceItemDecoration);
        if(AppCache.getLesson_collection()!=null)
        {
            refreshLesson(AppCache.getLesson_collection().get(position));
        }
    }

    public void refreshLesson(List<Map<String,String>> list)
    {
        LessonListAdapter adapter = new LessonListAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
