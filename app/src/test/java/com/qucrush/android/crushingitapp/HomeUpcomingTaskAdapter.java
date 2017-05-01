package com.qucrush.android.crushingitapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * HomeUpcomingTaskAdapter
 * An adapter for the listview in home_2.xml that will display the name, date, and time of
 *  upcoming tasks
 */

public class HomeUpcomingTaskAdapter extends ArrayAdapter<Task> {
    private List<Task> taskList;
    private Context context;
    private LayoutInflater vi;

    /**
     * Constructor
     * @param context
     * @param textViewResourceId
     * @param taskList
     */
    public HomeUpcomingTaskAdapter(Context context, int textViewResourceId,
                                   List<Task> taskList) {
        super(context, textViewResourceId, taskList);
        this.taskList = new ArrayList<Task>();
        this.taskList = taskList;
        vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    //Create holder to change the textviews in the display
    private class ViewHolder {
        TextView name;
        TextView time;
        TextView date;
    }

    //Set the holders to each of the textviews in home_upcoming_task_display.xml
    public View getView(final int position, View convertView, ViewGroup parent){
        HomeUpcomingTaskAdapter.ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));

        if(convertView == null){
            convertView = vi.inflate(R.layout.home_upcoming_task_display, null);

            holder = new HomeUpcomingTaskAdapter.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.upcomingTaskName);
            holder.date = (TextView) convertView.findViewById(R.id.upcomingTaskDate);
            holder.time = (TextView) convertView.findViewById(R.id.upcomingTaskTime);

            convertView.setTag(holder);
        }
        else {
            holder = (HomeUpcomingTaskAdapter.ViewHolder) convertView.getTag();
        }

        //Set name and color the text black
        Task task = taskList.get(position);
        holder.name.setText(task.getName());
        holder.date.setText(task.getDate());
        holder.time.setText(task.getTime());
        holder.name.setTextColor(Color.BLACK);
        holder.date.setTextColor(Color.BLACK);
        holder.time.setTextColor(Color.BLACK);
        return convertView;
    }
}
