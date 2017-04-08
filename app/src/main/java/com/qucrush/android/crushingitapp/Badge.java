package com.qucrush.android.crushingitapp;

/**
 * Created by Garry on 3/31/2017.
 */

public class Badge {
    private String name,desc,earnedDate,isEarned,imgsrc;
    private int id;

    public Badge(int id, String name, String desc, String isEarned, String earnedDate, String imgsrc){
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.earnedDate = earnedDate;
        this.isEarned = isEarned;
        this.imgsrc = imgsrc;
    }

    public String getName(){
        return name;
    }
    public String getDesc(){
        return desc;
    }
    public String getEarnedDate(){
        return earnedDate;
    }
    public String getIsEarned(){
        return isEarned;
    }
    public String getImgsrc(){
        return imgsrc;
    }
    public int getID(){
        return id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setDesc(String desc){
        this.desc = desc;
    }
    public void setEarnedDate(String earnedDate){
        this.earnedDate = earnedDate;
    }
    public void setIsEarned(String isEarned){
        this.isEarned = isEarned;
    }
    public void setImgsrc(String setImgsrc){
        this.imgsrc = imgsrc;

    }
}
