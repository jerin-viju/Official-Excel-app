package com.greycodes.excel14.pojo;

import java.util.ArrayList;

public class CompetitionCategory
{
    private String id;
    private String title;
//    private String color;
    private String imageURL;
    private ArrayList<CompetitionEvent> events;

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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

//    public String getColor() {
//        return color;
//    }
//
//    public void setColor(String color) {
//        this.color = color;
//    }

    public ArrayList<CompetitionEvent> getEvents() {
        return events;
    }

    public void addEvent(CompetitionEvent event)
    {
        if (events == null)
        {
            events = new ArrayList<>();
        }
        events.add(event);
    }
}
