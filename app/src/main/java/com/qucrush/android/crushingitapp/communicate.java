package com.qucrush.android.crushingitapp;

/**
 * Created by Garry on 2/14/2017.
 */

public interface communicate {
    public void storeTask();
    public void startCreationForm();
    public void startTaskMenu();
    public void startEditForm(Task task);
    public void startDailyReport();
    public Task getTaskToEdit();

}
