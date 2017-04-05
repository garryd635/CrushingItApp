package com.qucrush.android.crushingitapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DailyFeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_feedback);
        startDialog();

    }
    public void startDialog(){
        //super.onStart();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Daily Report is Ready!");
        builder.setMessage("Do you wish to view the daily feedback now?");
        builder.setPositiveButton("View Now",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(DailyFeedbackActivity.this,MainActivity.class);
                        intent.putExtra("FrgToLoad", "dailyReport");
                startActivity(intent);
    }
});
        builder.setNegativeButton("View Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(DailyFeedbackActivity.this,MainActivity.class);
                intent.putExtra("FrgToLoad", "taskMenu");
                intent.putExtra("prepReport", true);
                startActivity(intent);
            }
        });
        builder.create().show();
    }
}
