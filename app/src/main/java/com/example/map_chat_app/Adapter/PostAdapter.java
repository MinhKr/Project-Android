package com.example.map_chat_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.map_chat_app.R;
import com.example.map_chat_app.Model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    Context context;
    List<Post> items;

    public PostAdapter(Context context, List<Post> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(context).inflate(R.layout.item_post_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.AvtPost.setImageResource(items.get(position).getAvtPost());
        holder.UsernamePost.setText(items.get(position).getUsernamePost());
        holder.TimePost.setText(items.get(position).getTimePost());
        holder.LikeIcon.setImageResource(items.get(position).getLikeIcon());
        holder.LikeNum.setText(items.get(position).getLikeNum());
        holder.CmtIcon.setImageResource(items.get(position).getCmtIcon());
        holder.CmtNum.setText(items.get(position).getCmtNum());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}