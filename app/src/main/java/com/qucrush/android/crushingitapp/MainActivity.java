package com.qucrush.android.crushingitapp;

import android.app.AlarmManager;

import android.support.v4.app.FragmentManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Main Activity
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,communicate {

    //Instance Variables
    public static DBHandler db;
    public static TaskManager tm = new TaskManager();
    public static BadgeManager bm = new BadgeManager();
    private Task editTask;
    private long time;
    private boolean reportReady = false;
    AlarmReceiver alarmReceiver = new AlarmReceiver();
    String timeStored = null;
    int[] schedule = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set font size (depricated, as phone's integrated size is used instead
        try {
            if ("Large".equalsIgnoreCase(getIntent().getStringExtra("Theme"))) {
              setTheme(R.style.AppTheme_Large);
            } else if ("Small".equalsIgnoreCase(getIntent().getStringExtra("Theme"))) {
                    setTheme(R.style.AppTheme_Small);
            }
            timeStored = getIntent().getStringExtra("Storing time");
            schedule = getIntent().getIntArrayExtra("Schedule");

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        db = new DBHandler(this);
        //tm = new TaskManager();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //drawer initialization
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //navigationView initialization
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Check to see if the main activity was started with any intent instructions from
        // the daily report activity
        String intentFragment = "";
        try {
            intentFragment = getIntent().getExtras().getString("FrgToLoad");
        }catch (NullPointerException e){
            System.out.println("Not yet");
        }
        //If there is an intent message, if the intent is a dailyReport, start daily report.
        //  Otherwise, start the task menu
        if(intentFragment != null){
            if(intentFragment.equals("dailyReport")){
                startDailyReport();
            }
            if(intentFragment.equals("taskMenu")){
                startTaskMenu();
            }
        }//if

        //If time stored isn't null store the time
        if(timeStored != null){
            storeTime(timeStored);
        }
        //If schedule isn't null, schedule the report using the settings condition
        if(schedule != null){
            scheduleReport(schedule[0],schedule[1],"settings");
        }
        try {
            reportReady = getIntent().getExtras().getBoolean("prepReport");
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        if(intentFragment == ""){
            startHomePage();
        }
    }//onCreate

    //initialize fragments for all active pages
    public void startHomePage(){
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame,
                new ViewPagerFragment()).commit();
    }//startHomePage

    public void storeTask(){
        FragmentManager fm = getSupportFragmentManager();
        //TaskFormTest frag = (TaskFormTest) fm.findFragmentById(R.id.);
        //frag.saveTask(tm);
        fm.beginTransaction().replace(R.id.content_frame,
                new TaskFragment()).commit();
    }//storeTask

    public void startCreationForm() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,
                new TaskFormTest()).commit();
    }//startCreationForm

    public void startDailyReport(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,
                new DailyReport()).commit();
    }//startDailyReport

    public void startTaskMenu(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,
                new TaskFragment()).commit();
    }//startTaskMenu

    public void startEditForm(Task task){
        editTask = task;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,
                new TaskEditFragment()).commit();
    }//startEditForm


    public Task getTaskToEdit(){
        return editTask;
    }

    //determines what happens when the back button is pressed
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }//onBackPressed

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }//onCreateOptionsMenu

    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new SettingsFragment())
                    .commit();
            System.out.println("SETTINGS BUTTON PRESSED");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getSupportFragmentManager();
        int id = item.getItemId();

        if (id == R.id.nav_task_menu) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new TaskFragment())
                    .commit();
        } else if (id == R.id.nav_reward_menu) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new RewardFragment())
                    .commit();
        } else if (id == R.id.nav_daily_report) {
            if(reportReady == false) {
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame
                                , new DailyFeedbackTest())
                        .commit();
            }else{
                reportReady = true;
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame
                                , new DailyReport())
                        .commit();
            }
        } else if (id == R.id.nav_home_menu){
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new ViewPagerFragment())
                    .commit();
        } else if (id == R.id.nav_credit_menu){
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main
                    , new CreditFragment())
                    //.commit()
                    //removed commit as the page was giving errors too late into the cycle
            ;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }//onNavigationItemSelected

    /**
     * scheduleReport
     *  Schedules the daily feedback report
     * @param hour
     * @param min
     * @param methodCalledLoc If the param is settings, then schedule the report for the next day,
     *                        otherwise, normally schedule the report
     */
    public void scheduleReport(int hour, int min, String methodCalledLoc){
        if(methodCalledLoc == "settings"){
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, min);

            if(c.before(Calendar.getInstance())){
                c.add(Calendar.DATE,1);
            }

            Intent intentAlarm = new Intent(this,AlarmReceiver.class);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            //set the alarm for particular time
            alarmManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(), PendingIntent.getBroadcast(this,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
            Toast.makeText(this, "Feedback report Scheduled", Toast.LENGTH_LONG).show();
        }else{
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, min);
            c.add(Calendar.DATE,1);
            Intent intentAlarm = new Intent(this,AlarmReceiver.class);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            //set the alarm for particular time
            alarmManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(), PendingIntent.getBroadcast(this,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
            Toast.makeText(this, "Report Scheduled for next day", Toast.LENGTH_LONG).show();
        }
    }//scheduleReport

    /**
     * Store the time to the database
     * @param time
     */
    public void storeTime(String time){
        if(tm.retrieveTime() != null){
            tm.updateTime(time);
        }else{
            tm.addTime(time);
        }
    }//storeTime

    /**
     * Class to start the DailyFeedbackActivity when the alarm is triggered
     */
    public static class AlarmReceiver extends BroadcastReceiver{
        public void onReceive(Context context, Intent intent){
            Toast.makeText(context, "Alarm Triggered", Toast.LENGTH_LONG).show();
            Intent intent1 = new Intent(context,DailyFeedbackActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }//onReceive
    }//AlarmReceiver
}//MainActivity
