package com.qucrush.android.crushingitapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * HomePageFragment2
 *
 * Contains contents for the second screen of the homepage.
 */

public class HomePageFragment2 extends Fragment{
    //Instance Variables
    View myView;
    LayoutInflater inflater;
    ViewGroup container;
    ListView upcomingList;
    HomeUpcomingTaskAdapter dataAdapter;
    List<Task> taskList,modifiedTaskList;
    @Nullable
    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        myView = inflater.inflate(R.layout.home_2, container, false);
        upcomingList = (ListView) myView.findViewById(R.id.homeUpcomingList);
        taskList = MainActivity.tm.retrieveTasks();

        //Sort the task lists by date when there are tasks existing
        // as of right now, the sorting only accounts for the date, not the time.
        if(taskList != null){
            Collections.sort(taskList, new Comparator<Task>() {
                @Override
                public int compare(Task o1, Task o2) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM");
                    Date date1 = null;
                    Date date2 = null;
                    Calendar cal1 = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    String[] time1, time2;
                    int temp;
                    time1 = o1.getTime().split(" ");
                    time2 = o2.getTime().split(" ");
                    String[] splitTime1 = time1[0].split(":");
                    String[] splitTime2 = time1[0].split(":");
                    if(time1[1].equals("PM")){
                        temp = Integer.parseInt(splitTime1[0]);
                        temp += 12;
                        splitTime1[0] = Integer.toString(temp);
                    }
                    if(time2[1].equals("PM")){
                        temp = Integer.parseInt(splitTime2[0]);
                        temp += 12;
                        splitTime2[0] = Integer.toString(temp);
                    }
                    String tempString1 = splitTime1[0] + ":" + splitTime1[1];
                    String tempString2 = splitTime2[0] + ":" + splitTime2[1];
                    try{
                        date1 = sdf.parse(o1.getDate() + " " + tempString1);
                        date2 = sdf.parse(o2.getDate() + " " + tempString2);
                    }catch (ParseException e){

                    }
                    cal1.setTime(date1);
                    cal2.setTime(date2);
                    if(cal1.before(cal2)){
                        return -1;
                    }else if (cal1.after(cal2)){
                        return 1;
                    }else{
                        return 0;
                    }
                }
            });//Collections.sort
        }//if

        //Get the taskLists with removed past dates
        modifiedTaskList = removePastDate(taskList);
        //Set the modified list to the listview
        if(modifiedTaskList != null){
            dataAdapter = new HomeUpcomingTaskAdapter(getActivity(),
                    R.layout.home_3, modifiedTaskList);
            upcomingList.setAdapter(dataAdapter);
        }
        return myView;
    }//onCreateView

    /**
     * removePastDate
     *  Returns an array with the sorted tasklist with tasks that are due today or in the furture
     * @param taskList
     * @return modified tasklist
     */
    public List<Task> removePastDate(List<Task> taskList){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar today = Calendar.getInstance();
        Calendar taskDate = Calendar.getInstance();
        List<Task> modified = new ArrayList<Task>();
        if(taskList != null){
            for(int i = 0; i < taskList.size(); i++){
                try{
                    taskDate.setTime(sdf.parse(taskList.get(i).getDate()));
                }catch(ParseException e){
                    e.printStackTrace();
                }
                if(taskDate.get(Calendar.DATE) == (today.get(Calendar.DATE)) || taskDate.after(today)){
                    modified.add(taskList.get(i));
                }
            }
        }
        return modified;
    }//removePastDate
}//HomePageFragment2
