package com.firedating.chat.Likes;

import java.util.Date;

public class LikesClass {
    Date user_liked;
    String user_likes;

    public LikesClass() {
    }

    public LikesClass(String str, Date date) {
        this.user_likes = str;
        this.user_liked = date;
    }

    public String getUser_likes() {
        return this.user_likes;
    }

    public void setUser_likes(String str) {
        this.user_likes = str;
    }

    public Date getUser_liked() {
        return this.user_liked;
    }

    public void setUser_liked(Date date) {
        this.user_liked = date;
    }
}
