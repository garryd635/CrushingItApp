package com.qucrush.android.crushingitapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * BadgeManager
 *  Communicates with DB to add, modify, add, and distribute badges with the rest of the application
 */

public class BadgeManager {
    private List<Badge> badgeList,completedList,incompletedList,results;
    private List<Task> completedTasks;

    /**
     * Retrieves all badges for the BadgeManager
     */
    public void retrieveDBBadges(){
        badgeList = MainActivity.db.getBadgeList();
    }//retrieveDBBadges()

    /**
     * Returns a list of badges from the DB
     * @return badgeList
     */
    public List<Badge> getAllBadges(){
        badgeList = MainActivity.db.getBadgeList();
        return badgeList;
    }//getAllBadges()

    /**
     * Returns a list of completed Badges
     * @return completedList
     */
    public List<Badge> getCompletedBadges(){
        completedList = new ArrayList<Badge>();
        for(Badge badge: badgeList){
            if(badge.getIsEarned() == "yes"){
                completedList.add(badge);
            }
        }
        return completedList;
    }//getCompletedBadges()

    /**
     * Returns a list of incompleted badges
     * @return incompleteList
     */
    public List<Badge> getInCompletedBadges(){
        incompletedList = new ArrayList<Badge>();
        for(Badge badge: badgeList){
            if(badge.getIsEarned() == "yes"){
                incompletedList.add(badge);
            }
        }
        return incompletedList;
    }//getIncompletedBadges

    /**
     * Check if user unlocked badge based on number of tasks completed
     */
    public List<Badge> taskBadgeCheck(int completedTaskNum, List<Task> completedTasks){
        badgeList = MainActivity.db.getBadgeList();
        results = new ArrayList<Badge>();
        this.completedTasks = completedTasks;
        int workCount = 0;
        int lifeCount = 0;

        if(completedTaskNum >= 1 && badgeList.get(0).getIsEarned().equals("no")){
            results.add(updateBadgeChecked(badgeList.get(0)));
        }
        if(completedTaskNum >= 2 && badgeList.get(1).getIsEarned().equals("no")){
            results.add(updateBadgeChecked(badgeList.get(1)));
        }
        if(completedTasks != null){
            for(int i = 0; i < completedTasks.size(); i++){
                if(completedTasks.get(i).getCategory().equals("Work") && completedTasks.get(i).getCompletion().equals("yes")){
                    workCount++;
                }
                if(completedTasks.get(i).getCategory().equals("Life") && completedTasks.get(i).getCompletion().equals("yes")){
                    lifeCount++;
                }
            }
            if(workCount > 0 && lifeCount > 0 && badgeList.get(2).getIsEarned().equals("no")){
                results.add(updateBadgeChecked(badgeList.get(2)));
            }
        }
        return results;
    }//taskBadgeCheck

    /*
    Used to update badge that was earned through daily feedback report
     */
    public Badge updateBadgeChecked(Badge badge){
        int month,day,year;
        badge.setIsEarned("yes");
        Calendar c = Calendar.getInstance();
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        year = c.get(Calendar.YEAR);
        month += 1;
        badge.setEarnedDate(month + "-" + day + "-" + year);
        MainActivity.db.updateBadge(badge);
        return badge;
    }//updateBadgeChecked
}//BadgeManager()
