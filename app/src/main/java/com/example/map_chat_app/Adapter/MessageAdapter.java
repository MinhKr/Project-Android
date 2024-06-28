package com.example.map_chat_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.map_chat_app.ChatFriendActivity;
import com.example.map_chat_app.Model.Chat;
import com.example.map_chat_app.Model.Users;
import com.example.map_chat_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.FindFriendViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;


    Context context;
    ArrayList<Chat> list;

    FirebaseUser fuser;

    public MessageAdapter(Context context, ArrayList<Chat> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MessageAdapter.FindFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_TYPE_RIGHT){
            View v = LayoutInflater.from(context).inflate(R.layout.chat_item_right,parent,false);
            return new MessageAdapter.FindFriendViewHolder(v);
        }else{
            View v = LayoutInflater.from(context).inflate(R.layout.chat_item_left,parent,false);
            return new MessageAdapter.FindFriendViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.FindFriendViewHolder holder, int position) {
        Chat chat = list.get(position);
        holder.show_message.setText(chat.getMessage());

    }



    public static class FindFriendViewHolder extends RecyclerView.ViewHolder {

        TextView show_message;
        public FindFriendViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (list.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}
