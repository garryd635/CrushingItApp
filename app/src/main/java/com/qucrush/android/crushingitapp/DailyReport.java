package com.qucrush.android.crushingitapp;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by Garry on 2/8/2017.
 */

public class DailyReport extends Fragment{
    View myView;
    List<Task> taskList = new ArrayList<Task>();
    List<Task> cTaskList = new ArrayList<Task>();
    List<Task> uTaskList = new ArrayList<Task>();
    List<Badge> badgeList = new ArrayList<Badge>();

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
        getActivity().setTheme(R.style.Theme_Small);
        myView = inflater.inflate(R.layout.daily_report, container, false);
        //completedList = (ListView) myView.findViewById(R.id.viewComplete);
        //uncompletedList = (ListView) myView.findViewById(R.id.viewIncomplete);
        feedbackList = (ListView) myView.findViewById(R.id.viewTasks);
        continueButton = (Button) myView.findViewById(R.id.contButton);
        badgeImg = (ImageView) myView.findViewById(R.id.badgeEarned);
        rewardMessage = (TextView) myView.findViewById(R.id.badgeMessage);
        taskList = MainActivity.tm.retrieveTasks();
        timeString = MainActivity.db.getTableDrtime();
        timeSplited = timeString.toString().split(" ");
        cm = (communicate) getActivity();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //Check through list of tasks for task due during current day
        if (taskList != null){
            for(int i = 0; i < taskList.size(); i++){
                Calendar toDate = Calendar.getInstance();
                Calendar nowDate = Calendar.getInstance();

                try{
                    toDate.setTime(sdf.parse(taskList.get(i).getDate()));
                }catch (ParseException e){
                    System.out.println("***ERROR***");
                }


                System.out.println("Now Date: " + nowDate.getTime().toString());

                //toDate.set(convertedDate.getYear(), convertedDate.getMonth(),convertedDate.getDay());
                System.out.println("To Date: " + toDate.getTime().toString());
                if((toDate.get(Calendar.YEAR) == nowDate.get(Calendar.YEAR)) &&
                        (toDate.get(Calendar.MONTH) == nowDate.get(Calendar.MONTH)) &&
                        (toDate.get(Calendar.DAY_OF_MONTH) == nowDate.get(Calendar.DAY_OF_MONTH))){
                    System.out.println("Date is equal");
                    if(taskList.get(i).getCompletion().equals("yes")){
                        cTaskList.add(taskList.get(i));
                    }else{
                        uTaskList.add(taskList.get(i));
                    }

                    //cTaskList.remove(cTaskList.size());

                }

            }
            //Do Task completion check HERE
            badgeList = MainActivity.bm.taskBadgeCheck(cTaskList.size(),cTaskList);
            rewardMessage.setText("No Badges have been earned today.");
            if(badgeList.size() > 0){
                if (badgeList.size() == 1) {
                    rewardMessage.setText("Congratulations! You have earned 1 badge! \n" + badgeList.get(count-1).getName());
                } else {
                    rewardMessage.setText("Congratulations! You have earned " + badgeList.size() +
                            " badges! \n" + badgeList.get(count-1).getName());
                }

                int resource = getActivity().getResources().getIdentifier(badgeList.get(0).getImgsrc(),"drawable",
                        getActivity().getPackageName());
                badgeImg.setImageResource(resource);
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
            cTaskList.addAll(uTaskList);
        }

        lDataAdapter = new CompletionAdapter(getActivity(),
                R.layout.daily_report, cTaskList);

        feedbackList.setAdapter(lDataAdapter);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.tm.updateCompletedTasks(cTaskList);
                cm.startTaskMenu();
            }
        });

        String[] hourMinSplit = timeSplited[0].toString().split(":");
        splitHour = Integer.parseInt(hourMinSplit[0].toString());
        splitMin = Integer.parseInt(hourMinSplit[1].toString());
        if(timeSplited[1] == "PM" ){
            splitHour += 12;
        }
        cm.scheduleReport(splitHour,splitMin,"dailyReport");
        return myView;
    }
}
