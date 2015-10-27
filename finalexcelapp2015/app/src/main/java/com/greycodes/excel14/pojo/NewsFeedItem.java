package com.greycodes.excel14.pojo;

/**
 * Created by jincy on 4/8/15.
 */
public class NewsFeedItem
{
    private String author;

    private String description;

    private String imageurl;

    private String heading;

    public String getAuthor ()
    {
        return author;
    }

    public void setAuthor (String author)
    {
        this.author = author;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getImageurl ()
    {
        return imageurl;
    }

    public void setImageurl (String imageurl)
    {
        this.imageurl = imageurl;
    }

    public String getHeading ()
    {
        return heading;
    }

    public void setHeading (String heading)
    {
        this.heading = heading;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [author = "+author+", description = "+description+", imageurl = "+imageurl+", heading = "+heading+"]";
    }
}

