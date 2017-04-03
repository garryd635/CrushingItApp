package com.qucrush.android.crushingitapp;

import android.app.Fragment;
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
    Button small, large;
    communicate cm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.settings, container, false);
        time = (TextView) myView.findViewById(R.id.textTimeDisplay);
        small = (Button) myView.findViewById(R.id.buttonSmall);
        large = (Button) myView.findViewById(R.id.buttonLarge);
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
                        cm.scheduleReport(hourOfDay,minute);
                    }
                }, hour, min, false);
                timepicker.show();
            }
        });

        small.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Small Text Selected", Toast.LENGTH_SHORT).show();
                Intent intent = getActivity().getIntent();
                intent.putExtra( "Theme", "Small" );
                MainActivity.db.close();
                startActivity(intent);
            }
        });

        large.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Large Text Selected", Toast.LENGTH_SHORT).show();
                Intent intent = getActivity().getIntent();
                intent.putExtra( "Theme", "Large" );
                MainActivity.db.close();
                startActivity(intent);
            }
        });

        //http://stackoverflow.com/questions/3241729/android-dynamically-change-style-at-runtime

        return myView;
    }
}
