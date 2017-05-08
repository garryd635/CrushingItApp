package com.qucrush.android.crushingitapp;

/**
 * Interface for Fragments to communicate with the Main Activity
 */

public interface communicate {
    public void storeTask();
    public void startCreationForm();
    public void startTaskMenu();
    public void startEditForm(Task task);
    public void startDailyReport();
    public Task getTaskToEdit();
    public void scheduleReport(int hour, int min, String callLoc);
    public void storeTime(String time);
}
