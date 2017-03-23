package com.qucrush.android.crushingitapp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Garry on 2/8/2017.
 */

public class DailyFeedbackTest extends Fragment{
    View myView;
    Button button;
    communicate cm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.daily_feedback_test, container, false);
        cm = (communicate) getActivity();
        button = (Button) myView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setCancelable(true);
//                builder.setTitle("Daily Report is Ready!");
//                builder.setMessage("Do you wish to view the daily feedback now?");
//                builder.setPositiveButton("View Now",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                cm.startDailyReport();
//                            }
//                        });
//                builder.setNegativeButton("View Later", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });
//
//                AlertDialog dialog = builder.create();
//                dialog.show();
                //cm.scheduleReport();
            }
        });
        return myView;
    }
}
