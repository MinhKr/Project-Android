package com.example.map_chat_app.Adapter;

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

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

    private List<Friend> friendList;
    private List<Friend> friendListFull;

    public FriendAdapter(List<Friend> friendList) {
        this.friendList = friendList;
        this.friendListFull = new ArrayList<>(friendList);
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_friend_list, parent, false);
        return new FriendViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        Friend friend = friendList.get(position);
        holder.imageViewAvatar.setImageResource(friend.getAvatarResId());
        holder.textViewName.setText(friend.getName());
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public void filter(String text) {
        friendList.clear();
        if (text.isEmpty()) {
            friendList.addAll(friendListFull);
        } else {
            text = text.toLowerCase();
            for (Friend item : friendListFull) {
                if (item.getName().toLowerCase().contains(text)) {
                    friendList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class FriendViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewAvatar;
        TextView textViewName;

        FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar);
            textViewName = itemView.findViewById(R.id.textViewName);
        }
    }
}