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

/**
 * Created by Garry on 2/8/2017.
 */

public class TaskFormTest extends Fragment{
    View myView;
    TaskManager tmPointer;
    Button add,cancel;
    TextView name,desc,dateM,dateD,dateY,timeH,timeM,timeAMPM,category;
    communicate cm;
    RadioGroup amPMGrp, cateGrp;
    RadioButton amPMBtn, cateBtn;
    String fullDate, fullTime;
    int count;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.task_creation, container, false);
        cm = (communicate) getActivity();
        add = (Button) myView.findViewById(R.id.addButton);
        cancel = (Button) myView.findViewById(R.id.cancelButton);
        count = 0;

        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
               cm.startTaskMenu();
            }
        });

        add.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                saveTask();
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

        amPMGrp = (RadioGroup) myView.findViewById(R.id.radioAMPM);
        cateGrp = (RadioGroup) myView.findViewById(R.id.radioWorkLife);

        int selectedID = amPMGrp.getCheckedRadioButtonId();
        amPMBtn = (RadioButton) myView.findViewById(selectedID);

        selectedID = cateGrp.getCheckedRadioButtonId();
        cateBtn = (RadioButton) myView.findViewById(selectedID);

        fullDate = dateY.getText().toString() + "-" + dateM.getText().toString() + "-" +
                dateD.getText().toString();
        fullTime = timeH.getText().toString() + ":" + timeM.getText().toString() + " " +
                amPMBtn.getText().toString();

        MainActivity.tm.createTask(name.getText().toString(), desc.getText().toString(),fullDate,fullTime
                ,cateBtn.getText().toString());
    }
}
