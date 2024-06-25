package com.example.map_chat_app.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.DatabaseErrorHandler;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.map_chat_app.Adapter.ChatAdapter;
import com.example.map_chat_app.Model.Friend;
import com.example.map_chat_app.Model.User;
import com.example.map_chat_app.R;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {
    CircleImageView profile_image;
    TextView username;
    FirebaseUser fuser;
    DatabaseReference reference;
    Intent intent;
    ChatAdapter chatAdapter;
    List<User> mUsers;
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView.setHasFixedSize(true);
        recyclerView = findViewById(R.id.recycleviewChat);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
//        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);



        intent = getIntent();
        String userid = intent.getStringExtra("userid");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (userid != null) {
            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        } else {
            // Xử lý trường hợp null, có thể là log lỗi hoặc đưa ra thông báo cho người dùng
            Log.e("MessageActivity", "UserID is null");
        }


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                username.setText(user.getName());
                if (user != null) {
                    username.setText(user.getName());
                } else {
                    Log.e("MessageActivity", "User is null");
                }
//                profile_image.setImageResource(R.mipmap.ic_launcher);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MessageActivity", "Failed to read value.", error.toException());
            }
        });
        readMessages();
    }

    private void readMessages() {
        mUsers = new ArrayList<>();

        // Đọc dữ liệu các user từ Firebase Database và đưa vào RecyclerView
        DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference("Users");
        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    assert user != null;
                    assert fuser != null;
                    if (!user.getUserId().equals(fuser.getUid())) {
                        mUsers.add(user);
                    }
                }

                chatAdapter = new ChatAdapter(MessageActivity.this, mUsers);
                recyclerView.setAdapter(chatAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MessageActivity", "Failed to read value.", databaseError.toException());
            }
        });
    }

}
