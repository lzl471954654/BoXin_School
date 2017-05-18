package com.boxintech.boxin_school.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.boxintech.boxin_school.Adapter.ChooseLessonListAdapter;
import com.boxintech.boxin_school.DataClass.AppCache;
import com.boxintech.boxin_school.OtherClass.SpaceItemDecoration;
import com.boxintech.boxin_school.R;

/**
 * Created by LZL on 2017/4/25.
 */

public class ChooseLessonFragment extends Fragment implements View.OnClickListener,TabLayout.OnTabSelectedListener {
    TextView edit_button;
    Button delete;
    RecyclerView pe_list;
    RecyclerView credit_list;
    TabLayout tabLayout;
    View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.choose_lesson_fragment,container,false);
        initView();
        return root;
    }

    public void initView()
    {
        tabLayout = (TabLayout)root.findViewById(R.id.choose_lesson_tab);
        delete = (Button)root.findViewById(R.id.lesson_delete_button);
        pe_list = (RecyclerView)root.findViewById(R.id.pe_lesson_list);
        credit_list = (RecyclerView)root.findViewById(R.id.literacy_lesson_list);

        ChooseLessonListAdapter adapter = new ChooseLessonListAdapter();
        ChooseLessonListAdapter adapter1 = new ChooseLessonListAdapter();
        pe_list.setAdapter(adapter);
        pe_list.setLayoutManager(new LinearLayoutManager(getContext()));
        pe_list.addItemDecoration(new SpaceItemDecoration(5));
        credit_list.setAdapter(adapter1);
        credit_list.setLayoutManager(new LinearLayoutManager(getContext()));
        credit_list.addItemDecoration(new SpaceItemDecoration(5));

        tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition())
        {
            case 0:
                AppCache.lesson_edit = false;
                delete.setVisibility(View.INVISIBLE);
                break;

            case 1:
                AppCache.lesson_edit  = true;
                delete.setText("提交");
                delete.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            default:break;
        }
    }
}
