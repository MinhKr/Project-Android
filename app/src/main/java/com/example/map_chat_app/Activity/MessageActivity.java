package com.example.map_chat_app.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.map_chat_app.Adapter.ChatAdapter;
import com.example.map_chat_app.Model.User;
import com.example.map_chat_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MessageActivity extends AppCompatActivity {
    TextView username;
    FirebaseUser fuser;
    DocumentReference reference;
    Intent intent;
    ChatAdapter chatAdapter;
    List<User> mUsers;
    RecyclerView recyclerView;
    FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = findViewById(R.id.recycleviewChat);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        username = findViewById(R.id.username);

        db = FirebaseFirestore.getInstance();
        intent = getIntent();
        String userid = intent.getStringExtra("userid");
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        if (fuser != null) {
            String userID = fuser.getUid();
            Log.d("MessageActivity", "User ID: " + userID);

            reference = db.collection("Users").document(userid);

            if (reference != null) {
                reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("MessageActivity", "Database error: " + error.getMessage());
                            return;
                        }
                        if (snapshot != null && snapshot.exists()) {
                            User user = snapshot.toObject(User.class);
                            if (user != null) {
                                username.setText(user.getName());
                            } else {
                                Log.e("MessageActivity", "User data is null");
                            }
                        }
                    }
                });
            } else {
                Log.e("MessageActivity", "Database reference is null");
            }
        } else {
            Log.e("MessageActivity", "User is not authenticated");
            Toast.makeText(this, "User is not authenticated. Please login again.", Toast.LENGTH_SHORT).show();
            finish();
        }

        mUsers = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, mUsers);
        if (recyclerView != null) {
            recyclerView.setAdapter(chatAdapter);
        }
        // Load users into RecyclerView
        loadUsers();
    }

    private void loadUsers() {
        db.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent( QuerySnapshot value,  FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("MessageActivity", "Failed to load users: " + error.getMessage());
                    return;
                }
                if (value != null) {
                    mUsers.clear();
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        User user = snapshot.toObject(User.class);
                        mUsers.add(user);
                    }
                    chatAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
