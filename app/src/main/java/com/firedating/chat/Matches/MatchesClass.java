package com.firedating.chat.Matches;

import java.util.Date;

public class MatchesClass {
    Date user_matched;
    String user_matches;

    public MatchesClass() {
    }

    public MatchesClass(String str, Date date) {
        this.user_matches = str;
        this.user_matched = date;
    }

    public String getUser_matches() {
        return this.user_matches;
    }

    public void setUser_matches(String str) {
        this.user_matches = str;
    }

    public Date getUser_matched() {
        return this.user_matched;
    }

    public void setUser_matched(Date date) {
        this.user_matched = date;
    }
}
