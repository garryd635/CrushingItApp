package com.qucrush.android.crushingitapp;

import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.jar.Manifest;

/**
 * Created by Garry on 2/13/2017.
 */

public class TaskManager {

    private List<Task> taskList;
    private int counter;
    private TextView dateText,timeText, nameText, cateText;
    private Task tempTask;
    private String reportTime;

    public TaskManager(){
        taskList = new ArrayList<Task>();
        counter = 0;
    }

    public void createTask(String name, String desc, String date, String time, String category){
        tempTask = new Task(0,name,desc,date,time,category,"no","no");
        MainActivity.db.addTask(tempTask);
        retrieveTasks();
    }

    public void updateTask(String name, String desc, String date, String time, String category, int id){
        tempTask = new Task(id,name,desc,date,time,category,"no","no");
        System.out.println("FROM TASKMANAGER:" + tempTask.getCompletion());
        MainActivity.db.updateTask(tempTask);
        retrieveTasks();
    }

    public void updateTaskInAdapter(String name, String desc, String date, String time, String category,String complete, int id){
        tempTask = new Task(id,name,desc,date,time,category,complete,"no");
        System.out.println("FROM TASKMANAGER:" + tempTask.getCompletion());
        MainActivity.db.updateTask(tempTask);
        retrieveTasks();
    }
    public void deleteTask(int count){
        taskList = retrieveTasks();
        MainActivity.db.deleteTask(count);
    }

    public void deleteCompletedTask(List<Task> taskList){
        for(int i=0; i < taskList.size(); i++){
            System.out.println(taskList.get(i).getRecurring());
            if(taskList.get(i).getRecurring().equals("no")){
                deleteTask(taskList.get(i).getId());
            }

        }
    }
    public List<Task> retrieveTasks(){
        //List<Task> taskList = new ArrayList<Task>();
        if(MainActivity.db.getTaskCount() != 0){
            taskList = MainActivity.db.getAllTasks();
            for(int i = 0; i < taskList.size(); i++){
                System.out.println("Retrieved Task: ID:" + taskList.get(i).getId() + " Name: " + taskList.get(i).getName() + " "
                        + taskList.get(i).getCompletion());
            }
        }
        else{
            taskList = null;
        }
        return taskList;
    }

    public String retrieveTime(){
        System.out.println("***********" + MainActivity.db.getTableDrtime());
        if(MainActivity.db.getTableDrtime() != ""){
            reportTime = MainActivity.db.getTableDrtime();
        }
        else
            reportTime = null;
        return reportTime;
    }

    public void addTime(String time){
        System.out.println("CALLED addTime Results:" + MainActivity.db.getTableDrtime());
        if(MainActivity.db.getTableDrtime() == ""){
            MainActivity.db.addTime(time);
        }
    }

    public void updateTime(String time){
        System.out.println("CALLED updateTime Results: updating " + time);
        MainActivity.db.updateTime(time);
    }

    public int getCount() {
        return counter;
    }

    public void changeCard(){
        if(taskList != null){
            if(counter <= MainActivity.db.getTaskCount()){

            }
        }
    }//changeCard

    /*
    Check to see if task from report should be deleted or modified
     */
    public void managedCompletedTask(List<Task> completedList){
        for(int i = 0; i < completedList.size(); i++){
            if(completedList.get(i).getRecurring() == "none"){
                MainActivity.db.deleteTask(completedList.get(i).getId());
            }else{
                Task updatedTask = completedList.get(i);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String[] splitDate = updatedTask.getDate().split("-");
                String newDateString = splitDate[0] + "-" + (Integer.parseInt(splitDate[1])-1) + "-" + splitDate[2];
                Date newDate = null;
                try{
                    newDate = sdf.parse(newDateString.toString());
                }catch(ParseException e){
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(newDate);
                if(completedList.get(i).getRecurring() == "Daily"){

                }
            }
        }//for
    }

}