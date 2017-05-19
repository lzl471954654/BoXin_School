package com.boxintech.boxin_school.DataClass;

/**
 * Created by LZL on 2017/3/25.
 */

public class Student {
    String name;
    String xh;
    String xy;
    String zy;
    String classes;
    String school = "西安邮电大学";

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("");
        builder.append(school)
                .append("\n")
                .append(xy)
                .append("\n")
                .append(zy)
                .append("\n")
                .append(classes)
                .append("\n"+xh+"\n")
                .append(name);
        return builder.toString();
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSchool() {
        return school;
    }

    public String getClasses() {
        return classes;

    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getXy() {
        return xy;
    }

    public void setXy(String xy) {
        this.xy = xy;
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }
}
