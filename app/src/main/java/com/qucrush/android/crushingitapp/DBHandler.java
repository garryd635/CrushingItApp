package com.qucrush.android.crushingitapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Garry on 11/25/2016.
 */

public class DBHandler extends SQLiteOpenHelper{
    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "CrushDB";
    // Contacts table name
    private static final String TABLE_TASKS = "Tasks";
    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESC = "description";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_CATE = "category";
    private static final String KEY_COMPLETE = "completion";
    private static final String KEY_RECUR = "recurring";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
        + KEY_DESC + " TEXT," + KEY_DATE + " TEXT," + KEY_TIME + " TEXT," + KEY_CATE + " TEXT," + KEY_COMPLETE + " TEXT,"
                + KEY_RECUR + " TEXT" + ")";
        db.execSQL(CREATE_TASK_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
// Creating tables again
        onCreate(db);
    }

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

        db.insert(TABLE_TASKS,null,values);
        db.close();
    }

    public void deleteTask(int count) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE from TASKS WHERE ID == " + count);
        db.close();
    }

    public void updateTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE TASKS SET " + KEY_NAME + " = '" + task.getName() +
        "', " + KEY_DESC + " = '" + task.getDesc() + "', " + KEY_DATE + " = '" +task.getDate() +
                "', " + KEY_TIME + " = '" + task.getTime() + "', " + KEY_CATE + " = '" + task.getCategory()
                    + "', " + KEY_COMPLETE + " = '" + task.getCompletion() + "' " + KEY_RECUR + " = '" + task.getRecurring()
                        +"' WHERE " + KEY_ID + " = " + task.getId());
        db.close();
    }
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
        db.close();
        return taskList;
    }

    public int getTaskCount(){

        String countQuery = "SELECT * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery(countQuery,null);
        int num = cur.getCount();
        cur.close();
        db.close();
        System.out.println(num);
        return num;
    }
}

