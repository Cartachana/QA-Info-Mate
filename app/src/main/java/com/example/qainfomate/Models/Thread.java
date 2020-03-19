package com.example.qainfomate.Models;

public class Thread {
    String fromTopic, fromID, date, message;

    public Thread() {
    }

    public Thread(String fromTopic, String fromID, String date, String message) {
        this.fromTopic = fromTopic;
        this.fromID = fromID;
        this.date = date;
        this.message = message;
    }

    public String getFromTopic() {
        return fromTopic;
    }

    public void setFromTopic(String fromTopic) {
        this.fromTopic = fromTopic;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
