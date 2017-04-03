package com.qucrush.android.crushingitapp;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Tyler on 4/3/2017.
 */

public class SettingsActivity  extends AppCompatActivity {

    public boolean smallSize = true;
    View myView;
    TextView time;
    Button small, large;
    Intent intent = SettingsActivity.this.getIntent();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        time = (TextView) myView.findViewById(R.id.textTimeDisplay);
        small = (Button) myView.findViewById(R.id.buttonSmall);
        large = (Button) myView.findViewById(R.id.buttonLarge);
        if(MainActivity.tm.retrieveTime() != null){
            time.setText(MainActivity.tm.retrieveTime());
        }

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int min = c.get(Calendar.MINUTE);
                TimePickerDialog timepicker = new TimePickerDialog(SettingsActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                            //cm.storeTime(txt);
                            intent.putExtra("Storing time", txt);
                        }else{
                            if(minute < 10){
                                txt = (hourOfDay - 12) + ":0" + minute + " PM";
                            }else {
                                txt = (hourOfDay - 12) + ":" + minute + " PM";
                            }
                            time.setText(txt);
                            //cm.storeTime(txt);
                            intent.putExtra("Storing time", txt);
                        }
                        //cm.scheduleReport(hourOfDay,minute);
                        intent.putExtra("Schedule", txt);
                    }
                }, hour, min, false);
                timepicker.show();
            }
        });

        small.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "Small Text Selected", Toast.LENGTH_SHORT).show();
                Intent intent = SettingsActivity.this.getIntent();
                intent.putExtra( "Theme", "Small" );
                MainActivity.db.close();
                startActivity(intent);
            }
        });

        large.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "Large Text Selected", Toast.LENGTH_SHORT).show();
                Intent intent = SettingsActivity.this.getIntent();
                intent.putExtra( "Theme", "Large" );
                MainActivity.db.close();
                startActivity(intent);
            }
        });

        //http://stackoverflow.com/questions/3241729/android-dynamically-change-style-at-runtime


    }
}
