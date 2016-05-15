package com.mikmat.auha30staff.Models;

import java.util.Date;

/**
 * Created by matry on 15-05-2016.
 */
public class Baby {
    private int ID;
    private String name;
    private Date birthday;

    public Baby(){}

    public Baby(int id, String name){
        this.ID = id;
        this.name = name;
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
}
