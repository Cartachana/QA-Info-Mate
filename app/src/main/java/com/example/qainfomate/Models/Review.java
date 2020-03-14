package com.example.qainfomate.Models;

public class Review {
    private String stuId, ISBN, review;

    public Review() {
    }

    public Review(String stuId, String isbn, String review) {
        this.stuId = stuId;
        this.ISBN = isbn;
        this.review = review;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
