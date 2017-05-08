package com.qucrush.android.crushingitapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * DBHandler
 *  Manages the database of the application
 */

public class DBHandler extends SQLiteOpenHelper{
    // Database Version
    private static final int DATABASE_VERSION = 23;
    // Database Name
    private static final String DATABASE_NAME = "CrushDB";
    // Table Names
    private static final String TABLE_TASKS = "Tasks";
    private static final String TABLE_DRTIME = "DRTime";
    private static final String TABLE_BADGES = "Badges";
    // Task Column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESC = "description";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_CATE = "category";
    private static final String KEY_COMPLETE = "completion";
    private static final String KEY_RECUR = "recurring";
    private static final String KEY_COMPLETE_DATE = "completionDate";
    private static final String KEY_DAYS_INCOMPLETE = "daysOfIncomplete";
    //Report Time additional Column names
    private static final String KEY_DRTIME = "time";
    //Badges additional Column Names
    private static final String KEY_IS_EARNED = "isEarned";
    private static final String KEY_DATE_EARNED = "dateEarned";
    private static final String KEY_IMG_SRC = "imgsrc";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * On database creation, create new tables for the tasks, Daily Feedback Report time, and Badges
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_DESC + " TEXT," + KEY_DATE + " TEXT," + KEY_TIME + " TEXT," + KEY_CATE + " TEXT," + KEY_COMPLETE + " TEXT,"
                + KEY_RECUR + " TEXT," + KEY_COMPLETE_DATE + " TEXT," + KEY_DAYS_INCOMPLETE + " TEXT" + ")";
        String CREATE_DRTIME_TABLE = "CREATE TABLE " + TABLE_DRTIME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DRTIME + " TEXT)";
        String CREATE_BADGE_TABLE = "CREATE TABLE " + TABLE_BADGES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_DESC + " TEXT," + KEY_IS_EARNED + " TEXT," + KEY_DATE_EARNED + " TEXT,"
                + KEY_IMG_SRC + " TEXT)";
        db.execSQL(CREATE_TASK_TABLE);
        db.execSQL(CREATE_DRTIME_TABLE);
        db.execSQL(CREATE_BADGE_TABLE);
        this.addBadges(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRTIME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BADGES);
        // Creating tables again
        onCreate(db);
    }

    /**
     * addTask
     *  Store task information to the database
     * @param task task object to store information
     */
    public void addTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task.getName());
        values.put(KEY_DESC, task.getDesc());
        values.put(KEY_DATE, task.getDate().toString());
        values.put(KEY_TIME, task.getTime().toString());
        values.put(KEY_CATE, task.getCategory().toString());
        values.put(KEY_COMPLETE, task.getCompletion());
        values.put(KEY_RECUR, task.getRecurring());
        values.put(KEY_COMPLETE_DATE, "none");
        values.put(KEY_DAYS_INCOMPLETE, "0");

        db.insert(TABLE_TASKS,null,values);
    }//addTask()

    /**
     * addTime
     *  Store the time for the daily feedback report
     * @param time
     */
    public void addTime(String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DRTIME, time);
        db.insert(TABLE_DRTIME,null,values);
    }//addTime()

    /**
     * addBadges
     *  Manually store badge information to the database
     * @param db
     */
    public void addBadges(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,"Crushing It! 101");
        values.put(KEY_DESC, "You've entered and completed your first daily task.  I think you are ready" +
                " to take on managing your work and life tasks");
        values.put(KEY_IS_EARNED, "no");
        values.put(KEY_DATE_EARNED,"null");
        values.put(KEY_IMG_SRC, "badge1");
        db.insert(TABLE_BADGES,null,values);
        values = new ContentValues();
        values.put(KEY_NAME,"To Do List 101");
        values.put(KEY_DESC, "You've at least 5 tasks in one day!  How do we know? Well the daily report" +
                " told us in advance!");
        values.put(KEY_IS_EARNED, "no");
        values.put(KEY_DATE_EARNED,"null");
        values.put(KEY_IMG_SRC, "badge2");
        db.insert(TABLE_BADGES,null,values);
        values = new ContentValues();
        values.put(KEY_NAME,"Employee by day, parent by night");
        values.put(KEY_DESC, "You managed to complete a work and personal task in one day!" +
                " You're living the double life well.");
        values.put(KEY_IS_EARNED, "no");
        values.put(KEY_DATE_EARNED,"null");
        values.put(KEY_IMG_SRC, "badge3");
        db.insert(TABLE_BADGES,null,values);
    }//addBadges

    /**
     * updateBadge
     *  Update the earn status and date for the specific badge
     * @param badge
     */
    public void updateBadge(Badge badge){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE BADGES set " + KEY_IS_EARNED + " = '" + badge.getIsEarned() +
                "', " + KEY_DATE_EARNED + " = '" + badge.getEarnedDate() + "'  WHERE " + KEY_ID + " = " + badge.getID());
    }//updateBadge

    /**
     * deleteTask
     *  Delete a task based on Task's id
     * @param id Task's id
     */
    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE from TASKS WHERE ID == " + id);
    }//deleteTask

    /**
     * updateTask
     *  Update task information in DB according to Task's id
     * @param task
     */
    public void updateTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE TASKS SET " + KEY_NAME + " = '" + task.getName() +
                "', " + KEY_DESC + " = '" + task.getDesc() + "', " + KEY_DATE + " = '" +task.getDate() +
                "', " + KEY_TIME + " = '" + task.getTime() + "', " + KEY_CATE + " = '" + task.getCategory()
                + "', " + KEY_COMPLETE + " = '" + task.getCompletion() + "' ," + KEY_RECUR + " = '" + task.getRecurring()
                +"' WHERE " + KEY_ID + " = " + task.getId());
        //db.close();
    }//updateTask

    /**
     * Get completed date from task according to id directly from DB
     * @param id
     * @return
     */
    public String getCompleteDateTask(int id){
        String date = "";
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery =("SELECT * FROM " + TABLE_TASKS + " WHERE " + KEY_ID + " = " + id);
        Cursor cur = db.rawQuery(selectQuery,null);
        if(cur.moveToFirst()){
            do{
                date = cur.getString(8);
            } while (cur.moveToNext());
        }
        cur.close();
        return date;
    }//getCompleteDateTask

    /**
     * Get the number of days the task was incompleted
     * @param id
     * @return
     */
    public String getDaysIncomplete(int id){
        String daysNum = "";
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery =("SELECT * FROM " + TABLE_TASKS + " WHERE " + KEY_ID + " = " + id);
        Cursor cur = db.rawQuery(selectQuery,null);
        if(cur.moveToFirst()){
            do{
                daysNum = cur.getString(9);
            } while (cur.moveToNext());
        }
        cur.close();
        return daysNum;
    }//getDaysIncomplete

    /**
     * updateCompleteDateTask
     *  update task's complete date
     * @param id
     * @param date
     */
    public void updateCompleteDateTask(int id, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE TASKS SET " + KEY_COMPLETE_DATE + " = '" + date +
                    "' WHERE " + KEY_ID + " = " + id);
    }

    /**
     * updateDaysIncomplete
     *  Update task's number of days a task has been incomplete
     * @param id
     * @param date
     */
    public void updateDaysIncomplete(int id, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE TASKS SET " + KEY_DAYS_INCOMPLETE + " = '" + date +
                    "' WHERE " + KEY_ID + " = " + id);
        //db.close();
    }

    /**
     * updateTime
     *  Update the Daily Report Time
     * @param time
     */
    public void updateTime(String time){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE DRTime SET " + KEY_DRTIME + " = '" + time
                    + "' WHERE " + KEY_ID + " = " + 1);
    }//updateTime

    /**
     * getAllTasks
     *
     *  Get all the tasks stored in the database
     * @return
     */
    public List<Task> getAllTasks(){
        List<Task> taskList = new ArrayList<Task>();

        String selectQuery = "SELECT * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery(selectQuery,null);
        if(cur.moveToFirst()){
            do{
                Task task = new Task(cur.getInt(0),
                        cur.getString(1),cur.getString(2),cur.getString(3),
                        cur.getString(4),cur.getString(5),cur.getString(6),cur.getString(7));
                taskList.add(task);
            } while (cur.moveToNext());
        }
        cur.close();
        return taskList;
    }

    /**
     * getBadgeList
     *  Get all the badges stored in database
     * @return
     */
    public List<Badge> getBadgeList(){
        List<Badge> badgeLst = new ArrayList<Badge>();
        String selectQuery = "SELECT * FROM " + TABLE_BADGES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery(selectQuery,null);
        if(cur.moveToFirst()){
            do{
                Badge badge = new Badge(cur.getInt(0),cur.getString(1),cur.getString(2),cur.getString(3),
                        cur.getString(4),cur.getString(5));
                badgeLst.add(badge);
            } while (cur.moveToNext());
        }
        cur.close();
        //db.close();
        return badgeLst;
    }

    /**
     * getTableDrtime
     *  Get the time for the database
     * @return
     */
    public String getTableDrtime(){
        String timeToSend = "";
        String selectQuery = "SELECT * FROM " + TABLE_DRTIME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery(selectQuery,null);

        if(cur.moveToFirst()){
            do{
                timeToSend = cur.getString(1);
                System.out.println("Time ID + " + cur.getInt(0));
            } while (cur.moveToNext());
        }
        cur.close();
        return timeToSend;
    }

    /**
     * Get the number of tasks stored in database
     * @return
     */
    public int getTaskCount(){

        String countQuery = "SELECT * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery(countQuery,null);
        int num = cur.getCount();
        cur.close();
        System.out.println(num);
        return num;
    }
}//DBHandler