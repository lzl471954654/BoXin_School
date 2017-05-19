package com.boxintech.boxin_school.Activity.SettingModule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.boxintech.boxin_school.OtherClass.ExitSystem;
import com.boxintech.boxin_school.R;

/**
 * Created by LZL on 2017/3/25.
 */

public class JoinUs_Activity extends AppCompatActivity implements View.OnClickListener {
    ImageView back_button;
    RadioButton production_operation;
    RadioButton background_development;
    RadioButton production_design;
    RadioButton front_design;
    RadioButton view_design;
    RadioGroup radioGroup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_us_layout);
        ExitSystem.add(this);
        init();
    }

    public void init()
    {
        bindingView();
        setListener();
    }
    public void bindingView()
    {
        radioGroup = (RadioGroup)findViewById(R.id.join_us_radio_group);
        radioGroup.clearCheck();
        back_button = (ImageView)findViewById(R.id.join_us_back_button);
        production_design = (RadioButton)findViewById(R.id.join_us_production_design);
        production_operation = (RadioButton)findViewById(R.id.join_us_production_operation);
        front_design = (RadioButton)findViewById(R.id.join_us_frontend_design);
        view_design = (RadioButton)findViewById(R.id.join_us_view_design);
        background_development = (RadioButton)findViewById(R.id.join_us_background_development);
    }
    public void setListener()
    {
        production_design.setOnClickListener(this);
        production_operation.setOnClickListener(this);
        front_design.setOnClickListener(this);
        view_design.setOnClickListener(this);
        background_development.setOnClickListener(this);
        back_button.setOnClickListener(this);
    }
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.join_us_back_button:
            {
                finish();
                break;
            }
            case R.id.join_us_frontend_design:
            {
                if(front_design.isChecked())
                {
                    front_design.setChecked(false);
                }
                else
                    front_design.setChecked(true);
                break;
            }
            case R.id.join_us_view_design:
            {
                if(view_design.isChecked())
                    view_design.setChecked(false);
                else
                    view_design.setChecked(true);
                break;
            }
            case R.id.join_us_background_development:
            {
                if(background_development.isChecked())
                    background_development.setChecked(false);
                else
                    background_development.setChecked(true);
                break;
            }
            case R.id.join_us_production_design:
            {
                if(production_design.isChecked())
                    production_design.setChecked(false);
                else
                    production_design.setChecked(true);
                break;
            }
            case R.id.join_us_production_operation:
            {
                System.out.println("checked!!\t"+production_operation.isChecked());
                if(production_operation.isChecked())
                {
                    //production_operation.setChecked(false);
                    radioGroup.clearCheck();
                    System.out.println("checked false");
                }
                else
                {
                    radioGroup.check(R.id.join_us_production_operation);
                    //production_operation.setChecked(true);
                    System.out.println("checked true");
                }
                break;
            }
        }
    }
}
