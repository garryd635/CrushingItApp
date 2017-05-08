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
 * BadgeAdapter
 *
 * An adapter for the listview in RewardFragment to display
 *  the badges and alertdialog for badge details
 */

public class BadgeAdapter extends ArrayAdapter<Badge> {
    //Instance Variables
    private List<Badge> badgeList;
    private Context context;
    private LayoutInflater vi;

    /**
     * Constructor
     * @param context
     * @param textViewResourceId
     * @param badgeList
     */
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
    }

    /**
     * Sets the individual listview row according to the badge_gridview_display layout
     *
     * @param position The specific position of the badge
     * @param convertView
     * @param parent
     * @return
     */
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

        //Set the badges to be gray and transparent when not earned
        if(badge.getIsEarned().equals("no")){

            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            holder.img.setColorFilter(filter);
            holder.img.setImageAlpha(112);
        }
        //Start dialog when a badge is pressed
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog(resource,badge.getName(),badge.getDesc());
            }
        });

        return convertView;
    }//getView

    /**
     * startDialog
     *  Displays the badge's information such as earned date and description
     * @param Res The resource number of the badge image
     * @param name Name of the badge
     * @param desc Description of the badge
     */
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
    }//startDialog
}//BadgeAdapter
