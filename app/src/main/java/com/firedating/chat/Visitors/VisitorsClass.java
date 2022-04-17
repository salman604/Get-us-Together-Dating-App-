package com.firedating.chat.Visitors;

import java.util.Date;

public class VisitorsClass {
    Date user_visited;
    String user_visitor;

    public VisitorsClass() {
    }

    public VisitorsClass(String str, Date date) {
        this.user_visitor = str;
        this.user_visited = date;
    }

    public String getUser_visitor() {
        return this.user_visitor;
    }

    public void setUser_visitor(String str) {
        this.user_visitor = str;
    }

    public Date getUser_visited() {
        return this.user_visited;
    }

    public void setUser_visited(Date date) {
        this.user_visited = date;
    }
}
