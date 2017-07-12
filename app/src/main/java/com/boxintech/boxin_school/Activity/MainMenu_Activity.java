package com.boxintech.boxin_school.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.boxintech.boxin_school.Activity.RunModule.ReleaseRun_Activity;
import com.boxintech.boxin_school.Activity.RunModule.RunHistory_Activity;
import com.boxintech.boxin_school.Activity.RunModule.StartRun_Activity;
import com.boxintech.boxin_school.DataClass.AppCache;
import com.boxintech.boxin_school.DataClass.AppLogonData;
import com.boxintech.boxin_school.DataClass.AppRunData;
import com.boxintech.boxin_school.Fragment.ChooseLessonFragment;
import com.boxintech.boxin_school.Fragment.EmptyFragment;
import com.boxintech.boxin_school.Fragment.LessonTableFragment;
import com.boxintech.boxin_school.Fragment.Me_Fragment;
import com.boxintech.boxin_school.Fragment.MyDateRunFragment;
import com.boxintech.boxin_school.Fragment.newLessonTableFragment;
import com.boxintech.boxin_school.Fragment.newMe_fragment;
import com.boxintech.boxin_school.OtherClass.ExitSystem;
import com.boxintech.boxin_school.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by LZL on 2017/3/25.
 */

public class MainMenu_Activity extends AppCompatActivity implements View.OnClickListener,Toolbar.OnMenuItemClickListener,BottomNavigationBar.OnTabSelectedListener{
    BottomNavigationBar navigationBar;
    TextView title;
    Toolbar toolbar;
    Menu menu;
    ActionMenuView actionMenuView;

    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    FrameLayout frameLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        if(ExitSystem.getActivityList()!=null)
        {
            ExitSystem.add(this);
        }
        else
        {
            ExitSystem.init();
            ExitSystem.add(this);
        }
        init();
        AppCache.setMainContext(MainMenu_Activity.this);
        addXH();
        getPersonRunInfo();
    }

    public void getPersonRunInfo()
    {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder form_builder = new FormBody.Builder();
        form_builder.add("user_id",AppLogonData.getStudent().getXh());
        FormBody formBody = form_builder.build();
        Request.Builder builder = new Request.Builder();
        builder.url(AppRunData.http_user_run)
                .post(formBody);
        Call call = client.newCall(builder.build());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("runInfo!!");
                System.out.println(response.toString()+"\n"+response.body().string());
            }
        });
    }

    public void addXH()
    {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder form_builder = new FormBody.Builder();
        form_builder.add("user_id",AppLogonData.getStudent().getXh())
                .add("do","add");
        FormBody formBody = form_builder.build();
        Request.Builder builder = new Request.Builder();
        builder.url(AppRunData.http_user)
                .post(formBody);
        Call call = client.newCall(builder.build());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.toString()+"\n"+response.body().string());
            }
        });
    }

    public void onClick(View v)
    {
        /*switch (v.getId())
        {
            case R.id.main_menu_run_start_button:
                Intent intent = new Intent(MainMenu_Activity.this, StartRun_Activity.class);
                startActivity(intent);
                break;
        }*/
    }

    public void init()
    {
        replaceFragment(new newLessonTableFragment());
        bindingView();
        setListener();
    }

    public void bindingView()
    {

        navigationBar = (BottomNavigationBar)findViewById(R.id.main_menu_bottom_navigation_bar);
        toolbar = (Toolbar)findViewById(R.id.main_menu_title_bar);
        title = (TextView)findViewById(R.id.main_menu_title_text);
        frameLayout = (FrameLayout)findViewById(R.id.main_menu_content_layout);
        //run_button = (ImageView)findViewById(R.id.main_menu_run_start_button);


        toolbar.inflateMenu(R.menu.main_menu_temp_item);
        toolbar.setOnMenuItemClickListener(this);
        menu  = toolbar.getMenu();
        //getMenuInflater().inflate(R.menu.main_menu_temp_item,actionMenuView.getMenu());

        navigationBar.addItem(new BottomNavigationItem(R.mipmap.lesson_sheet,"课程表"))
                     .addItem(new BottomNavigationItem(R.mipmap.run,"跑步"))
                    .addItem(new BottomNavigationItem(R.mipmap.run_start,"开始跑步"))
                     //.addItem(new BottomNavigationItem(R.mipmap.books,"选课"))
                    .addItem(new BottomNavigationItem(R.mipmap.me,"我"))
                    .setFirstSelectedPosition(0)
                    .setMode(BottomNavigationBar.MODE_FIXED);
        navigationBar.initialise();
    }




    public void setListener()
    {
        navigationBar.setTabSelectedListener(this);
        //run_button.setOnClickListener(this);
    }

    public void replaceFragment(Fragment fragment)
    {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_menu_content_layout,fragment);
        transaction.commit();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent;
        switch (item.getItemId())
        {
            default:break;
            case R.id.main_menu_item_distribution:
                intent = new Intent(this, ReleaseRun_Activity.class);
                startActivity(intent);
                break;
            case R.id.main_menu_item_history:
                intent = new Intent(MainMenu_Activity.this, RunHistory_Activity.class);
                startActivity(intent);
                break;
            case R.id.main_menu_item_refresh_data:
                String add = "http://summercode.cn/bxyp/addruninfo.php";
                Request.Builder builder = new Request.Builder();
                FormBody.Builder formBody = new FormBody.Builder();
                formBody.add("user_id","04153072")
                        .add("run_type","2")
                        .add("run_date","2016-12-21-17-23")
                        .add("run_time","0.5")
                        .add("run_km","1.5")
                        .add("run_place","东区操场")
                        .add("run_person_num","3");
                FormBody body = formBody.build();
                builder.url(add)
                        .post(body);
                Request request = builder.build();
                Call call = AppLogonData.getOkHttpClient().newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.out.println(response.toString());
                        System.out.println(response.body().string());
                    }
                });
                break;
        }
        return true;
    }

    @Override
    public void onTabSelected(int position) {
        switch (position)
        {
            case 0:
                replaceFragment(new newLessonTableFragment());
                title.setText("课程表");
                menu.clear();
                toolbar.inflateMenu(R.menu.main_menu_temp_item);
                break;
            case 1:
                replaceFragment(new MyDateRunFragment());
                title.setText("跑步");
                menu.clear();
                toolbar.inflateMenu(R.menu.main_menu_item);
                break;
            case 2:
                Intent intent = new Intent(MainMenu_Activity.this,StartRun_Activity.class);
                startActivity(intent);
                break;
            /*case 3:
                replaceFragment(new ChooseLessonFragment());
                title.setText("选课");
                menu.clear();
                toolbar.inflateMenu(R.menu.main_menu_temp_item);
                break;*/
            case 3:
                replaceFragment(new newMe_fragment());
                title.setText("我");
                menu.clear();
                toolbar.inflateMenu(R.menu.main_menu_temp_item);
                break;

        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
