package com.greycodes.excel14.pojo;

import java.util.ArrayList;

/**
 * Created by jincy on 2/8/15.
 */
public class DaySchedule {

    private String Date;
    private String day;
    private ArrayList<ScheduleTiming> timingsArrayList;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public ArrayList<ScheduleTiming> getTimingsArrayList() {
        return timingsArrayList;
    }

    public void addTiming(ScheduleTiming timing)
    {
        if (timingsArrayList == null)
        {
            timingsArrayList = new ArrayList<>();
        }
        timingsArrayList.add(timing);
    }
}

