package com.greycodes.excel14.pojo;

import java.util.ArrayList;


public class TalkCategory {
    private String title;
    private String imageId;
//    private String color;
    private String about;
    private String registration;

    private EventSchedule schedule;

    private ArrayList<CommonCategoryItem> speakers;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

//    public String getColor() {
//        return color;
//    }
//
//    public void setColor(String color) {
//        this.color = color;
//    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public EventSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(EventSchedule schedule) {
        this.schedule = schedule;
    }

    public ArrayList<CommonCategoryItem> getSpeakers() {
        return speakers;
    }

    public void addSpeaker(CommonCategoryItem speaker)
    {
        if (speakers == null)
        {
            speakers = new ArrayList<>();
        }
        speakers.add(speaker);
    }
}
