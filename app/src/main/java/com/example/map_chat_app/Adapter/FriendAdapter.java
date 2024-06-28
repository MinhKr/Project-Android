package com.example.map_chat_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.map_chat_app.ChatFriendActivity;
import com.example.map_chat_app.Model.Users;
import com.example.map_chat_app.R;
import com.example.map_chat_app.Model.Friend; // Import Friend từ package chính xác

import java.util.ArrayList;
import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FindFriendViewHolder> {

    Context context;
    ArrayList<Users> list;


    public FriendAdapter(Context context, ArrayList<Users> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FindFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_chat_list,parent,false);
        return new FindFriendViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FindFriendViewHolder holder, int position) {
        Users users = list.get(position);
        holder.name.setText(users.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatFriendActivity.class);
                intent.putExtra("userId", users.getUserId());
                context.startActivity(intent);

            }
        });
    }



    public static class FindFriendViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        public FindFriendViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.username);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}