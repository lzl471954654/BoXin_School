package com.boxintech.boxin_school.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.boxintech.boxin_school.OtherClass.ExitSystem;
import com.boxintech.boxin_school.R;

/**
 * Created by LZL on 2017/3/28.
 */

public class FirstPage extends AppCompatActivity {

    Button logon_button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitSystem.init();
        ExitSystem.add(this);
        setContentView(R.layout.fisr_page);
        logon_button = (Button)findViewById(R.id.first_page_logon_button);
        logon_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstPage.this,Logon_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
