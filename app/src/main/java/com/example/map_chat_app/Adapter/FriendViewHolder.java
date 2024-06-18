package com.example.map_chat_app.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.map_chat_app.R;

public class FriendViewHolder extends RecyclerView.ViewHolder {

    ImageView imageViewAvatar;
    TextView textViewName;
    public FriendViewHolder(@NonNull View itemView) {
        super(itemView);
        imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar);
        textViewName = itemView.findViewById(R.id.textViewName);
    }
}
