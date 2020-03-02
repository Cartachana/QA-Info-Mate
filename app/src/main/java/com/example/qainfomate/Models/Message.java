package com.example.qainfomate.Models;

import java.util.Date;

public class Message {
    String IDfrom, IDto, bookTitle, message, date;
    Boolean isRead;

    public Message(String IDfrom, String IDto, String bookTitle, String message, String date, Boolean isRead) {
        this.IDfrom = IDfrom;
        this.IDto = IDto;
        this.message = message;
        this.bookTitle = bookTitle;
        this.date = date;
        this.isRead = isRead;
    }

    public Message() {
    }

    public String getIDfrom() {
        return IDfrom;
    }

    public void setIDfrom(String IDfrom) {
        this.IDfrom = IDfrom;
    }

    public String getIDto() {
        return IDto;
    }

    public void setIDto(String IDto) {
        this.IDto = IDto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookID) {
        this.bookTitle = bookID;
    }
}
