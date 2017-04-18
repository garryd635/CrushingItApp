package com.qucrush.android.crushingitapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

/**
 * Created by Garry on 4/16/2017.
 */

public class BadgeDialogFragment extends DialogFragment{

    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //Pass Badge layout to Dialog
        builder.setView(inflater.inflate(R.layout.badge_info_dialog, null));
        builder.setNeutralButton(R.string.action_close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }

    public static BadgeDialogFragment newInstance() {
        BadgeDialogFragment f = new BadgeDialogFragment();
        return f;
    }
}
