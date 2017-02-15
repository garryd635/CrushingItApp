package com.qucrush.android.crushingitapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Garry on 2/8/2017.
 */

public class TaskFragment extends Fragment implements View.OnClickListener {
    View myView;
//    TaskManager tm;
//    List<Task> taskList = new ArrayList<Task>();
//    TextView dateTextView, timeTextView, nameTextView, cateTextView;
    Button add;
    communicate cm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.task_menu, container, false);
//        dateTextView = (TextView) myView.findViewById(R.id.dateText);
//        timeTextView = (TextView) myView.findViewById(R.id.timeText);
//        nameTextView = (TextView) myView.findViewById(R.id.taskNameText);
//        cateTextView = (TextView) myView.findViewById(R.id.taskCateText);
        cm = (communicate) getActivity();
        add = (Button) myView.findViewById(R.id.addButton);
        add.setOnClickListener(this);
        return myView;
    }

    public void onClick(View arg0){
        cm.startCreationForm();
    }
}
