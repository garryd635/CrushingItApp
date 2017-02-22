package com.qucrush.android.crushingitapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Garry on 2/8/2017.
 */

public class TaskEditFragment extends Fragment{
    View myView;
    Button update,cancel;
    communicate cm;
    TextView name,desc,dateM,dateD,dateY,timeH,timeM,timeAMPM,category;
    RadioGroup AMPMgrp, categrp;
    RadioButton amPMBtn, cateBtn;
    Task editTask;
    String fullDate;
    String fullTime;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.task_edit, container, false);
        cancel = (Button) myView.findViewById(R.id.cancelButton);
        update = (Button) myView.findViewById(R.id.confirmButton);

        name = (TextView) myView.findViewById(R.id.InputName);
        desc = (TextView) myView.findViewById(R.id.inputDesc);
        dateM = (TextView) myView.findViewById(R.id.inputMonth);
        dateD = (TextView) myView.findViewById(R.id.inputDay);
        dateY = (TextView) myView.findViewById(R.id.inputYear);
        timeH = (TextView) myView.findViewById(R.id.inputHours);
        timeM = (TextView) myView.findViewById(R.id.inputMins);
        AMPMgrp = (RadioGroup) myView.findViewById(R.id.radioAMPM);
        categrp = (RadioGroup) myView.findViewById(R.id.radioWorkLife);

        cm = (communicate) getActivity();
        editTask = cm.getTaskToEdit();

        String[] splitDate = editTask.getDate().split("-");
        String[] splitTime = editTask.getTime().split("\\s+");//Splits into full time and am/pm
        String[] splitNumTime = splitTime[0].split(":");//Splits the 00:00 time

        name.setText(editTask.getName());
        desc.setText(editTask.getDesc());
        dateM.setText(splitDate[1]);
        dateD.setText(splitDate[2]);
        dateY.setText(splitDate[0]);
        timeH.setText(splitNumTime[0]);
        timeM.setText(splitNumTime[1]);
        if(splitTime[1] == "AM"){
            AMPMgrp.check(R.id.radioButton);
        }
        else{
            AMPMgrp.check(R.id.radioButton2);
        }

        if(editTask.getCategory() == "Life"){
            categrp.check(R.id.radioButton4);
        }
        else{
            categrp.check(R.id.radioButton3);
        }

        update.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                saveTask();
                cm.startTaskMenu();
            }
        });



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cm.startTaskMenu();
            }
        });
        return myView;
    }

    public void saveTask(){
        name = (TextView) myView.findViewById(R.id.InputName);
        desc = (TextView) myView.findViewById(R.id.inputDesc);
        timeH = (TextView) myView.findViewById(R.id.inputHours);
        timeM = (TextView) myView.findViewById(R.id.inputMins);
        dateM = (TextView) myView.findViewById(R.id.inputMonth);
        dateD = (TextView) myView.findViewById(R.id.inputDay);
        dateY = (TextView) myView.findViewById(R.id.inputYear);

        AMPMgrp = (RadioGroup) myView.findViewById(R.id.radioAMPM);
        categrp = (RadioGroup) myView.findViewById(R.id.radioWorkLife);

        int selectedID = AMPMgrp.getCheckedRadioButtonId();
        amPMBtn = (RadioButton) myView.findViewById(selectedID);

        selectedID = categrp.getCheckedRadioButtonId();
        cateBtn = (RadioButton) myView.findViewById(selectedID);

        fullDate = dateY.getText().toString() + "-" + dateM.getText().toString() + "-" +
                dateD.getText().toString();
        fullTime = timeH.getText().toString() + ":" + timeM.getText().toString() + " " +
                amPMBtn.getText().toString();

        MainActivity.tm.updateTask(name.getText().toString(), desc.getText().toString(),fullDate,fullTime
                ,cateBtn.getText().toString(), editTask.getId());
    }
}