package com.qucrush.android.crushingitapp;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Garry on 2/13/2017.
 */

public class TaskManager {

    private List<Task> taskList;
    private int counter;
    private TextView dateText,timeText, nameText, cateText;
    private Task tempTask;

    public TaskManager(){
        taskList = new ArrayList<Task>();
        counter = 0;
    }

    public void createTask(String name, String desc, String date, String time, String category){
        tempTask = new Task(0,name,desc,date,time,category);
        MainActivity.db.addTask(tempTask);
        retrieveTasks();
    }

    public void deleteTask(int count){
        taskList = retrieveTasks();
        MainActivity.db.deleteTask(count);
    }

    public List<Task> retrieveTasks(){
        //List<Task> taskList = new ArrayList<Task>();
        if(MainActivity.db.getTaskCount() != 0){
            taskList = MainActivity.db.getAllTasks();
            for(int i = 0; i < taskList.size(); i++){
                System.out.println("Retrieved Task: ID:" + taskList.get(i).getId() + " Name: " + taskList.get(i).getName());
            }
        }
        else{
            taskList = null;
        }
        return taskList;
    }

    public int getCount() {
        return counter;
    }

    public void updateTask(String name, String desc, String date, String time, String category, int id){
        tempTask = new Task(id,name,desc,date,time,category);
        MainActivity.db.updateTask(tempTask);
        retrieveTasks();
    }

    public void changeCard(){
        if(taskList != null){
            if(counter <= MainActivity.db.getTaskCount()){

            }
        }
    }

}
