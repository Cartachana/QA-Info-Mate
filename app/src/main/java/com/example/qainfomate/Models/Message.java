package com.example.qainfomate.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Message implements Parcelable, GenericList {
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

    protected Message(Parcel in) {
        IDfrom = in.readString();
        IDto = in.readString();
        bookTitle = in.readString();
        message = in.readString();
        date = in.readString();
        byte tmpIsRead = in.readByte();
        isRead = tmpIsRead == 0 ? null : tmpIsRead == 1;
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(IDfrom);
        dest.writeString(IDto);
        dest.writeString(bookTitle);
        dest.writeString(message);
        dest.writeString(date);
        dest.writeByte((byte) (isRead == null ? 0 : isRead ? 1 : 2));
    }
}
