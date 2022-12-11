package com.example.passwordio.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordio.LoginItemViewActivity;
import com.example.passwordio.NoteItemViewActivity;
import com.example.passwordio.R;
import com.example.passwordio.models.Folder;
import com.example.passwordio.models.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    final Note[] notes;

    public NoteAdapter(Note[] notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_item, parent, false);
        return new NoteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.countTextView.setVisibility(View.GONE);
        Note note = notes[position];

        holder.nameTextView.setText(note.name);
        holder.iconImageView.setImageResource(R.drawable.ic_baseline_note_24);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), NoteItemViewActivity.class);
                intent.putExtra("note", note);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.length;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView countTextView;
        private final ImageView iconImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.folderItemNameTextView);
            countTextView = itemView.findViewById(R.id.folderItemCountTextView);
            iconImageView = itemView.findViewById(R.id.folderItemIconImageView);
        }
    }
}
