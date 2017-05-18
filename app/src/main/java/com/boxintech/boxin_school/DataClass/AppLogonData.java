package com.boxintech.boxin_school.DataClass;

import okhttp3.OkHttpClient;

/**
 * Created by LZL on 2017/3/29.
 */

public  class AppLogonData {
    private static final String Logon_Host = "http://222.24.62.120/default2.aspx";
    private static final String Secret_Image_URL = "http://222.24.62.120/CheckCode.aspx";
    private static String VIEWSTATE = "dDwtNTE2MjI4MTQ7Oz5O9kSeYykjfN0r53Yqhqckbvd83A==";
    private static String __VIEWSTATE;
    private static String cookies = null;
    private static String name = null;
    private static OkHttpClient okHttpClient;
    private static Student student = null;


    public static void set_viewstate(String _viewstate) {
        __VIEWSTATE = _viewstate;
    }

    public static String get_viewstate() {
        return __VIEWSTATE;
    }

    public static Student getStudent() {
        return student;
    }

    public static void setStudent(Student student) {
        AppLogonData.student = student;
    }

    public static String getCookies() {
        return cookies;
    }

    public static void setCookies(String cookies) {
        AppLogonData.cookies = cookies;
    }

    public static String getSecret_Image_URL() {
        return Secret_Image_URL;
    }

    public static String getLogon_Host() {
        return Logon_Host;
    }

    public static String getVIEWSTATE() {
        return VIEWSTATE;
    }

    public static void setVIEWSTATE(String VIEWSTATE) {
        AppLogonData.VIEWSTATE = VIEWSTATE;
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public static void setOkHttpClient(OkHttpClient okHttpClient) {
        AppLogonData.okHttpClient = okHttpClient;
    }
}
