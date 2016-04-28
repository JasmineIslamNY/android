package com.tek_genie.notes;

/**
 * Created by jasmineislam on 2/24/16.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NoteListItemAdapter extends RecyclerView.Adapter<NoteListItemAdapter.ViewHolder> {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private ArrayList<NoteListItem> mNoteListItems = new ArrayList<NoteListItem>();

    public NoteListItemAdapter(Context context, RecyclerView recyclerView) {
        this.mContext = context;
        this.mRecyclerView = recyclerView;
        //this.mNoteListItems.add(new NoteListItem("This is your first note."));
        NoteDAO dao = new NoteDAO(context);
        mNoteListItems = (ArrayList<NoteListItem>) dao.list();
    };

    @Override
    public NoteListItemAdapter.ViewHolder onCreateViewHolder (ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.note_list_item, viewGroup, false);
            //final int pos = mRecyclerView.getChildLayoutPosition(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage(R.string.delete_dialog_box_message);
                builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NoteListItem noteListItem = mNoteListItems.get(mRecyclerView.getChildLayoutPosition(v));
                        NoteDAO dao = new NoteDAO(mContext);
                        dao.delete(noteListItem);
                        Toast.makeText(mContext, "Deleted: \n" + noteListItem.getText(), Toast.LENGTH_LONG).show();
                        removeItem(mRecyclerView.getChildLayoutPosition(v));
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(mContext, "Canceled the Delete", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                }
        });

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    NoteListItem noteListItem = mNoteListItems.get(mRecyclerView.getChildLayoutPosition(v));
                    //Toast.makeText(mContext, "Selected: " + noteListItem.getText(), Toast.LENGTH_LONG).show();
                    removeItem(mRecyclerView.getChildLayoutPosition(v));
                    Intent intent = new Intent(mContext, EditNoteActivity.class);
                    intent.putExtra("Note", noteListItem);

                    ((Activity)mContext).startActivityForResult(intent, 1);// 1 is the request code. The request code tells the activity who called it, etc MainActivity

                    return true;
                }
            });

            return new ViewHolder(v);
        };

    @Override
    public void onBindViewHolder(NoteListItemAdapter.ViewHolder viewHolder, int i) {
        NoteListItem noteListItem = mNoteListItems.get(i);
        viewHolder.setText(noteListItem.getText());
    };

    @Override
    public int getItemCount() {
        return mNoteListItems.size();
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        };

        public void setText(String text) {
            this.text.setText(text);
        };
    };

    public void addItem(NoteListItem item) {
        mNoteListItems.add(0, item);
        notifyItemInserted(0);
    }

    public void removeItem(int position) {
        mNoteListItems.remove(position);
        notifyItemRemoved(position);
    }


};