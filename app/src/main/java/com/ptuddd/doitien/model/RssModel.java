package com.ptuddd.doitien.model;

/**
 * Created by Nhatran241 - 10/6/20202
 */

public class RssModel {
    private String rssTitle;
    private String link;
    private String rssDescription;

    public RssModel(String rssTitle,String link, String rssDescription) {
        this.rssTitle = rssTitle;
        this.link =link;
        this.rssDescription = rssDescription;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRssTitle() {
        return rssTitle;
    }

    public void setRssTitle(String rssTitle) {
        this.rssTitle = rssTitle;
    }

    public String getRssDescription() {
        return rssDescription;
    }

    public void setRssDescription(String rssDescription) {
        this.rssDescription = rssDescription;
    }
}
