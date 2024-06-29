package com.example.map_chat_app;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.map_chat_app.Adapter.FriendAdapter;
import com.example.map_chat_app.Model.Users;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FindFriendActivity  extends AppCompatActivity {


    DatabaseReference database;

    FriendAdapter friendAdapter;
    ArrayList<Users> list;

    RecyclerView recyclerView;

    ImageView toChatimg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recycleviewChat);
        database = FirebaseDatabase.getInstance().getReference("Users");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toChatimg = findViewById(R.id.toMain);

        String username = getIntent().getStringExtra("username");
        String id = getIntent().getStringExtra("userId");

        list = new ArrayList<>();
        friendAdapter = new FriendAdapter(this,list);
        recyclerView.setAdapter(friendAdapter);

        //Quay về Main
        toChatimg.setOnClickListener(v -> {
            Intent intent = new Intent(FindFriendActivity.this, MainActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("userId", id);
            startActivity(intent);
        });

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Users users = dataSnapshot.getValue(Users.class);
                    list.add(users);

                }

                friendAdapter.notifyDataSetChanged();

//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}
