package com.qucrush.android.crushingitapp;

/**
 * Created by Garry on 2/12/2017.
 */

public class Task {
    private String name;
    private String desc;
    private String date;
    private String time;
    private String category;

    public Task(String name, String desc, String date, String time, String category){
        this.name = name;
        this.desc = desc;
        this.date = date;
        this.time = time;
        this.category = category;
    }

    public String getName(){
        return name;
    }
    public String getDesc(){
        return desc;
    }
    public String getDate(){
        return date;
    }
    public String getTime(){
        return time;
    }
    public String getCategory(){
        return category;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setDesc(String desc){
        this.desc = desc;
    }
    public void setDate(String date){
        this.date = date;
    }
    public void setTime(String time){
        this.time = time;
    }
    public void setCategory(String category){
        this.category = category;
    }
}
