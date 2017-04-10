package com.qucrush.android.crushingitapp;

import android.content.Context;
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

        Badge badge = badgeList.get(position);
        System.out.println("*************" + badge.getImgsrc());
        int resource = context.getResources().getIdentifier(badge.getImgsrc(),"drawable",
                context.getPackageName());
        System.out.println(resource);
        holder.img.setImageResource(resource);
        if(badge.getIsEarned().equals("no")){

            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            holder.img.setColorFilter(filter);
        }
        //holder.name.setText(task.getName());
        //holder.name.setChecked(task.isSelected());
        //holder.name.setTag(task);

        return convertView;
    }
}
