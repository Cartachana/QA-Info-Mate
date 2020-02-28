package com.example.qainfomate.Models;

public class User {
    private String stuID, fname, sname, email, password;

    public User(String stuID, String fname, String sname) {
        this.stuID = stuID;
        this.fname = fname;
        this.sname = sname;
    }

    public User() {
    }

    public String getStuID() {
        return stuID;
    }

    public void setStuID(String stuID) {
        this.stuID = stuID;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

}
