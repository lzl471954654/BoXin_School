package com.boxintech.boxin_school.OtherClass;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by LZL on 2017/5/5.
 */

public class ExitSystem {
    private static List<Activity> activityList;

    public static void init()
    {
        activityList = new LinkedList<>();
    }

    public static List<Activity> getActivityList() {
        return activityList;
    }

    public static void add(Activity activity)
    {
        activityList.add(activity);
    }

    public static void exit()
    {
        for (Activity activity : activityList) {
            if(activity!=null)
                activity.finish();
        }
        System.exit(0);
    }
}
