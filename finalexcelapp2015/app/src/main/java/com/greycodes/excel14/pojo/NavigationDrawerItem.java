package com.greycodes.excel14.pojo;

public class NavigationDrawerItem
{
    private String title;
    private int imageID;
    private int sectionDividerColor;

    public NavigationDrawerItem() {}

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getSectionDividerColor() {
        return sectionDividerColor;
    }

    public void setSectionDividerColor(int sectionDividerColor) {
        this.sectionDividerColor = sectionDividerColor;
    }
}
