package com.firedating.chat.Extra;

import java.util.Date;

public class NopesClass {
    Date user_noped;
    String user_nopes;

    public NopesClass() {
    }

    public NopesClass(String str, Date date) {
        this.user_nopes = str;
        this.user_noped = date;
    }

    public String getUser_nopes() {
        return this.user_nopes;
    }

    public void setUser_nopes(String str) {
        this.user_nopes = str;
    }

    public Date getUser_noped() {
        return this.user_noped;
    }

    public void setUser_noped(Date date) {
        this.user_noped = date;
    }
}
