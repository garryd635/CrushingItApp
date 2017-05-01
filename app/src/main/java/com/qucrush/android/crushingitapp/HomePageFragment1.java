package com.qucrush.android.crushingitapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Home Page Fragment
 *  The screen that contains home_1.xml as part of the screens for the homepage
 */

public class HomePageFragment1 extends Fragment{
    //Instance Variables
    View myView;
    String type = "one";
    ProgressBar dayStatus;
    TextView percentText;
    List<Task> allTasks,todayTasks;
    LayoutInflater inflater;
    ViewGroup container;
    int totalPer,completePer;
    @Nullable
    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        myView = inflater.inflate(R.layout.home_1, container, false);

        allTasks = MainActivity.tm.retrieveTasks();
        todayTasks = new ArrayList<Task>();
        todayTasks = getTodayTasksList(allTasks);
        dayStatus = (ProgressBar) myView.findViewById(R.id.homeProgressBar);
        percentText = (TextView) myView.findViewById(R.id.percentText);
        totalPer = todayTasks.size();
        completePer = 0;

        //If there's tasks for today, calculate the total completed tasks
        if(todayTasks.size() > 0){
            for(int i =0; i < todayTasks.size();i++){
                if(todayTasks.get(i).getCompletion().equals("yes")){
                    completePer++;
                }
            }
        }

        if(completePer != 0){
            dayStatus.setProgress(((completePer*10/totalPer*10)));
            percentText.setText((completePer*10/totalPer*10) + "%");
        }else if(todayTasks != null){
            dayStatus.setProgress(0);
            percentText.setText("0 %");
        }else{
            dayStatus.setProgress(0);
            percentText.setText("No Tasks Today");
        }

        return myView;
    }

    /**
     * Gets all the tasks that are due on the current day
     *
     * @return tempLst
     */
    public List<Task> getTodayTasksList(List<Task> taskList){
        List<Task> tempLst = new ArrayList<Task>();
        Calendar todayCal = Calendar.getInstance();
        Calendar taskCal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(taskList != null) {
            for (int i = 0; i < taskList.size(); i++) {
                try {
                    todayCal.setTime(sdf.parse(taskList.get(i).getDate()));
                } catch (ParseException e) {
                    System.out.println("***ERROR***");
                }
                if ((todayCal.get(Calendar.YEAR) == taskCal.get(Calendar.YEAR)) &&
                        (todayCal.get(Calendar.MONTH) == taskCal.get(Calendar.MONTH)) &&
                        (todayCal.get(Calendar.DAY_OF_MONTH) == taskCal.get(Calendar.DAY_OF_MONTH))) {
                    tempLst.add(taskList.get(i));
                }
            }
        }
        return tempLst;
    }//setTodayTaskList()
}//HomePageFragment1
