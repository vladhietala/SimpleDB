package com.example.vlad.simpledb;

/**
 * Created by Vlad on 11/05/2016.
 */
public class Student {
    private int id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
