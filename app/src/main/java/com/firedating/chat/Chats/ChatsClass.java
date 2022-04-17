package com.firedating.chat.Chats;

import java.util.Date;

public class ChatsClass {
    private Date chat_dateseen;
    private Date chat_datesent;
    private String chat_message;
    private String chat_receiver;
    private String chat_seenchat;
    private String chat_sender;
    private String delete_receiver;
    private String delete_sender;

    public ChatsClass() {
    }

    public ChatsClass(Date date, Date date2, String str, String str2, String str3, String str4, String str5, String str6) {
        this.chat_datesent = date;
        this.chat_dateseen = date2;
        this.chat_sender = str;
        this.chat_receiver = str2;
        this.chat_message = str3;
        this.chat_seenchat = str4;
        this.delete_sender = str5;
        this.delete_receiver = str6;
    }

    public Date getChat_datesent() {
        return this.chat_datesent;
    }

    public void setChat_datesent(Date date) {
        this.chat_datesent = date;
    }

    public Date getChat_dateseen() {
        return this.chat_dateseen;
    }

    public void setChat_dateseen(Date date) {
        this.chat_dateseen = date;
    }

    public String getChat_sender() {
        return this.chat_sender;
    }

    public void setChat_sender(String str) {
        this.chat_sender = str;
    }

    public String getChat_receiver() {
        return this.chat_receiver;
    }

    public void setChat_receiver(String str) {
        this.chat_receiver = str;
    }

    public String getChat_message() {
        return this.chat_message;
    }

    public void setChat_message(String str) {
        this.chat_message = str;
    }

    public String getChat_seenchat() {
        return this.chat_seenchat;
    }

    public void setChat_seenchat(String str) {
        this.chat_seenchat = str;
    }

    public String getDelete_sender() {
        return this.delete_sender;
    }

    public void setDelete_sender(String str) {
        this.delete_sender = str;
    }

    public String getDelete_receiver() {
        return this.delete_receiver;
    }

    public void setDelete_receiver(String str) {
        this.delete_receiver = str;
    }
}
