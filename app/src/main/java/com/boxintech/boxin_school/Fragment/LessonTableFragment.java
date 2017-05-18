package com.boxintech.boxin_school.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boxintech.boxin_school.Adapter.LessonListAdapter;
import com.boxintech.boxin_school.DataClass.AppCache;
import com.boxintech.boxin_school.DataClass.AppLogonData;
import com.boxintech.boxin_school.OtherClass.ParseDataFromHtml;
import com.boxintech.boxin_school.OtherClass.SpaceItemDecoration;
import com.boxintech.boxin_school.R;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LZL on 2017/3/30.
 */

public class LessonTableFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    TabLayout tabLayout;
    TextView content;
    RecyclerView recyclerView;
    List<List<Map<String,String>>> lessoncollection;

    final int REFRESH_LESSON = 10;
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case REFRESH_LESSON:
                {
                    onTabSelected(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()));
                    break;
                }
                default:break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lesson_table,container,false);
        tabLayout = (TabLayout)view.findViewById(R.id.lesson_table_tab);
        //content = (TextView)view.findViewById(R.id.lesson_table_data_text);
        recyclerView = (RecyclerView)view.findViewById(R.id.lesson_table_list);
        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(35);
        recyclerView.addItemDecoration(spaceItemDecoration);
        init();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void getLessonTable()
    {
        String url = getRequestUrl(AppLogonData.getStudent().getXh(),AppLogonData.getStudent().getName());
        OkHttpClient okHttpClient = AppLogonData.getOkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url(url)
                .get()
                .header("Accept-Encoding", "gzip,deflate")
                .header("Accept-Language","zh-Hans-CN,zh-Hans;q=0.5")
                .header("Accept","text/html, application/xhtml+xml, image/jxr, */*")
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .header("Pragma","no-cache")
                .header("Referer","http://222.24.62.120/")
                .header("Cookie",AppLogonData.getCookies());
        final Request request = builder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Snackbar.make(tabLayout,"对不起网络连接出错",Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                List<List<Map<String,String>>> Lesson_collection = ParseDataFromHtml.getLessonsTable(s);
                AppCache.setLesson_collection(Lesson_collection);
                lessoncollection = Lesson_collection;
                Message message = new Message();
                message.what = REFRESH_LESSON;
                handler.sendMessage(message);
            }
        });
    }

    public String getRequestUrl(String xh,String student_name)
    {
        StringBuilder builder = new StringBuilder("http://222.24.62.120/xskbcx.aspx?xh=");
        builder.append(xh)
                .append("&xm=")
                .append(student_name)
                .append("&gnmkdm=N121603");
        return builder.toString();
    }


    public void init()
    {
        tabLayout.addOnTabSelectedListener(this);
        System.out.println("Fragment init finish!");
        if((lessoncollection=AppCache.getLesson_collection())==null)
        {
            getLessonTable();
        }
        else
            onTabSelected(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()));
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        //String s = Integer.toString(position);
        //content.setText(s);
        //if(lessoncollection!=null)
        //{
            setAdapterForRecyclerView(position);
            System.out.println("ListSize is:"+lessoncollection.size());
            System.out.println("item listSize is:"+lessoncollection.get(position).size());
        //}
    }

    public void setAdapterForRecyclerView(int position)
    {
        //recyclerView.removeAllViews();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(50);
        //recyclerView.addItemDecoration(spaceItemDecoration);
        LessonListAdapter listAdapter = new LessonListAdapter(lessoncollection.get(position));
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
