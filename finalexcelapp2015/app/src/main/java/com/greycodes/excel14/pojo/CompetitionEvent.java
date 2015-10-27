package com.greycodes.excel14.pojo;

import java.util.ArrayList;

public class CompetitionEvent
{
    private String id;
    private String title;
    private String image;
    private String about;
    private String registration;
    private String eventLink;
    private Boolean going;

    private ArrayList<EventFormat> eventFormats;
    private ArrayList<String> rules;

    private ArrayList<EventContact> contacts;

    private EventSchedule schedule;

    private EventPrize prize;

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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<EventFormat> getEventFormats() {
        return eventFormats;
    }

    public ArrayList<String> getRules()
    {
        return rules;
    }

    public EventSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(EventSchedule schedule) {
        this.schedule = schedule;
    }

    public EventPrize getPrize() {
        return prize;
    }

    public void setPrize(EventPrize prize) {
        this.prize = prize;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getEventLink() {
        return eventLink;
    }

    public void setEventLink(String eventLink) {
        this.eventLink = eventLink;
    }

    public Boolean getGoing() {
        return going;
    }

    public void setGoing(Boolean going) {
        this.going = going;
    }

    public ArrayList<EventContact> getContacts() {
        return contacts;
    }

    public void addEventFormat(EventFormat eventFormat)
    {
        if (eventFormats == null)
        {
            eventFormats = new ArrayList<>();
        }
        eventFormats.add(eventFormat);
    }

    public void addRule(String rule)
    {
        if(rules == null)
        {
            rules = new ArrayList<>();
        }
        rules.add(rule);
    }

    public void addContact(EventContact contact)
    {
        if (contacts == null)
        {
            contacts = new ArrayList<>();
        }
        contacts.add(contact);
    }
}
