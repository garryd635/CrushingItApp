package com.qucrush.android.crushingitapp;

import android.app.AlertDialog;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * DailyReport
 *  Displays the Daily Feedback Report
 */

public class DailyReport extends Fragment{
    //Instance Variables
    View myView;
    List<Task> taskList = new ArrayList<Task>();
    List<Task> cTaskList = new ArrayList<Task>();
    List<Task> uTaskList = new ArrayList<Task>();
    List<Badge> badgeList = new ArrayList<Badge>();

    private Animation button_shrink;
    ListView feedbackList;
    CompletionAdapter lDataAdapter;
    Button continueButton;
    communicate cm;
    ImageView badgeImg;
    TextView rewardMessage;
    String timeString;
    String[] timeSplited;
    private int count = 1;
    int splitHour, splitMin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTheme(R.style.AppTheme_Small);
        myView = inflater.inflate(R.layout.daily_report, container, false);
        feedbackList = (ListView) myView.findViewById(R.id.viewTasks);
        continueButton = (Button) myView.findViewById(R.id.contButton);
        badgeImg = (ImageView) myView.findViewById(R.id.badgeEarned);
        rewardMessage = (TextView) myView.findViewById(R.id.badgeMessage);
        taskList = MainActivity.tm.retrieveTasks();
        timeString = MainActivity.db.getTableDrtime();
        timeSplited = timeString.toString().split(" ");
        cm = (communicate) getActivity();

        button_shrink = AnimationUtils.loadAnimation(getActivity(),R.anim.button_shrink);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //Check through list of tasks for task due during current day
        if (taskList != null){
            for(int i = 0; i < taskList.size(); i++){
                Calendar toDate = Calendar.getInstance();
                Calendar nowDate = Calendar.getInstance();
                //set the time of the toDate to the due date stored in the task
                try{
                    toDate.setTime(sdf.parse(taskList.get(i).getDate()));
                }catch (ParseException e){
                    System.out.println("***ERROR***");
                }

                //For all tasks that are due for the current day, if the task is completed store it in the
                //  Completed task list.  Otherwise, store the task in the uncompleted task list
                if((toDate.get(Calendar.YEAR) == nowDate.get(Calendar.YEAR)) &&
                        (toDate.get(Calendar.MONTH) == nowDate.get(Calendar.MONTH)) &&
                        (toDate.get(Calendar.DAY_OF_MONTH) == nowDate.get(Calendar.DAY_OF_MONTH))){
                    if(taskList.get(i).getCompletion().equals("yes")){
                        cTaskList.add(taskList.get(i));
                    }else{
                        uTaskList.add(taskList.get(i));
                    }
                }//if
            }//for

            //Get a list of earned badges according to the Badge Manager
            badgeList = MainActivity.bm.taskBadgeCheck(cTaskList.size(),cTaskList);
            //Set default reward message
            rewardMessage.setText("No Badges have been earned today.");
            //If badges was earned, set the reward message according to the number of badges earned
            if(badgeList.size() > 0){
                if (badgeList.size() == 1) {
                    rewardMessage.setText("Congratulations! You have earned 1 badge! \n" + badgeList.get(count-1).getName());
                } else {
                    rewardMessage.setText("Congratulations! You have earned " + badgeList.size() +
                            " badges! \n" + badgeList.get(count-1).getName());
                }
                //Retrieve the badge resource from the badge object
                int resource = getActivity().getResources().getIdentifier(badgeList.get(0).getImgsrc(),"drawable",
                        getActivity().getPackageName());
                badgeImg.setImageResource(resource);
                //Set Listener to rotate badges earned when tapping on the badge
                badgeImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(badgeList.size() != 0){
                            if(count < badgeList.size()){
                                count++;
                            }else{
                                count = 1;
                            }
                            if (badgeList.size() == 1) {
                                rewardMessage.setText("Congratulations! You have earned 1 badge! \n" + badgeList.get(count-1).getName());
                            } else {
                                rewardMessage.setText("Congratulations! You have earned " + badgeList.size() +
                                        " badges! \n" + badgeList.get(count-1).getName());
                            }

                            int resource = getActivity().getResources().getIdentifier(badgeList.get(count-1).getImgsrc(),"drawable",
                                    getActivity().getPackageName());
                            badgeImg.setImageResource(resource);
                        }
                    }
                });
            }
            //Combine the completed and uncompleted task list together
            cTaskList.addAll(uTaskList);
        }
        //Set the adapter with the combined task list and set adapter to the listview
        lDataAdapter = new CompletionAdapter(getActivity(),
                R.layout.daily_report, cTaskList);
        feedbackList.setAdapter(lDataAdapter);

        //When pressed, have the daily feedback start the task menu and update tasks
        // according to their recurrance
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueButton.startAnimation(button_shrink);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after .2s = 200ms
                        MainActivity.tm.updateCompletedTasks(cTaskList);
                        cm.startTaskMenu();
                    }
                }, 500);
            }
        });
        //play sound effect
        continueButton.playSoundEffect(android.view.SoundEffectConstants.CLICK);

        //Schedule the next daily report
        String[] hourMinSplit = timeSplited[0].toString().split(":");
        splitHour = Integer.parseInt(hourMinSplit[0].toString());
        splitMin = Integer.parseInt(hourMinSplit[1].toString());
        if(timeSplited[1] == "PM" ){
            splitHour += 12;
        }
        cm.scheduleReport(splitHour,splitMin,"dailyReport");
        return myView;
    }//onCreateView()
}//DailyReport
