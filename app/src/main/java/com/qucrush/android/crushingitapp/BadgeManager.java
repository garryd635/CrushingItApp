package com.qucrush.android.crushingitapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Garry on 4/8/2017.
 */

public class BadgeManager {
    private List<Badge> badgeList,completedList,incompletedList,results;
    //private List<String> results;


    public void retrieveDBBadges(){
        badgeList = MainActivity.db.getBadgeList();
    }//retrieveDBBadges()

    public List<Badge> getAllBadges(){
        badgeList = MainActivity.db.getBadgeList();
        return badgeList;
    }

    public List<Badge> getCompletedBadges(){
        completedList = new ArrayList<Badge>();
        for(Badge badge: badgeList){
            if(badge.getIsEarned() == "yes"){
                completedList.add(badge);
            }
        }
        return completedList;
    }//getCompletedBadges()

    public List<Badge> getInCompletedBadges(){
        incompletedList = new ArrayList<Badge>();
        for(Badge badge: badgeList){
            if(badge.getIsEarned() == "yes"){
                incompletedList.add(badge);
            }
        }
        return incompletedList;
    }

    /**
     * Check if user unlocked badge based on number of tasks completed
     */
    public List<Badge> taskBadgeCheck(int completedTaskNum){
        badgeList = MainActivity.db.getBadgeList();
        results = new ArrayList<Badge>();
        int month,day,year;
        System.out.println("***BADGE NUM " + completedTaskNum);
        if(completedTaskNum >= 2 && badgeList.get(2).getIsEarned().equals("no")){
            badgeList.get(1).setIsEarned("yes");
            Calendar c = Calendar.getInstance();
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            year = c.get(Calendar.YEAR);
            month += 1;
            badgeList.get(1).setEarnedDate(month + "-" + day + "-" + year);
            MainActivity.db.updateBadge(badgeList.get(1));
            results.add(badgeList.get(1));
        }
        return results;
    }
}
