package com.qucrush.android.crushingitapp;

/**
 * Created by Garry on 2/12/2017.
 */

public class Task {
    private int id;
    private String name;
    private String desc;
    private String date;
    private String time;
    private String category;
    private String completion;
    private String recurring;

    public Task(int id, String name, String desc, String date, String time, String category, String completion, String recurring){
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.date = date;
        this.time = time;
        this.category = category;
        this.completion = completion;
        this.recurring = recurring;
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
    public int getId(){
        return id;
    }
    public String getCompletion(){
        return completion;
    }
    public String getRecurring(){
        return recurring;
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
    public void setId(int id) {
        this.id = id;
    }
    public void setCompletion(String completion){
        this.completion = completion;
    }
    public void setRecurring(String recurring){
        this.recurring = recurring;
    }
}
