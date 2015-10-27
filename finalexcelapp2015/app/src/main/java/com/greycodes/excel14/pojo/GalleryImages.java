package com.greycodes.excel14.pojo;

/**
 * Created by jincy on 1/8/15.
 */
public class GalleryImages
{
    private int status;

    private Images[] Images;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Images[] getImages ()
    {
        return Images;
    }

    public void setImages (Images[] Images)
    {
        this.Images = Images;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+status+", Images = "+Images+"]";
    }
}