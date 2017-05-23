package com.boxintech.boxin_school.DataClass;
import java.io.Serializable;

/**
 * Created by LZL on 2017/4/5.
 */

public class RunRoomData implements Serializable {
    String id;
    String user_id;
    String title;
    String yp_date;
    String run_place;

    public String getYp_date() {
        return yp_date;
    }

    public void setYp_date(String yp_date) {
        this.yp_date = yp_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRun_place() {
        return run_place;
    }

    public void setRun_place(String run_place) {
        this.run_place = run_place;
    }

}
