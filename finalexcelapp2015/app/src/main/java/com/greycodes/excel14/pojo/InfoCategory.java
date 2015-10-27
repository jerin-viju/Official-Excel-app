package com.greycodes.excel14.pojo;

import java.util.ArrayList;

/**
 * Created by jincy on 26/7/15.
 */
public class InfoCategory {
    private String title;
    private String image;
//    private String color;

    private ArrayList<CommonCategoryItem> infoListItems;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
//
//    public String getColor() {
//        return color;
//    }
//
//    public void setColor(String color) {
//        this.color = color;
//    }

    public ArrayList<CommonCategoryItem> getList() {
        return infoListItems;
    }

    public void addtoInfoList(CommonCategoryItem item)
    {
        if (infoListItems == null)
        {
            infoListItems = new ArrayList<>();
        }
        infoListItems.add(item);
    }
}
