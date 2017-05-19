package com.boxintech.boxin_school.OtherClass;

import com.boxintech.boxin_school.DataClass.RunData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by LZL on 2017/5/15.
 */

public class ParseDataFromJSON {
    public static List<RunData> getPersonRunDataList(String s)
    {
        List<RunData> list = new LinkedList<>();
        JSONArray jsonArray = null;
        try
        {
            jsonArray = new JSONArray(s);
            for(int i = 0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                RunData runData = new RunData();
                runData.setUser_id(jsonObject.getString("user_id"));
                runData.setRun_type(jsonObject.getString("run_type"));
                runData.setRun_date(jsonObject.getString("run_date"));
                runData.setRun_time(jsonObject.getString("run_time"));
                runData.setRun_km(jsonObject.getString("run_km"));
                runData.setRun_person_num(jsonObject.getString("run_person_num"));
                list.add(runData);
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return list;
    }
}
