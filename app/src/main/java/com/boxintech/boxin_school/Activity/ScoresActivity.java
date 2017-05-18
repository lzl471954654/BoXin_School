package com.boxintech.boxin_school.Activity;

import android.app.ProgressDialog;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.boxintech.boxin_school.Adapter.ScoresListAdapter;
import com.boxintech.boxin_school.DataClass.AppCache;
import com.boxintech.boxin_school.DataClass.AppLogonData;
import com.boxintech.boxin_school.DataClass.Student;
import com.boxintech.boxin_school.OtherClass.ExitSystem;
import com.boxintech.boxin_school.OtherClass.ParseDataFromHtml;
import com.boxintech.boxin_school.OtherClass.SpaceItemDecoration;
import com.boxintech.boxin_school.R;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LZL on 2017/4/27.
 */

public class ScoresActivity extends AppCompatActivity implements View.OnClickListener,Toolbar.OnMenuItemClickListener{
    final String part1 = "http://222.24.62.120/xscjcx.aspx?xh=";
    final String part2 = "&xm=";
    final  String part3 = "&gnmkdm=N121605";

    OkHttpClient okHttpClient;

    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    Toolbar toolbar;
    ImageView backbutton;


    final int GET_SCORES_FROM_HTTP = 10;
    final int REFRESH_LESSON = 11;
    final int REFRESH_XNXQ = 12;
    final int NO_DATA = 13;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case GET_SCORES_FROM_HTTP:
                    getScores(AppCache.getXNLIST().get(1),"1");
                    break;
                case REFRESH_LESSON:
                    recyclerView.setAdapter(new ScoresListAdapter(AppCache.getScores_list()));
                    break;
                case REFRESH_XNXQ:
                    refreshMenu();
                    break;
                case NO_DATA:
                    AlertDialog.Builder builder = new AlertDialog.Builder(ScoresActivity.this);
                    builder.setMessage("本学期没有数据。。");
                    builder.setPositiveButton("确定",null);
                    builder.create();
                    builder.show();
                    break;
                default:break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scores_activity_layout);
        ExitSystem.add(this);
        init();
    }

    public void init()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("请稍后正在加载成绩");
        progressDialog.setCancelable(false);
        progressDialog.create();
        progressDialog.show();



        backbutton = (ImageView)findViewById(R.id.scores_activity_back);
        backbutton.setOnClickListener(this);
        toolbar = (Toolbar)findViewById(R.id.scores_layout_toolbar);
        toolbar.setOnMenuItemClickListener(this);
        if(AppCache.getXNLIST()!=null)
            refreshMenu();


        recyclerView = (RecyclerView)findViewById(R.id.scores_layout_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpaceItemDecoration(30));
        if(AppCache.getScores_list()!=null)
        {
            recyclerView.setAdapter(new ScoresListAdapter(AppCache.getScores_list()));
            progressDialog.dismiss();
        }
        else
            getDataFormHttp();
    }

    public void refreshMenu()
    {
        Menu menu = toolbar.getMenu();
        List<String> list = AppCache.getXNLIST();
        int i = 0;
        for(int j = 0;j<list.size();j++)
        {
            menu.add(Menu.NONE,Menu.FIRST+i,i,list.get(j)+"-1");
            i++;
            menu.add(Menu.NONE,Menu.FIRST+i,i,list.get(j)+"-2");
            i++;
        }
        System.out.println("Refresh menu");
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        progressDialog.show();
        String title = item.getTitle().toString();
        String xq = title.substring(title.length()-1);
        String xn = title.substring(0,title.length()-2);
        getScores(xn,xq);
        return true;
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.scores_activity_back:
                finish();
                break;
        }
    }

    public void getDataFormHttp()
    {
        httpGetViewState();
    }

    public void httpGetViewState()
    {
        if((okHttpClient=AppLogonData.getOkHttpClient())==null)
        {
            okHttpClient = new OkHttpClient();
            AppLogonData.setOkHttpClient(okHttpClient);
        }
        Request.Builder builder = new Request.Builder();
        builder.get()
                .url(part1+AppLogonData.getStudent().getXh()+part2+AppLogonData.getStudent().getName()+part3)
                .addHeader("Accept-Encoding","gzip,deflate")
                .addHeader("Accept-Language","zh-Hans-CN,zh-Hans;q=0.5")
                .addHeader("Accept","text/html, application/xhtml+xml, image/jxr, */*")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .addHeader("Cookie",AppLogonData.getCookies())
                .addHeader("Referer","http://222.24.62.120/")
                .addHeader("Pragma","no-cache");
        Request request = builder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                progressDialog.dismiss();
                Snackbar.make(recyclerView,"对不起加载VIEWSTATE失败，请检查网络连接！",Snackbar.LENGTH_SHORT).show();
                System.out.println(call.request().toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                List<String> XNLIST = ParseDataFromHtml.getXN(s);
                String ViewState = ParseDataFromHtml.getVIEWSTATE(s);
                AppLogonData.set_viewstate(ViewState);
                AppCache.setXNLIST(XNLIST);
                Message message = new Message();
                message.what = GET_SCORES_FROM_HTTP;
                Message message1 = new Message();
                message1.what = REFRESH_XNXQ;
                handler.sendMessage(message);
                handler.sendMessage(message1);
            }
        });
    }

    public void getScores(String XN,String XQ)
    {
        if((okHttpClient=AppLogonData.getOkHttpClient())==null)
        {
            okHttpClient = new OkHttpClient();
            AppLogonData.setOkHttpClient(okHttpClient);
        }
        Student student = AppLogonData.getStudent();
        String query = part1+student.getXh()+part2+student.getName()+part3;
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("__EVENTTARGET","")
                .add("__EVENTARGUMENT","")
                .add("__VIEWSTATE",AppLogonData.get_viewstate())
                .add("hidLanguage","")
                .add("ddlXN",XN)
                .add("ddlXQ",XQ)
                .add("ddl_kcxz","")
                .add("btn_xq","学期成绩");
        FormBody formBody = builder.build();
        Request.Builder request_builder = new Request.Builder();
        request_builder.post(formBody)
                .url(query)
                .addHeader("Accept-Encoding","gzip,deflate")
                .addHeader("Accept-Language","zh-Hans-CN,zh-Hans;q=0.5")
                .addHeader("Accept","text/html, application/xhtml+xml, image/jxr, */*")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .addHeader("Cookie",AppLogonData.getCookies())
                .addHeader("Referer","http://222.24.62.120/")
                .addHeader("Pragma","no-cache");
        Request request = request_builder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                progressDialog.dismiss();
                Snackbar.make(recyclerView,"对不起加载成绩失败，请检查网络连接！",Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                List<Map<String,String>> list = ParseDataFromHtml.getScoreList(s);
                AppCache.setScores_list(list);
                Message message = new Message();
                message.what = REFRESH_LESSON;
                handler.sendMessage(message);
                progressDialog.dismiss();
                if (list.size()==0)
                {
                    Message message1 = new Message();
                    message1.what = NO_DATA;
                    handler.sendMessage(message1);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
