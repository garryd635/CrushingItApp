package com.qucrush.android.crushingitapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * DailyFeedbackActivity
 *  Activity that activates when the alarmreciver activates.  The activity will display
 *  a dialog to determine if the user wants to view the daily report now or later.  Then
 *  the activity will send instructions to the main activity based on the user's choice.
 */
public class DailyFeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_feedback);
        startDialog();

    }//onCreateView()

    /**
     * startDialog()
     *  Gives user the option to view the report now or later,
     *      and sends instructions to the MainActivity based on the choice.
     */
    public void startDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Daily Report is Ready!");
        builder.setMessage("Do you wish to view the daily feedback now?");
        builder.setPositiveButton("View Now",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Start the main activity with the intent to load the daily report
                        Intent intent = new Intent(DailyFeedbackActivity.this,MainActivity.class);
                        intent.putExtra("FrgToLoad", "dailyReport");
                startActivity(intent);
    }
});
        builder.setNegativeButton("View Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Start the main activity with the intent to prepare the report to be view through
                //  the Feedback Report option in the Navigation Drawer
                Intent intent = new Intent(DailyFeedbackActivity.this,MainActivity.class);
                intent.putExtra("FrgToLoad", "taskMenu");
                intent.putExtra("prepReport", true);
                startActivity(intent);
            }
        });
        //Display dialog
        builder.create().show();
    }//startDialog()
}//DailyFeedbackActivity
