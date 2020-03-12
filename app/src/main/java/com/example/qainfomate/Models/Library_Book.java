package com.example.qainfomate.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Library_Book extends Book implements Parcelable {
    private String loanedTo, Edition, ISBN;

    public Library_Book(String title, String author, String description, String category, String imageUrl, String type, String loanedTo, String edition, String ISBN) {
        super(title, author, description, category, imageUrl, type);
        this.loanedTo = loanedTo;
        Edition = edition;
        this.ISBN = ISBN;
    }
    public Library_Book(){

    }

    public String getLoanedTo() {
        return loanedTo;
    }

    public void setLoanedTo(String loanedTo) {
        this.loanedTo = loanedTo;
    }

    public String getEdition() {
        return Edition;
    }

    public void setEdition(String edition) {
        Edition = edition;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    protected Library_Book(Parcel in) {
        super(in);
        loanedTo = in.readString();
        Edition = in.readString();
        ISBN = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(loanedTo);
        dest.writeString(Edition);
        dest.writeString(ISBN);
    }

    public static final Creator<Library_Book> CREATOR = new Creator<Library_Book>() {
        @Override
        public Library_Book createFromParcel(Parcel in) {
            return new Library_Book(in);
        }

        @Override
        public Library_Book[] newArray(int size) {
            return new Library_Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
