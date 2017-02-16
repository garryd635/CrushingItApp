package com.qucrush.android.crushingitapp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Garry on 2/8/2017.
 */

public class TaskFragment extends Fragment implements View.OnClickListener {
    //Instance Variables
    View myView;
    List<Task> taskList = new ArrayList<Task>();
    TextView dateTextView, timeTextView, nameTextView, cateTextView;
    Button add,delete;
    ImageButton nextBtn,prevBtn;
    CardView displayCard;
    communicate cm;
    int count;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.task_menu, container, false);
//        dateTextView = (TextView) myView.findViewById(R.id.dateText);
//        timeTextView = (TextView) myView.findViewById(R.id.timeText);
//        nameTextView = (TextView) myView.findViewById(R.id.taskNameText);
//        cateTextView = (TextView) myView.findViewById(R.id.taskCateText);
        cm = (communicate) getActivity();
        taskList = MainActivity.tm.retrieveTasks();

        add = (Button) myView.findViewById(R.id.addButton);
        delete = (Button) myView.findViewById(R.id.deleteButton);
        nextBtn = (ImageButton) myView.findViewById(R.id.nextButton);
        prevBtn = (ImageButton) myView.findViewById(R.id.prevButton);
        displayCard = (CardView) myView.findViewById(R.id.taskDisplay);

        add.setOnClickListener(this);
        delete.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (taskList != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setCancelable(true);
                    builder.setTitle("Delete Current Task");
                    builder.setMessage("Do you wish to delete this task?");
                    builder.setPositiveButton("Delete",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MainActivity.tm.deleteTask(taskList.get(count).getId());
                                    taskList = MainActivity.tm.retrieveTasks();
                                    updateTextView(0, taskList);
                                }
                            });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });


                AlertDialog dialog = builder.create();
                dialog.show();
                }
//                if(MainActivity.tm.retrieveTasks() != null) {

//                            count++;
//                    if (count >= TaskManager.getCount().taskList.size()) {
//                        System.out.println("Adjusting Next Counter");
//                        count = 0;
//                    }
//                    updateTextView(count,taskList);
//                }
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(MainActivity.tm.retrieveTasks() != null) {
                    count++;
                    if (count >= taskList.size()) {
                        System.out.println("Adjusting Next Counter");
                        count = 0;
                    }
                    updateTextView(count,taskList);
                }
            }
        });
        prevBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(MainActivity.tm.retrieveTasks() != null) {
                    count--;
                    if (count < 0) {
                        System.out.println("Adujusting Prev Counter");
                        count = taskList.size()-1;
                    }
                    updateTextView(count,taskList);
                }
            }
        });

        if(MainActivity.tm.retrieveTasks() != null){
//            dateTextView = (TextView) myView.findViewById(R.id.dateText);
//            timeTextView = (TextView) myView.findViewById(R.id.timeText);
//            nameTextView = (TextView) myView.findViewById(R.id.taskNameText);
//            cateTextView = (TextView) myView.findViewById(R.id.taskCateText);
//
//            taskList = MainActivity.tm.retrieveTasks();
//            count = 0;
//            dateTextView.setText(taskList.get(count).getDate());
//            timeTextView.setText(taskList.get(count).getTime());
//            cateTextView.setText(taskList.get(count).getCategory());
//            nameTextView.setText(taskList.get(count).getName());
            count = 0;
            taskList = MainActivity.tm.retrieveTasks();
            updateTextView(count,taskList);
        }

        displayCard.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(taskList != null){
                    cm.startEditForm(taskList.get(count));
                }
            }
        });
        return myView;
    }

    public void onClick(View arg0){
        cm.startCreationForm();
    }

    public void updateTextView(int count, List<Task> taskList){
        if (taskList != null) {
            dateTextView = (TextView) myView.findViewById(R.id.dateText);
            timeTextView = (TextView) myView.findViewById(R.id.timeText);
            nameTextView = (TextView) myView.findViewById(R.id.taskNameText);
            cateTextView = (TextView) myView.findViewById(R.id.taskCateText);

            //taskList = MainActivity.tm.retrieveTasks();
            dateTextView.setText(taskList.get(count).getDate());
            timeTextView.setText(taskList.get(count).getTime());
            cateTextView.setText(taskList.get(count).getCategory());
            nameTextView.setText(taskList.get(count).getName());
            count = 0;
        } else {
            dateTextView = (TextView) myView.findViewById(R.id.dateText);
            timeTextView = (TextView) myView.findViewById(R.id.timeText);
            nameTextView = (TextView) myView.findViewById(R.id.taskNameText);
            cateTextView = (TextView) myView.findViewById(R.id.taskCateText);

            //taskList = MainActivity.tm.retrieveTasks();
            dateTextView.setText("NA");
            timeTextView.setText("NA");
            cateTextView.setText("NA");
            nameTextView.setText("NA");
            count = 0;
        }
    }

    public int getID(int count) {
        return taskList.get(count).getId();
    }
}
