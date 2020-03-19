package com.example.qainfomate.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Topic implements GenericList, Parcelable {
    String stuId, subject, desc, date;

    public Topic() {
    }

    public Topic(String stuId, String subject, String desc, String date) {
        this.stuId = stuId;
        this.subject = subject;
        this.desc = desc;
        this.date = date;
    }

    protected Topic(Parcel in) {
        stuId = in.readString();
        subject = in.readString();
        desc = in.readString();
        date = in.readString();
    }

    public static final Creator<Topic> CREATOR = new Creator<Topic>() {
        @Override
        public Topic createFromParcel(Parcel in) {
            return new Topic(in);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stuId);
        dest.writeString(subject);
        dest.writeString(desc);
        dest.writeString(date);
    }
}
