package com.example.qainfomate.Models;

public class Book_for_Sale extends Book {
    private String stuId;
    private Boolean isAvaiable;

    public Book_for_Sale(String title, String author, String description, String category, String imageUrl, String stuId, Boolean isAvaiable) {
        super(title, author, description, category, imageUrl);
        this.stuId = stuId;
        this.isAvaiable = isAvaiable;
    }

    public Book_for_Sale(String stuId, Boolean isAvaiable) {
        this.stuId = stuId;
        this.isAvaiable = isAvaiable;
    }

    public Book_for_Sale() {
    }

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
}
