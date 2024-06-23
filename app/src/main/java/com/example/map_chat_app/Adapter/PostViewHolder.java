package com.example.map_chat_app.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.map_chat_app.R;

import java.text.BreakIterator;

public class PostViewHolder extends RecyclerView.ViewHolder {

    ImageView AvtPost;
    TextView UsernamePost;
    TextView TimePost;
    ImageView LikeIcon;
    TextView LikeNum;
    ImageView CmtIcon;
    TextView CmtNum;
    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        AvtPost = itemView.findViewById(R.id.avt_post);
        UsernamePost = itemView.findViewById(R.id.username_post);
        TimePost = itemView.findViewById(R.id.time_post);
        LikeIcon = itemView.findViewById(R.id.like_icon);
        LikeNum = itemView.findViewById(R.id.like_num);
        CmtIcon = itemView.findViewById(R.id.cmt_icon);
        CmtNum = itemView.findViewById(R.id.cmt_num);
    }
}