package com.qucrush.android.crushingitapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Garry on 2/8/2017.
 */

public class TaskFormTest extends Fragment{
    View myView;
    TaskManager tmPointer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.task_creation, container, false);
        return myView;
    }


    public void saveTask(TaskManager tm){
        tmPointer = tm;

    }
}
