package com.example.map_chat_app;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.map_chat_app.Model.User;

public class FindFriendActivity  extends AppCompatActivity {

    FirebaseRecyclerOptions<User>
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }
}
