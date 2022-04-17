package com.firedating.chat.Message;

import java.util.Date;

public class MessageClass {
    Date user_datesent;
    String user_message;
    String user_receiver;
    String user_sender;
    String user_unread;

    public MessageClass() {
    }

    public MessageClass(Date date, String str, String str2, String str3, String str4) {
        this.user_datesent = date;
        this.user_sender = str;
        this.user_receiver = str2;
        this.user_message = str3;
        this.user_unread = str4;
    }

    public Date getUser_datesent() {
        return this.user_datesent;
    }

    public void setUser_datesent(Date date) {
        this.user_datesent = date;
    }

    public String getUser_sender() {
        return this.user_sender;
    }

    public void setUser_sender(String str) {
        this.user_sender = str;
    }

    public String getUser_receiver() {
        return this.user_receiver;
    }

    public void setUser_receiver(String str) {
        this.user_receiver = str;
    }

    public String getUser_message() {
        return this.user_message;
    }

    public void setUser_message(String str) {
        this.user_message = str;
    }

    public String getUser_unread() {
        return this.user_unread;
    }

    public void setUser_unread(String str) {
        this.user_unread = str;
    }
}
