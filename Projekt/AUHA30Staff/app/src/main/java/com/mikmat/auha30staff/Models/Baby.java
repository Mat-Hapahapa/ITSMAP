package com.mikmat.auha30staff.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by matry on 15-05-2016.
 */
public class Baby implements Serializable {
    private String ID;
    private String name;
    private String gender;
    private Date birthday;
    private String email;
    private String caretaker;
    private String firebaseRef;
    private String PhoneNr;
    private String ParentName;

    public Baby(){}

    public Baby(String id, String name, String gender, Date birthday, String email, String caretaker){
        this.ID = id;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.email = email;
        this.caretaker = caretaker;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
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

    public String getFirebaseRef() {
        return firebaseRef;
    }

    public void setFirebaseRef(String firebaseRef) {
        this.firebaseRef = firebaseRef;
    }

    public String getPhoneNr() {
        return PhoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        PhoneNr = phoneNr;
    }

    public String getParentName() {
        return ParentName;
    }

    public void setParentName(String parentName) {
        ParentName = parentName;
    }
}
