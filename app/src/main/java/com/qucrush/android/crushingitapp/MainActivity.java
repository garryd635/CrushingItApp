package com.qucrush.android.crushingitapp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.View;
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
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,communicate {

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


        try {
            if ("Large".equalsIgnoreCase(getIntent().getStringExtra("Theme"))) {
              setTheme(R.style.Theme_Large);
            }   else if ("Small".equalsIgnoreCase(getIntent().getStringExtra("Theme"))) {
                    setTheme(R.style.Theme_Small);
            }
            timeStored = getIntent().getStringExtra("Storing time");
            schedule = getIntent().getIntArrayExtra("Schedule");

        } catch (Exception e) {

    }


        super.onCreate(savedInstanceState);
        db = new DBHandler(this);
        //tm = new TaskManager();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        String intentFragment = "";
        //intentFragment = getIntent().getExtras().getString("FrgToLoad");
        try {
            intentFragment = getIntent().getExtras().getString("FrgToLoad");
        }catch (NullPointerException e){
            System.out.println("Not yet");
        }
        System.out.println("IntendedFrag is :" + intentFragment );
        if(intentFragment != null){
            if(intentFragment.equals("dailyReport")){
                startDailyReport();
            }
            if(intentFragment.equals("taskMenu")){
                startTaskMenu();
            }
        }

        if(timeStored != null){
            storeTime(timeStored);
        }
        if(schedule != null){
            scheduleReport(schedule[0],schedule[1]);
        }
        try {
            reportReady = getIntent().getExtras().getBoolean("prepReport");
        }catch (NullPointerException e){

        }
        //startHomePage();
    }


    public void startHomePage(){
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame,
                new HomePageFragment()).commit();
    }


    public void storeTask(){
        FragmentManager fm = getFragmentManager();
        //TaskFormTest frag = (TaskFormTest) fm.findFragmentById(R.id.);
        //frag.saveTask(tm);
        fm.beginTransaction().replace(R.id.content_frame,
                new TaskFragment()).commit();
    }

    public void startCreationForm() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,
                new TaskFormTest()).commit();
    }

    public void startDailyReport(){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,
                new DailyReport()).commit();
    }
    public void startTaskMenu(){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,
                new TaskFragment()).commit();
    }

    public void startEditForm(Task task){
        editTask = task;
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,
                new TaskEditFragment()).commit();
    }
    public Task getTaskToEdit(){
        return editTask;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new SettingsFragment())
                    .commit();
//            Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
//            startActivity(intent);
            System.out.println("SETTINGS BUTTON PRESSED");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getFragmentManager();
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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void scheduleReport(int hour, int min){
        //this.time = time;
        //long calc = (long) 1.0/60.0;
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, min);


//        this.time = new GregorianCalendar().getTimeInMillis()+5*1000;
//        System.out.println(time);
//        Date d = new Date(time);
//        System.out.println(d);
        if(c.before(Calendar.getInstance())){
            c.add(Calendar.DATE,1);
        }

        Intent intentAlarm = new Intent(this,AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //set the alarm for particular time
        alarmManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(), PendingIntent.getBroadcast(this,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        Toast.makeText(this, "Feedback report Scheduled", Toast.LENGTH_LONG).show();
    }

    public void storeTime(String time){
        System.out.println("MainActivity stored time called. Results:" + tm.retrieveTime());
        if(tm.retrieveTime() != null){
            tm.updateTime(time);
        }else{
            tm.addTime(time);
        }
    }

    public static class AlarmReceiver extends BroadcastReceiver{
        public void onReceive(Context context, Intent intent){
            Toast.makeText(context, "Alarm Triggered", Toast.LENGTH_LONG).show();
            Intent intent1 = new Intent(context,DailyFeedbackActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }
}
