package com.boxintech.boxin_school.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boxintech.boxin_school.Activity.Logon_Activity;
import com.boxintech.boxin_school.Adapter.LessonFragmentPagerAdapter;
import com.boxintech.boxin_school.DataClass.AppCache;
import com.boxintech.boxin_school.DataClass.AppLogonData;
import com.boxintech.boxin_school.OtherClass.ParseDataFromHtml;
import com.boxintech.boxin_school.R;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by LZL on 2017/4/19.
 */

public class newLessonTableFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    ViewPager viewPager;
    TabLayout tabLayout;
    View root;
    List<Fragment> fragmentList;
    final static int REFRESH_LESSON = 1;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case REFRESH_LESSON:
                    /*List<List<Map<String,String>>> lesson_list = (List<List<Map<String,String>>>)msg.obj;
                    for(int i = 0;i<5;i++)
                    {
                        ((newLessonListFragment)fragmentList.get(i)).refreshLesson(lesson_list.get(i));
                    }*/
                    onTabSelected(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()));
                    break;
                default:break;
            }
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("viewpager on create!!");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.new_lesson_table,container,false);
//        initView();
        return  root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public void initView()
    {
        System.out.println("viewpager init!!");
        viewPager =(ViewPager) root.findViewById(R.id.lesson_viewpager);
        tabLayout = (TabLayout)root.findViewById(R.id.lesson_table_tab_new);

        fragmentList = new LinkedList<>();
        for(int i = 0;i<5;i++)
        {
            fragmentList.add(newLessonListFragment.newInstace(i));
        }
        LessonFragmentPagerAdapter adapter = new LessonFragmentPagerAdapter(getChildFragmentManager(),fragmentList,getContext());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                int width = page.getWidth();
                int height = page.getHeight();
                //這裏只對右邊的view做了操作
                if (position > 0 && position <= 1) {//right scorlling
                    //position是1.0->0,但是沒有等於0
                    //Log.e(TAG, "right----position====" + position);
                    //設置該view的X軸不動
                    page.setTranslationX(-width * position);
                    //設置縮放中心點在該view的正中心
                    page.setPivotX(width / 2);
                    page.setPivotY(height / 2);
                    //設置縮放比例（0.0，1.0]
                    page.setScaleX(1 - position);
                    page.setScaleY(1 - position);

                } else if (position >= -1 && position < 0) {//left scrolling

                } else {//center

                }
            }
        });
        if (AppCache.getLesson_collection()==null)
        {
            getLessonTable();
        }
        else
        {
            /*fragmentList.get(0).onAttach(getContext());
            fragmentList.get(1).onAttach(getContext());
            fragmentList.get(0).onc*/
            //onTabSelected(tabLayout.getTabAt(0));
            //onTabSelected(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()));
        }
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
                if(ParseDataFromHtml.getVIEWSTATE(s)==null)
                {
                    handler.post(noTeacherSuggestion);
                    return;
                }
                List<List<Map<String,String>>> Lesson_collection = ParseDataFromHtml.getLessonsTable(s);
                AppCache.setLesson_collection(Lesson_collection);
                Message message = new Message();
                message.what = REFRESH_LESSON;
                message.obj = Lesson_collection;
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

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int postition = tab.getPosition();
        System.out.println("Tab ："+postition);
        if(AppCache.getLesson_collection()!=null)
        {
            ((newLessonListFragment)fragmentList.get(postition)).refreshLesson(AppCache.getLesson_collection().get(postition));
        }
        else
        {
            getLessonTable();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    Runnable noTeacherSuggestion = new Runnable() {
        @Override
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("对不起，您未评价教师，暂时无法进入系统。");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getContext(), Logon_Activity.class);
                    //startActivity(intent);
                    //getActivity().finish();
                }
            });
            builder.setCancelable(false);
            builder.create().show();
        }
    };
}
