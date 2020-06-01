package com.example.ijobs.data;

import java.util.Date;

public class Chat {
    private Date date;
    private String sender;
    private String receiver;
    private String message;

    public Chat() {

    }

    public Chat(String sender, String receiver, String message) {

        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.date = new Date();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
