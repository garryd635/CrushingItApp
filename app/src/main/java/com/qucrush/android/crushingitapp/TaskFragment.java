package com.qucrush.android.crushingitapp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Garry on 2/8/2017.
 */

public class TaskFragment extends Fragment implements View.OnClickListener {
    //Instance Variables
    View myView;
    List<Task> taskList = new ArrayList<Task>();
    TextView dateTextView, timeTextView, nameTextView, cateTextView;
    Button add,delete;
    ListView lView;
    //ImageButton nextBtn,prevBtn;
    communicate cm;
    int count;
    CustomAdapter dataAdapter = null;

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
        //nextBtn = (ImageButton) myView.findViewById(R.id.nextButton);
        //prevBtn = (ImageButton) myView.findViewById(R.id.prevButton);
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
                                    //updateTextView(0, taskList);
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
            //updateTextView(count,taskList);
        }

        lView = (ListView) myView.findViewById(R.id.listView);
//        String[] lstLabel = new String[taskList.size()];
//        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
//        if(taskList != null) {
//            for (Task item : taskList) {
//                Map<String, String> datum = new HashMap<String, String>(2);
//                datum.put("Name:", item.getName());
//                datum.put("Due:", item.getDate());
//                data.add(datum);
//            }
//        }
//        else {
//            Map<String, String> datum = new HashMap<String, String>(2);
//            datum.put("Name:", "Sample Name");
//            datum.put("Due:", "Sample Date");
//            data.add(datum);
//        }

        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, taskList);
//        SimpleAdapter adapter = new SimpleAdapter(getActivity(), data,
//                android.R.layout.simple_list_item_2,
//                new String[] {"Name:", "Due:"},
//                new int[] {android.R.id.text1,
//                        android.R.id.text2});

        dataAdapter = new CustomAdapter(getActivity(),
                R.layout.task_menu, taskList);

        // Assign adapter to ListView
        if (taskList != null) {
            lView.setAdapter(dataAdapter);
        }

        //lView.setAdapter(adapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                cm.startEditForm(taskList.get(position));
            }
        });

        return myView;
    }

    public void onClick(View arg0){
        cm.startCreationForm();
    }

    public int getID(int count) {
        return taskList.get(count).getId();
    }
}
