package com.example.qainfomate.Models;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class Book implements Parcelable {
    private String title, author, description, category, imageUrl, type;

    public Book(String title, String author, String description, String category, String imageUrl, String type) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
        this.type = type;
    }

    public Book() {
    }

    protected Book(Parcel in) {
        title = in.readString();
        author = in.readString();
        description = in.readString();
        category = in.readString();
        imageUrl = in.readString();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeString(imageUrl);
        dest.writeString(type);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

