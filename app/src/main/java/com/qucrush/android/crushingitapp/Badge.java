package com.qucrush.android.crushingitapp;

/**
 * Badge
 *  Defines the information for a badge object
 */

public class Badge {
    private String name,desc,earnedDate,isEarned,imgsrc;
    private int id;

    /**
     * Constructor
     *
     * @param id Id of the badge assigned by the DB
     * @param name Name of the badge
     * @param desc Description of the badge
     * @param isEarned The earned status of the badge
     * @param earnedDate The date when the badge was earned
     * @param imgsrc The source to retrieve the badge image from the project
     */
    public Badge(int id, String name, String desc, String isEarned, String earnedDate, String imgsrc){
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.earnedDate = earnedDate;
        this.isEarned = isEarned;
        this.imgsrc = imgsrc;
    }

    //Getters and Setters for each variable
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
}//Badge
