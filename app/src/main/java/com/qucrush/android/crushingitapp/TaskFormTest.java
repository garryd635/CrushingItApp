package com.qucrush.android.crushingitapp;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

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
        dateD = (TextView) myView.findViewById(R.id.inputDay);
        timeH = (TextView) myView.findViewById(R.id.inputHours);
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

        dateD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
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
        timeH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
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
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.recurr_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        recurOption.setAdapter(adapter);
        return myView;
    }


    public void saveTask(){
        name = (TextView) myView.findViewById(R.id.InputName);
        desc = (TextView) myView.findViewById(R.id.inputDesc);

        //timeM = (TextView) myView.findViewById(R.id.inputMins);


        //amPMGrp = (RadioGroup) myView.findViewById(R.id.radioAMPM);
        cateGrp = (RadioGroup) myView.findViewById(R.id.radioWorkLife);

        //int selectedID = amPMGrp.getCheckedRadioButtonId();
        //amPMBtn = (RadioButton) myView.findViewById(selectedID);

        int selectedID = cateGrp.getCheckedRadioButtonId();
        cateBtn = (RadioButton) myView.findViewById(selectedID);

        fullDate = dateD.getText().toString();
        fullTime = timeH.getText().toString();

        MainActivity.tm.createTask(name.getText().toString(), desc.getText().toString(),fullDate,fullTime
                ,cateBtn.getText().toString());
    }
}
