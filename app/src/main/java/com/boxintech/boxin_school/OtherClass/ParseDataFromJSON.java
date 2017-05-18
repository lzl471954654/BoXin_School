package com.boxintech.boxin_school.OtherClass;

import com.boxintech.boxin_school.DataClass.RunHistoryDataItem;
import com.boxintech.boxin_school.DataClass.RunRoomData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by LZL on 2017/5/15.
 */

public class ParseDataFromJSON {
    public static List<RunRoomData> getRoomList(String s)
    {
        List<RunRoomData> list = new LinkedList<>();
        JSONArray jsonArray = null;
        try
        {
            jsonArray = new JSONArray(s);
            for(int i = 0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                RunRoomData runRoomData = new RunRoomData();
                runRoomData.setId(jsonObject.getString("id"));
                runRoomData.setRun_place(jsonObject.getString("run_place"));
                runRoomData.setTitle(jsonObject.getString("title"));
                runRoomData.setUser_id(jsonObject.getString("user_id"));
                runRoomData.setYp_date(jsonObject.getString("yp_date"));
                list.add(runRoomData);
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public static List<RunHistoryDataItem> getPersonRunDataList(String s)
    {
        List<RunHistoryDataItem> list = new LinkedList<>();
        JSONArray jsonArray = null;
        try
        {
            jsonArray = new JSONArray(s);
            for(int i = 0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                RunHistoryDataItem runData = new RunHistoryDataItem();
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
