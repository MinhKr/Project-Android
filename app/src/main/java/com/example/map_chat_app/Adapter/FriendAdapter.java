package com.example.map_chat_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.map_chat_app.R;
import com.example.map_chat_app.Model.Friend; // Import Friend từ package chính xác

import java.util.ArrayList;
import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendViewHolder> {

    Context context;
    List<Friend> items;

    public FriendAdapter(Context context, List<Friend> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendViewHolder(LayoutInflater.from(context).inflate(R.layout.item_friend_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        holder.textViewName.setText(items.get(position).getName());
        holder.imageViewAvatar.setImageResource(items.get(position).getAvatarResId());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}