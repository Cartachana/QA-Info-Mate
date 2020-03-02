package com.example.qainfomate.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.lang.reflect.Array;

public class Book_for_Sale extends Book implements Parcelable {
    private String stuId;
    private Boolean isAvaiable;

    public Book_for_Sale(String title, String author, String description, String category, String imageUrl, String type, String stuId, Boolean isAvaiable) {
        super(title, author, description, category, imageUrl, type);
        this.stuId = stuId;
        this.isAvaiable = isAvaiable;
    }

    public Book_for_Sale(String stuId, Boolean isAvaiable) {
        this.stuId = stuId;
        this.isAvaiable = isAvaiable;
    }

    public Book_for_Sale() {
    }

    protected Book_for_Sale(Parcel in) {
        super(in);
        stuId = in.readString();
        byte tmpIsAvaiable = in.readByte();
        isAvaiable = tmpIsAvaiable == 0 ? null : tmpIsAvaiable == 1;
    }

    public static final Creator<Book_for_Sale> CREATOR = new Creator<Book_for_Sale>() {
        @Override
        public Book_for_Sale createFromParcel(Parcel in) {
            return new Book_for_Sale(in);
        }

        @Override
        public Book_for_Sale[] newArray(int size) {
            return new Book_for_Sale[size];
        }
    };

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public Boolean getAvaiable() {
        return isAvaiable;
    }

    public void setAvaiable(Boolean avaiable) {
        isAvaiable = avaiable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(stuId);
        dest.writeByte((byte) (isAvaiable ? 1 : 0));
    }

}
