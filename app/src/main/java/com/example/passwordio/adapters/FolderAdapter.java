package com.example.passwordio.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordio.R;
import com.example.passwordio.models.Folder;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    final Folder[] folders;

    public FolderAdapter(Folder[] folders) {
        this.folders = folders;
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_item, parent, false);
        return new FolderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Folder folder = folders[position];
        holder.countTextView.setText(String.valueOf(folder.count));
        holder.nameTextView.setText(folder.name);
        holder.iconImageView.setImageResource(folder.icon);
    }

    @Override
    public int getItemCount() {
        return folders.length;
    }
}
