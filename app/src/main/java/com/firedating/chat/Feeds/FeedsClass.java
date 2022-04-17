package com.firedating.chat.Feeds;

import java.util.Date;

public class FeedsClass {
    String feed_cover;
    Date feed_date;
    String feed_like;
    String show_feeds;
    String feed_uid;
    String feed_user;

    public FeedsClass() {
    }

    public FeedsClass(Date date, String str, String str2, String str3, String str4, String str5) {
        this.feed_date = date;
        this.feed_like = str;
        this.feed_cover = str2;
        this.feed_user = str3;
        this.show_feeds = str4;
        this.feed_uid = str5;
    }

    public Date getFeed_date() {
        return this.feed_date;
    }

    public void setFeed_date(Date date) {
        this.feed_date = date;
    }

    public String getFeed_like() {
        return this.feed_like;
    }

    public void setFeed_like(String str) {
        this.feed_like = str;
    }

    public String getFeed_cover() {
        return this.feed_cover;
    }

    public void setFeed_cover(String str) {
        this.feed_cover = str;
    }

    public String getFeed_user() {
        return this.feed_user;
    }

    public void setFeed_user(String str) {
        this.feed_user = str;
    }

    public String getShow_feeds() {
        return show_feeds;
    }

    public void setShow_feeds(String show_feeds) {
        this.show_feeds = show_feeds;
    }

    public String getFeed_uid() {
        return this.feed_uid;
    }

    public void setFeed_uid(String str) {
        this.feed_uid = str;
    }
}
