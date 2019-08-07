package com.oskarrek.notepadapp.recycler_view_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.oskarrek.notepadapp.R;
import com.oskarrek.notepadapp.models.Note;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private List<Note> notes;


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.note_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        //TODO: add image view

        // Get views.
        ImageView imageView = holder.noteView.findViewById(R.id.note_list_image);
        TextView titleView = holder.noteView.findViewById(R.id.note_list_title);
        TextView textView =  holder.noteView.findViewById(R.id.note_list_text);

        // Get values.
        String title = notes.get(position).getTitle();
        String text = notes.get(position).getText();

        // Set Values.
        //TODO
        imageView.setVisibility(View.GONE);

        titleView.setText(title);
        textView.setText(text);

    }

    @Override
    public int getItemCount() {
        if(notes != null)
            return notes.size();
        return -1;
    }



    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        private CardView noteView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteView = (CardView) itemView;
        }
    }


    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }
}
