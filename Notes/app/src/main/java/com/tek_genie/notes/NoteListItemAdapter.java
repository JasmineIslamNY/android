package com.tek_genie.notes;

/**
 * Created by jasmineislam on 2/24/16.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    };

    @Override
    public NoteListItemAdapter.ViewHolder onCreateViewHolder (ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.note_list_item, viewGroup, false);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String TAG = "MyData";
                    Log.i(TAG, "Removing: " + mNoteListItems.get(mRecyclerView.getChildLayoutPosition(v)).getText());
                    removeItem(mRecyclerView.getChildLayoutPosition(v));
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