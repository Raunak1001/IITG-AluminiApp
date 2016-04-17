package com.example.alumniapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {
    Context context;
    private ArrayList<HashMap<String, String>> adapterDataset;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, description, icon;
        SQLiteHandler db;
        LinearLayout layout;


        public ViewHolder(View v) {
            super(v);

            db = new SQLiteHandler(context);
            title = (TextView) v.findViewById(R.id.ann_title);
            description = (TextView) v.findViewById(R.id.ann_des);
            icon = (TextView) v.findViewById(R.id.ann_icon);
            layout = (LinearLayout) v.findViewById(R.id.ann_layout);
        }
    }


    @Override
    public AnnouncementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final HashMap<String, String> announcemnt = adapterDataset.get(position);
        final String ann_title = announcemnt.get(context.getString(R.string.ANN_TITLE));
        holder.title.setText(ann_title);
        holder.description.setText(announcemnt.get(context.getString(R.string.ANN_DESCRIPTION)));
        if (!ann_title.isEmpty())
            holder.icon.setText(ann_title.substring(0, 1));

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnouncementView.class);

                intent.putExtra("title", ann_title);
                intent.putExtra("description", announcemnt.get(context.getString(R.string.ANN_DESCRIPTION)));

                context.startActivity(intent);

            }
        });

        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setMessage("Delete Reminder ?");
                alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        holder.db.remAnn(ann_title, announcemnt.get(context.getString(R.string.ANN_DESCRIPTION)));
                        remove(position);
                    }
                });
                alertDialog.show();
                return false;
            }
        });
    }

    public void add(int position, HashMap<String, String> item) {
        adapterDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        adapterDataset.remove(position);
        notifyItemRemoved(position);
    }

    public void swap(ArrayList<HashMap<String, String>> newData) {
        adapterDataset.clear();
        adapterDataset.addAll(newData);
        notifyDataSetChanged();
    }

    public AnnouncementAdapter(ArrayList<HashMap<String, String>> myDataset, Context context) {
        adapterDataset = myDataset;
        this.context = context;
    }


    @Override
    public int getItemCount() {
        return adapterDataset.size();
    }
}
