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
import androidx.recyclerview.widget.RecyclerView;

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


import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {
    CircleImageView profile_image;
    TextView username;
    FirebaseUser fuser;
    DatabaseReference reference;
    Intent intent;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        RecyclerView recyclerView = findViewById(R.id.recycleviewChat);
//        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);

        intent = getIntent();
        String userid = intent.getStringExtra("userid");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (userid != null) {
            reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);
        } else {
            // Xử lý trường hợp null, có thể là log lỗi hoặc đưa ra thông báo cho người dùng
            Log.e("MessageActivity", "pathString is null");
        }


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                username.setText(user.getName());
//                profile_image.setImageResource(R.mipmap.ic_launcher);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
