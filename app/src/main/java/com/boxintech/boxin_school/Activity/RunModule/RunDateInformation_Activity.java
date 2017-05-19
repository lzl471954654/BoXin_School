package com.boxintech.boxin_school.Activity.RunModule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.boxintech.boxin_school.OtherClass.ExitSystem;
import com.boxintech.boxin_school.R;

/**
 * Created by LZL on 2017/4/7.
 */

public class RunDateInformation_Activity extends AppCompatActivity implements View.OnClickListener {
    ImageView back;
    Button date_button;
    Button start_run;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_date_information_layout);
        ExitSystem.add(this);

        init();
    }

    public void init()
    {
        back = (ImageView)findViewById(R.id.run_date_information_layout_back_button);
        date_button = (Button)findViewById(R.id.run_date_information_date_button);
        start_run = (Button)findViewById(R.id.run_date_information_layout_run_button);
        back.setOnClickListener(this);
        date_button.setOnClickListener(this);
        start_run.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            default:break;
            case R.id.run_date_information_layout_back_button:
                finish();
                break;
            case R.id.run_date_information_layout_run_button:

                break;
            case R.id.run_date_information_date_button:

                break;
        }
    }
}
