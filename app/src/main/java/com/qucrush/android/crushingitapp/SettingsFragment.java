package com.qucrush.android.crushingitapp;

import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Garry on 2/8/2017.
 */

public class SettingsFragment extends Fragment{
    View myView;
    TextView time;
    communicate cm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.settings, container, false);
        time = (TextView) myView.findViewById(R.id.textTimeDisplay);
        cm = (communicate) getActivity();

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
                            txt = hourOfDay + ":" + minute + " AM";
                            time.setText(txt);
                        }else{
                            txt = (hourOfDay - 12) + ":" + minute + " PM";
                            time.setText(txt);
                        }
                        cm.scheduleReport(hourOfDay,minute);
                        System.out.println(hourOfDay);
                    }
                }, hour, min, false);
                timepicker.show();
            }
        });


        return myView;
    }
}
