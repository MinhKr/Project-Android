package com.example.map_chat_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.map_chat_app.Adapter.MessageAdapter;
import com.example.map_chat_app.Model.Chat;
import com.example.map_chat_app.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatFriendActivity extends AppCompatActivity {
    CircleImageView imageView10;
    TextView textView3;
    FirebaseUser fuser;
    DatabaseReference reference;
    Intent intent;

    // xử lý sending chat

    ImageButton btn_send;
    EditText text_send;

    //Sending Chat

    MessageAdapter messageAdapter;
    ArrayList<Chat> list;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        ImageButton imageView6;
        imageView6 = findViewById(R.id.imageView6);
        imageView6.setOnClickListener(v ->
                startActivity(new Intent(ChatFriendActivity.this, FindFriendActivity.class)));

        recyclerView = findViewById(R.id.recycleviewChat);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        textView3 = findViewById(R.id.textView3);
        //xử lý sending chat
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);
        intent = getIntent();
        final String userId = intent.getStringExtra("userId");
        final String username = intent.getStringExtra("username");
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        // Xử lý sending chat với btn_send
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                if(!msg.equals("")){
                    sendMessage(fuser.getUid(),userId,msg);
                }else{
                    Toast.makeText(ChatFriendActivity.this,"Không thể gửi tin nhắn trống", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });


        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Users users = snapshot.getValue(Users.class);

                    if (users != null) {
                        textView3.setText(users.getName());
                        // Tiếp tục xử lý với biến name
                    } else {
                        Log.e("ChatFriendActivity", "User object is null");
                    }
                } else {
                    Log.e("ChatFriendActivity", "User does not exist");
                }
                readMessages(fuser.getUid(),userId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void sendMessage(String sender, String receiver, String message){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap .put("sender", sender);
        hashMap .put("receiver", receiver);
        hashMap .put("message", message);

        reference.child("Chats").push().setValue(hashMap);
    }


    private void readMessages(String myid, String userid){
        list = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid)||
                    chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
                        list.add(chat);
                    }

                    messageAdapter = new MessageAdapter(ChatFriendActivity.this, list);
                    recyclerView.setAdapter(messageAdapter);
                };
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}



