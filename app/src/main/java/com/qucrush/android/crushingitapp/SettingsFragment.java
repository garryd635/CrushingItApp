package com.qucrush.android.crushingitapp;

import android.support.v4.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Garry on 2/8/2017.
 */

public class SettingsFragment extends Fragment{
    public boolean smallSize = true;
    View myView;
    TextView time;
    communicate cm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTheme(R.style.AppTheme_Small);
        myView = inflater.inflate(R.layout.settings, container, false);
        time = (TextView) myView.findViewById(R.id.textTimeDisplay);
        cm = (communicate) getActivity();
        if(MainActivity.tm.retrieveTime() != null){
            time.setText(MainActivity.tm.retrieveTime());
        }

        time.setOnClickListener(new View.OnClickListener() {
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
                            if(minute < 10){
                                txt = hourOfDay + ":0" + minute + " AM";
                            }else {
                                txt = hourOfDay + ":" + minute + " AM";
                            }
                            time.setText(txt);
                            cm.storeTime(txt);
                        }else{
                            if(minute < 10){
                                txt = (hourOfDay - 12) + ":0" + minute + " PM";
                            }else {
                                txt = (hourOfDay - 12) + ":" + minute + " PM";
                            }
                            time.setText(txt);
                            cm.storeTime(txt);
                        }
                        cm.scheduleReport(hourOfDay,minute,"settings");
                    }
                }, hour, min, false);
                timepicker.show();
            }
        });

        //http://stackoverflow.com/questions/3241729/android-dynamically-change-style-at-runtime

        return myView;
    }
}
