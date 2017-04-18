package com.qucrush.android.crushingitapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Garry on 3/2/2017.
 */

public class BadgeAdapter extends ArrayAdapter<Badge> {
    private List<Badge> badgeList;
    private Context context;
    private LayoutInflater vi;

    public BadgeAdapter(Context context, int textViewResourceId,
                        List<Badge> badgeList) {
        super(context, textViewResourceId, badgeList);
        this.badgeList = new ArrayList<Badge>();
        this.badgeList = badgeList;
        this.context = context;
        vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    private class ViewHolder {
        ImageView img;
        //CheckBox name;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        BadgeAdapter.ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));

        if(convertView == null){
            convertView = vi.inflate(R.layout.badge_gridview_display, null);

            holder = new BadgeAdapter.ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.badgeImg);

            convertView.setTag(holder);

        }
        else {
            holder = (BadgeAdapter.ViewHolder) convertView.getTag();
        }

        final Badge badge = badgeList.get(position);
        final int resource = context.getResources().getIdentifier(badge.getImgsrc(),"drawable",
                context.getPackageName());
        holder.img.setImageResource(resource);
        if(badge.getIsEarned().equals("no")){

            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            holder.img.setColorFilter(filter);
            holder.img.setImageAlpha(112);
        }
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DialogFragment dialog = new BadgeDialogFragment.newInstance();
                startDialog(resource,badge.getName(),badge.getDesc());
            }
        });
        //holder.name.setText(task.getName());
        //holder.name.setChecked(task.isSelected());
        //holder.name.setTag(task);

        return convertView;
    }

    public void startDialog(int Res, String name, String desc){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.badge_info_dialog, null);
        ImageView img = (ImageView) convertView.findViewById(R.id.badgeDialogImg);
        TextView titleText = (TextView) convertView.findViewById(R.id.badgeDialogName);
        TextView descText = (TextView) convertView.findViewById(R.id.badgeDialogDesc);
        titleText.setText(name);
        descText.setText(desc);
        img.setImageResource(Res);
        builder.setView(convertView);
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}
