package com.firedating.chat.Loves;

import java.util.Date;

public class LovesClass {
    Date user_loved;
    String user_loves;

    public LovesClass() {
    }

    public LovesClass(String str, Date date) {
        this.user_loves = str;
        this.user_loved = date;
    }

    public String getUser_loves() {
        return this.user_loves;
    }

    public void setUser_loves(String str) {
        this.user_loves = str;
    }

    public Date getUser_loved() {
        return this.user_loved;
    }

    public void setUser_loved(Date date) {
        this.user_loved = date;
    }
}
