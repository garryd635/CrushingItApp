package com.qucrush.android.crushingitapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
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
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    TextView beforeDate, afterDate, screenTitle;
    Button add,delete,go;
    ListView lView;
    Spinner filterOptions;
    //ImageButton nextBtn,prevBtn;
    communicate cm;
    int count;
    CustomAdapter dataAdapter = null;
    Boolean textViewClickable;
    String pickerStatus;
    private int mYear;
    private int mMonth;
    private int mDay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        myView = inflater.inflate(R.layout.task_menu, container, false);
        cm = (communicate) getActivity();
        taskList = MainActivity.tm.retrieveTasks();

        add = (Button) myView.findViewById(R.id.addButton);
        delete = (Button) myView.findViewById(R.id.deleteButton);
        go = (Button) myView.findViewById(R.id.goButton);

        beforeDate = (TextView) myView.findViewById(R.id.beforeDate);
        afterDate = (TextView) myView.findViewById(R.id.afterDate);

        filterOptions = (Spinner) myView.findViewById(R.id.dateFilterSpinner);
        lView = (ListView) myView.findViewById(R.id.listView);
        textViewClickable = true;

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
            }
        });


        beforeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textViewClickable == true){
                    final Calendar calendar = Calendar.getInstance();
                    int yy = calendar.get(Calendar.YEAR);
                    int mm = calendar.get(Calendar.MONTH);
                    int dd = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            monthOfYear +=1;//To display correct month in textview
                            String date = monthOfYear+ "-" + dayOfMonth + "-" + year;
                            beforeDate.setText(date);
                        }
                    }, yy, mm, dd);
                    datePicker.show();
                }
            }
        });

        afterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textViewClickable == true){
                    final Calendar calendar = Calendar.getInstance();
                    int yy = calendar.get(Calendar.YEAR);
                    int mm = calendar.get(Calendar.MONTH);
                    int dd = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            monthOfYear +=1;//To display correct month in textview
                            String date = monthOfYear+ "-" + dayOfMonth + "-" + year;
                            afterDate.setText(date);
                        }
                    }, yy, mm, dd);
                    datePicker.show();
                }
            }
        });
        if(MainActivity.tm.retrieveTasks() != null){
            count = 0;
            taskList = MainActivity.tm.retrieveTasks();
        }


        dataAdapter = new CustomAdapter(getActivity(),
                R.layout.task_menu, taskList);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.date_filter_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // Assign adapter to ListView
        if (taskList != null) {
            lView.setAdapter(dataAdapter);
        }
        filterOptions.setAdapter(adapter);


        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                cm.startEditForm(taskList.get(position));
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textViewClickable == true){
                    Boolean firstError = false;
                    if(beforeDate.getText().toString() == "Set Before Date" || afterDate.getText().toString() == "Set After Date"){
                        errorDialog("Please set a before and after date before pressing Go");
                        firstError = true;
                    }

                    if(firstError == false){
                        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                        Calendar firstDate = Calendar.getInstance();
                        Calendar secondDate = Calendar.getInstance();
                        try{
                            firstDate.setTime(sdf.parse(beforeDate.getText().toString()));
                            secondDate.setTime(sdf.parse(afterDate.getText().toString()));
                        }catch (ParseException e){
                            System.out.println("***ERROR***");
                        }
                        if(firstDate.after(secondDate) || firstDate.equals(secondDate)){
                            errorDialog("Please set the 'before date' before the 'after date'.  E.g. 1/2/2017 and 1/3/2017.  If you want today's date" +
                                    "please select 'Today' from the filter options.");
                        }else{
                            //Perform Date filtering for Tasks HERE
                        }
                    }
                }else{
                    //Peform Date filter for Tasks with Toady's date HERE
                }
            }
        });

        filterOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selected = parentView.getItemAtPosition(position).toString();
                if (selected.equals("None")){
                    beforeDate.setVisibility(View.GONE);
                    afterDate.setVisibility(View.GONE);
                    go.setVisibility(View.GONE);
                }
                if(selected.equals("Today")){
                    textViewClickable = false;
                    final Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    afterDate.setVisibility(View.VISIBLE);
                    beforeDate.setVisibility(View.GONE);
                    go.setVisibility(View.VISIBLE);
                    afterDate.setText(month + "-" + day + "-" + year);
                }
                if((selected.equals("Between Dates"))){
                    textViewClickable = true;
                    beforeDate.setVisibility(View.VISIBLE);
                    afterDate.setVisibility(View.VISIBLE);
                    go.setVisibility(View.VISIBLE);
                    beforeDate.setText("Set Before Date");
                    afterDate.setText("Set After Date");

                }
            }
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
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

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mYear = year;
        mMonth = monthOfYear;
        mDay = dayOfMonth;
        updateTextview();
    }

    public void updateTextview(){
        if(pickerStatus == "before"){
            beforeDate.setText(mMonth + "/" + mDay + "/" + mYear);
        }
        if(pickerStatus == "after"){
            afterDate.setText(mMonth + "/" + mDay + "/" + mYear);
        }
    }

    public void errorDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle("Error");
        builder.setMessage(message);
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            ((DatePickerDialog.OnDateSetListener) getActivity()).onDateSet(view, year, month, day);

        }

    }
}
