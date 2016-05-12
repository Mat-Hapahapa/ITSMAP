package com.mat.firebasetestapp.models;

/**
 * Created by matry on 11-05-2016.
 */
public class User {
    private String name;
    private int age;

    public User(){}

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
