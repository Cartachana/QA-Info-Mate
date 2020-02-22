package com.example.qainfomate;

public abstract class Book {
    private String title, author, description, category, imageUrl;

    public Book(String title, String author, String description, String category, String imageUrl) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public Book() {
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
}

