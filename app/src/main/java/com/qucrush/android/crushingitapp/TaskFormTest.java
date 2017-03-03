package com.qucrush.android.crushingitapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
    Spinner recurOption;
    int count;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.task_creation, container, false);
        cm = (communicate) getActivity();
        add = (Button) myView.findViewById(R.id.addButton);
        cancel = (Button) myView.findViewById(R.id.cancelButton);
        recurOption = (Spinner) myView.findViewById(R.id.spinnerCreate);
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

        // Create an ArrayAdapter using the string array and a default spinner layout
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
        //        R.array.recurr_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        //recurOption.setAdapter(adapter);
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
