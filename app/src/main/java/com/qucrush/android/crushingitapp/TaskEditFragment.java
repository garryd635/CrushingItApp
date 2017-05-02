package com.qucrush.android.crushingitapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Garry on 2/8/2017.
 */

public class TaskEditFragment extends Fragment{
    View myView;
    Button update,cancel,delete;
    communicate cm;
    TextView name,desc,dateM,dateD,dateY,timeH,timeM,timeAMPM,category;
    RadioGroup AMPMgrp, categrp;
    RadioButton amPMBtn, cateBtn;
    Task editTask;
    String fullDate;
    String fullTime;
    Spinner recurOption;
    Animation button_shrink;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTheme(R.style.AppTheme_Small);
        myView = inflater.inflate(R.layout.task_edit, container, false);
        cancel = (Button) myView.findViewById(R.id.cancelButton);
        update = (Button) myView.findViewById(R.id.confirmButton);
        delete = (Button) myView.findViewById(R.id.deleteTaskB);
        recurOption = (Spinner) myView.findViewById(R.id.spinnerEdit);

        name = (TextView) myView.findViewById(R.id.InputName);
        desc = (TextView) myView.findViewById(R.id.inputDesc);
        //dateM = (TextView) myView.findViewById(R.id.inputMonth);
        dateD = (TextView) myView.findViewById(R.id.inputDay);
        //dateY = (TextView) myView.findViewById(R.id.inputYear);
        timeH = (TextView) myView.findViewById(R.id.inputHours);
        //timeM = (TextView) myView.findViewById(R.id.inputMins);
        //AMPMgrp = (RadioGroup) myView.findViewById(R.id.radioAMPM);
        categrp = (RadioGroup) myView.findViewById(R.id.radioWorkLife);

        cm = (communicate) getActivity();
        editTask = cm.getTaskToEdit();

        String[] splitDate = editTask.getDate().split("-");
        String[] splitTime = editTask.getTime().split("\\s+");//Splits into full time and am/pm
        String[] splitNumTime = splitTime[0].split(":");//Splits the 00:00 time

        button_shrink = AnimationUtils.loadAnimation(getActivity(),R.anim.button_shrink);

        name.setText(editTask.getName());
        desc.setText(editTask.getDesc());
        //dateM.setText(splitDate[1]);
        dateD.setText(editTask.getDate());
        //dateY.setText(splitDate[0]);
        timeH.setText(editTask.getTime());
        //timeM.setText(splitNumTime[1]);
        if(splitTime[1] == "AM"){
           // AMPMgrp.check(R.id.radioButton);
        }
        else{
            //AMPMgrp.check(R.id.radioButton2);
        }

        if(editTask.getCategory() == "Life"){
            categrp.check(R.id.radioButton4);
        }
        else{
            categrp.check(R.id.radioButton3);
        }

        update.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                update.startAnimation(button_shrink);
                update.playSoundEffect(android.view.SoundEffectConstants.CLICK);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after .2s = 200ms
                        saveTask();
                        cm.startTaskMenu();
                    }
                }, 500);

            }
        });


        timeH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeH.playSoundEffect(android.view.SoundEffectConstants.CLICK);
                final Calendar c = Calendar.getInstance();
                String timeTxt = timeH.getText().toString();
                String[] splitTime = timeTxt.split("\\s+");
                Calendar setCal = Calendar.getInstance();
                //setCal.(Calendar.HOUR_OF_DAY, hour);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Date date = null;
                try{
                    date = sdf.parse(splitTime[0].toString());
                }catch (ParseException e){

                }
                c.setTime(date);
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int min = c.get(Calendar.MINUTE);

                TimePickerDialog timepicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String txt;
                        if(hourOfDay < 13){
                            if(minute > 9){
                                txt = hourOfDay + ":" + minute + " AM";
                                timeH.setText(txt);
                            }else{
                                txt = hourOfDay + ":0" + minute + " AM";
                                timeH.setText(txt);
                            }

                        }else{
                            if(minute > 9){
                                txt = (hourOfDay-12) + ":" + minute + " PM";
                                timeH.setText(txt);
                            }else{
                                txt = (hourOfDay-12) + ":0" + minute + " PM";
                                timeH.setText(txt);
                            }
                        }
                    }
                }, hour, min, false);
                timepicker.show();
            }
        });

        dateD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateD.playSoundEffect(android.view.SoundEffectConstants.CLICK);
                final Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try{
                    date = sdf.parse(dateD.getText().toString());
                }catch (ParseException e){

                }
                calendar.setTime(date);
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear +=1;//To display correct month in textview
                        String date = year + "-" + monthOfYear+ "-" + dayOfMonth;
                        dateD.setText(date);
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.recurr_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recurOption.setAdapter(adapter);

        if(editTask.getRecurring().equals("None")){
            recurOption.setSelection(0);
        }else if(editTask.getRecurring().equals("Daily")){
            recurOption.setSelection(1);
        }else if(editTask.getRecurring().equals("Weekly")){
            recurOption.setSelection(2);
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel.playSoundEffect(android.view.SoundEffectConstants.CLICK);
                cancel.startAnimation(button_shrink);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after .2s = 200ms
                        cm.startTaskMenu();
                    }
                }, 500);

            }
        });

        delete.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                delete.playSoundEffect(android.view.SoundEffectConstants.CLICK);
                delete.startAnimation(button_shrink);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after .2s = 200ms
                        System.out.println("Button pressed");
                        MainActivity.tm.deleteTask(editTask.getId());
                        cm.startTaskMenu();
                    }
                }, 500);

            }
        });


        return myView;
    }

    public void checkDate(){

    }

    public void saveTask(){
        name = (TextView) myView.findViewById(R.id.InputName);
        desc = (TextView) myView.findViewById(R.id.inputDesc);
        timeH = (TextView) myView.findViewById(R.id.inputHours);
        dateD = (TextView) myView.findViewById(R.id.inputDay);
        //dateY = (TextView) myView.findViewById(R.id.inputYear);

        //AMPMgrp = (RadioGroup) myView.findViewById(R.id.radioAMPM);
        categrp = (RadioGroup) myView.findViewById(R.id.radioWorkLife);

        //int selectedID = AMPMgrp.getCheckedRadioButtonId();
//        amPMBtn = (RadioButton) myView.findViewById(selectedID);

        int selectedID = categrp.getCheckedRadioButtonId();
        cateBtn = (RadioButton) myView.findViewById(selectedID);

        fullDate = dateD.getText().toString();
        fullTime = timeH.getText().toString();

        MainActivity.tm.updateTask(name.getText().toString(), desc.getText().toString(),fullDate,fullTime
                ,cateBtn.getText().toString(),editTask.getCompletion(),recurOption.getSelectedItem().toString(), editTask.getId());
    }
}