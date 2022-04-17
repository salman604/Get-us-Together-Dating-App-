package com.firedating.chat.Favorite;

import java.util.Date;

public class FavoritesClass {
    String user_favorite;
    Date user_favorited;

    public FavoritesClass() {
    }

    public FavoritesClass(String str, Date date) {
        this.user_favorite = str;
        this.user_favorited = date;
    }

    public String getUser_favorite() {
        return this.user_favorite;
    }

    public void setUser_favorite(String str) {
        this.user_favorite = str;
    }

    public Date getUser_favorited() {
        return this.user_favorited;
    }

    public void setUser_favorited(Date date) {
        this.user_favorited = date;
    }
}
