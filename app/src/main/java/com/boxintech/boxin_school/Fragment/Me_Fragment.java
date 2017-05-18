package com.boxintech.boxin_school.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boxintech.boxin_school.Activity.ScoresActivity;
import com.boxintech.boxin_school.Activity.UserMenu_Activity;
import com.boxintech.boxin_school.DataClass.AppLogonData;
import com.boxintech.boxin_school.R;

/**
 * Created by LZL on 2017/3/25.
 */

public class Me_Fragment extends Fragment implements View.OnClickListener {
    View root;
    CardView user_layout;
    ImageView qr_code;
    TextView user_name;
    TextView school;
    CardView my_scores;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_info_fragment_layout,container,false);
        root = view;
        bindingView(root);
        setListener();
        return view;
    }

    public void bindingView(View view)
    {
        my_scores = (CardView)view.findViewById(R.id.my_scores_list);
        user_layout = (CardView) view.findViewById(R.id.my_info_fragment_user_layout);
        qr_code = (ImageView)view.findViewById(R.id.my_info_fragment_qr_code);
        user_name = (TextView)view.findViewById(R.id.my_info_fragment_user_name);
        school = (TextView)view.findViewById(R.id.my_info_fragment_user_school);
        user_name.setText(AppLogonData.getStudent().getName());
        String s = "西安邮电大学 "+AppLogonData.getStudent().getXy();
        school.setText(s);
    }

    public void setListener()
    {
        user_layout.setOnClickListener(this);
        qr_code.setOnClickListener(this);
        my_scores.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.my_info_fragment_user_layout:
            {
                Intent intent = new Intent(getContext(), UserMenu_Activity.class);
                startActivity(intent);
                break;
            }
            case R.id.my_scores_list:
                Intent intent = new Intent(getContext(), ScoresActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
