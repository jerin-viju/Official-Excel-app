package com.greycodes.excel14.pojo;

/**
 * Created by jincy on 17/7/15.
 */
public class MenuFragmentItem {
    int menuImageId;
    String menuItemName;

    public MenuFragmentItem(int menuImageId,String menuItemName)
    {
        this.menuImageId = menuImageId;
        this.menuItemName = menuItemName;
    }

    public int getMenuImageId() {
        return menuImageId;
    }

    public String getMenuItemName() {
        return menuItemName;
    }
}
