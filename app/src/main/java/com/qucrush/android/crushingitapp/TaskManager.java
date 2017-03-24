package com.qucrush.android.crushingitapp;

import android.widget.TextView;

import java.util.ArrayList;
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
        if(MainActivity.db.getTableDrtime() != ""){
            reportTime = MainActivity.db.getTableDrtime();
        }
        else
            reportTime = null;
        return reportTime;
    }

    public void addTime(String time){
        if(MainActivity.db.getTableDrtime() == ""){
            MainActivity.db.addTime(time);
        }
    }

    public void updateTime(String time){
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
    }

}