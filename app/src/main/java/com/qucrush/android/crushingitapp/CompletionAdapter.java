package com.qucrush.android.crushingitapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Completion Adapter
 *
 * An adapter for the lists views in daily feedback report and completed Tasks in
 *  HomePage3 to display completed and incompleted tasks
 */

public class CompletionAdapter extends ArrayAdapter<Task> {
    private List<Task> taskList;
    private Context context;
    private LayoutInflater vi;

    public CompletionAdapter(Context context, int textViewResourceId,
                         List<Task> taskList) {
        super(context, textViewResourceId, taskList);
        this.taskList = new ArrayList<Task>();
        this.taskList = taskList;
        vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    private class ViewHolder {
        TextView code;
    }

    /**
     *Sets the individual Listview row according to the report display layout
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(final int position, View convertView, ViewGroup parent){
        CompletionAdapter.ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));

        if(convertView == null){
            convertView = vi.inflate(R.layout.report_display, null);
            holder = new CompletionAdapter.ViewHolder();
            holder.code = (TextView) convertView.findViewById(R.id.textReport);
            convertView.setTag(holder);
        }
        else {
            holder = (CompletionAdapter.ViewHolder) convertView.getTag();
        }
        //Set task name
        Task task = taskList.get(position);
        holder.code.setText(task.getName());
        holder.code.setTextColor(Color.BLACK);

        //Set the color of the row to be green if completed and red if the task isn't completed
        if(task.getCompletion().equals("yes")){
            convertView.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorLightGreen));

        }else{
            convertView.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorLightRed));
        }
        return convertView;
    }//getView()
}//CompletionAdapter
