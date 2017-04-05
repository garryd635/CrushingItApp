package com.qucrush.android.crushingitapp;

/**
 * Created by Garry on 3/31/2017.
 */

public class Badge {
    private String name,desc,earnedDate,isEarned,imgsrc;

    public Badge(String name, String desc, String earnedDate, String isEarned, String imgsrc){
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
