package com.example.passwordio.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordio.LoginItemViewActivity;
import com.example.passwordio.R;
import com.example.passwordio.models.Login;

public class LoginAdapter extends RecyclerView.Adapter<LoginAdapter.ViewHolder> {

    final Login[] logins;

    public LoginAdapter(Login[] logins) {
        this.logins = logins;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView urlTextView;
        final TextView usernameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            urlTextView = itemView.findViewById(R.id.loginItemURL);
            usernameTextView = itemView.findViewById(R.id.loginItemUsername);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.login_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Login login = logins[position];
        holder.urlTextView.setText(login.url.substring(8));
        holder.usernameTextView.setText(login.username);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), LoginItemViewActivity.class);
                intent.putExtra("login", login);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return logins.length;
    }




}
