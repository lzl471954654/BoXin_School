package com.boxintech.boxin_school.DataClass;

import android.content.Context;

import java.util.List;
import java.util.Map;

/**
 * Created by LZL on 2017/3/30.
 */

public class AppCache {
    public static boolean lesson_edit = true; //选课界面是否为可编辑状态

    private static Context mainContext;

    private static List<List<Map<String,String>>> lesson_collection;

    private static List<String> XNLIST;

    private static List<Map<String,String>> scores_list;
    private static List<RunHistoryDataItem> person_run_data_list;

    private static String XQ = "";
    private static String XN = "";

    private static int person_run_times = 0;
    private static int person_run_all_km = 0;
    private static int person_run_all_people_count = 0;
    private static int person_run_sum_hours = 0;

    public static int getPerson_run_all_km() {
        return person_run_all_km;
    }

    public static int getPerson_run_all_people_count() {
        return person_run_all_people_count;
    }

    public static int getPerson_run_sum_hours() {
        return person_run_sum_hours;
    }

    public static void setPerson_run_all_km(int person_run_all_km) {
        AppCache.person_run_all_km = person_run_all_km;
    }

    public static void setPerson_run_all_people_count(int person_run_all_people_count) {
        AppCache.person_run_all_people_count = person_run_all_people_count;
    }

    public static void setPerson_run_sum_hours(int person_run_sum_hours) {
        AppCache.person_run_sum_hours = person_run_sum_hours;
    }

    public static void setPerson_run_times(int person_run_times) {
        AppCache.person_run_times = person_run_times;
    }

    public static int getPerson_run_times() {
        return person_run_times;
    }

    public static void setPerson_run_data_list(List<RunHistoryDataItem> person_run_data_list) {
        AppCache.person_run_data_list = person_run_data_list;
    }

    public static List<RunHistoryDataItem> getPerson_run_data_list() {
        return person_run_data_list;
    }

    public static boolean compareXNXQ(String xq, String xn)
    {
        return (xn.equals(XN)&&xq.equals(xq));
    }

    public static void setXQ(String XQ) {
        AppCache.XQ = XQ;
    }

    public static void setXN(String XN) {
        AppCache.XN = XN;
    }

    public static void setScores_list(List<Map<String, String>> scores_list) {
        AppCache.scores_list = scores_list;
    }

    public static List<Map<String, String>> getScores_list() {
        return scores_list;
    }

    public static List<String> getXNLIST() {
        return XNLIST;
    }

    public static void setXNLIST(List<String> XNLIST) {
        AppCache.XNLIST = XNLIST;
    }

    public static Context getMainContext() {
        return mainContext;
    }

    public static boolean isLesson_edit() {
        return lesson_edit;
    }

    public static void setLesson_edit(boolean lesson_edit) {
        AppCache.lesson_edit = lesson_edit;
    }

    public static void setMainContext(Context mainContext) {
        AppCache.mainContext = mainContext;
    }

    public static List<List<Map<String, String>>> getLesson_collection() {
        return lesson_collection;
    }

    public static void setLesson_collection(List<List<Map<String, String>>> lesson_collection) {
        AppCache.lesson_collection = lesson_collection;
    }
}
