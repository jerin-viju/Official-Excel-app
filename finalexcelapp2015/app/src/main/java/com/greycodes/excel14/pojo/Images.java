package com.greycodes.excel14.pojo;

/**
 * Created by jincy on 15/8/15.
 */
public class Images
{
    private String author;

    private String url;

    public String getAuthor ()
    {
        return author;
    }

    public void setAuthor (String author)
    {
        this.author = author;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [author = "+author+", url = "+url+"]";
    }
}
