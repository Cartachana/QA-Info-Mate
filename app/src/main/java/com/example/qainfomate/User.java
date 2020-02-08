package com.example.qainfomate;

public class User {
    private String stuID, fname, sname, email, password;

    public User(String stuID, String fname, String sname, String email, String password) {
        this.stuID = stuID;
        this.fname = fname;
        this.sname = sname;
        this.email = email;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
