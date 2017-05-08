package com.qucrush.android.crushingitapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * HomePageFragment3
 *  Contains the contents of home3.xml
 */

public class HomePageFragment3 extends Fragment{
    //Instance variables
    View myView;
    LayoutInflater inflater;
    ViewGroup container;
    ListView completeLst;
    List<Task> complete;
    CompletionAdapter dataAdapter;
    @Nullable
    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        myView = inflater.inflate(R.layout.home_3, container, false);
        completeLst = (ListView) myView.findViewById(R.id.homeCompleteList);
        complete = getTodayCompletedTasksList(MainActivity.tm.retrieveTasks());

        //checks if there is at least one completed task
        if(complete != null){
            dataAdapter = new CompletionAdapter(getActivity(),
                    R.layout.home_3, complete);
            completeLst.setAdapter(dataAdapter);
        }

        return myView;
    }
    /**
     * Gets all the tasks that are due on the current day
     *
     * @return tempLst
     */
    public List<Task> getTodayCompletedTasksList(List<Task> taskList){
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
                    if(taskList.get(i).getCompletion().equals("yes")){
                        tempLst.add(taskList.get(i));
                    }
                }
            }
        }
        return tempLst;
    }//getTodayTaskList()
}//HomePageFragment3
