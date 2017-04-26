package com.qucrush.android.crushingitapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tyler on 2/24/2017.
 */

public class CustomAdapter extends ArrayAdapter<Task>{
    private List<Task> taskList;
    private Context context;
    private LayoutInflater vi;

    public CustomAdapter(Context context, int textViewResourceId,
                         List<Task> taskList) {
        super(context, textViewResourceId, taskList);
        this.context = context;
        this.taskList = new ArrayList<Task>();
        this.taskList = taskList;
        vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //this.taskList.addAll(taskList);
    }

    private class ViewHolder {
        TextView code;
        CheckBox name;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));

        if (convertView == null) {

            //LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.task_display, null);

            holder = new ViewHolder();
            holder.code = (TextView) convertView.findViewById(R.id.textTask1);
            holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);

            convertView.setTag(holder);

            holder.name.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    Task task = (Task) cb.getTag();
//                    task.setSelected(cb.isChecked());
                    System.out.println(cb.isChecked());
                    if(cb.isChecked() == true){
                        MainActivity.tm.updateTaskInAdapter(taskList.get(position).getName(),taskList.get(position).getDesc(),taskList.get(position).getDate()
                        , taskList.get(position).getTime(), taskList.get(position).getCategory(), "yes",taskList.get(position).getRecurring(),taskList.get(position).getId());
                        taskList = MainActivity.tm.retrieveTasks();
                        System.out.println("New Task Update: " + taskList.get(position).getName() + " complete? " + taskList.get(position).getCompletion());
                    }
                    if(cb.isChecked() == false){
                        MainActivity.tm.updateTaskInAdapter(taskList.get(position).getName(),taskList.get(position).getDesc(),taskList.get(position).getDate()
                                , taskList.get(position).getTime(), taskList.get(position).getCategory(), "no",taskList.get(position).getRecurring(),taskList.get(position).getId());
                        taskList = MainActivity.tm.retrieveTasks();
                        System.out.println("New Task Update: " + taskList.get(position).getName() + " complete? " + taskList.get(position).getCompletion());
                    }
                }
            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Task task = taskList.get(position);
        holder.code.setText("      Due: " +  task.getDate());
        holder.name.setText(task.getName());
        //holder.name.setChecked(task.isSelected());
        holder.name.setTag(task);
        if(task.getCompletion().equals("yes")){
            holder.name.setChecked(true);
        }
        context.setTheme(R.style.AppTheme_Small);
        return convertView;
    }

//    private void checkButtonClick() {
//
//        Button myButton = (Button) findViewById(R.id.findSelected);
//        myButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                StringBuffer responseText = new StringBuffer();
//                responseText.append("The following were selected...\n");
//
//                ArrayList<Task> countryList = dataAdapter.countryList;
//                for(int i=0;i<countryList.size();i++){
//                    Task task = countryList.get(i);
//                    if(task.isSelected()){
//                        responseText.append("\n" + task.getName());
//                    }
//                }
//            }
//        });
//    }
}
