package com.greycodes.excel14.pojo;

import java.util.ArrayList;

/**
 * Created by jincy on 2/8/15.
 */
public class ScheduleTiming {
    private String time;
    ArrayList<CompetitionEvent> scheduleEvents;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<CompetitionEvent> getScheduleEvents() {
        return scheduleEvents;
    }

    public void addScheduleEvent(CompetitionEvent event)
    {
        if (scheduleEvents == null)
        {
            scheduleEvents = new ArrayList<>();
        }
        scheduleEvents.add(event);
    }
}
