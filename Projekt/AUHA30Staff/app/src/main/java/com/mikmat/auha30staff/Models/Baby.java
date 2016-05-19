package com.mikmat.auha30staff.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by matry on 15-05-2016.
 */
public class Baby implements Serializable {
    private int ID;
    private String name;
    private String gender;
    private Date birthday;
    private String email;
    private String caretaker;

    public Baby(){}

    public Baby(int id, String name, String gender, Date birthday, String email, String caretaker){
        this.ID = id;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.email = email;
        this.caretaker = caretaker;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCaretaker() {
        return caretaker;
    }

    public void setCaretaker(String caretaker) {
        this.caretaker = caretaker;
    }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }
}
