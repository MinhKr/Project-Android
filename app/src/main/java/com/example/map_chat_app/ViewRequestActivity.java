 package com.example.map_chat_app;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ViewRequestActivity extends AppCompatActivity {

    DatabaseReference mUserRef, requestRef,friendRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String profileImgUrl, Username, Gender;

    ImageView profileImg;
    TextView username, gender;
    Button sendrq, declinerq;
    String CurrentSate ="nothing_happen";

    @Override
    protected void onCreate(Bundle saveInstanceStace){
        super.onCreate(saveInstanceStace);
        setContentView(R.layout.activity_view_request);
        final String userID = getIntent().getStringExtra("userId");
        Toast.makeText(this, ""+userID, Toast.LENGTH_SHORT).show();

        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        requestRef = FirebaseDatabase.getInstance().getReference().child("Request");
        friendRef = FirebaseDatabase.getInstance().getReference().child("friend");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        declinerq = findViewById(R.id.declinerq);
        sendrq = findViewById(R.id.sendrq);


//        profileImg = findViewById(R.id.profileImg);
        username = findViewById(R.id.username);
        gender = findViewById(R.id.gender);

        LoadUser();

        sendrq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendAction(userID);
            }
        });

        CheckUserExistance(userID);
        declinerq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Unfriend(userID);
            }
        });
    }

    private void Unfriend(String userID) {
        if (CurrentSate.equals("friend")) {
            friendRef.child(mUser.getUid()).child(userID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        requestRef.child(userID).child(mUser.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ViewRequestActivity.this, "You are Unfriend", Toast.LENGTH_SHORT).show();
                                    CurrentSate = "nothing_happen";
                                    sendrq.setText("Send Friend Request");
                                    declinerq.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                }
            });
        }
        if (CurrentSate.equals("he_sent_pending")){
            HashMap hashMap = new HashMap();
            hashMap.put("status","decline");
            requestRef.child(userID).child(mUser.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ViewRequestActivity.this, "You Decline Friend", Toast.LENGTH_SHORT).show();
                        CurrentSate="he_sent_decline";
                        sendrq.setVisibility(View.GONE);
                        declinerq.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
    private void CheckUserExistance(String userID) {
        friendRef.child(mUser.getUid()).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    CurrentSate="friend";
                    sendrq.setText("Messeger");
                    declinerq.setText("Unfriend");
                    declinerq.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        friendRef.child(userID).child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    CurrentSate="friend";
                    sendrq.setText("Messeger");
                    declinerq.setText("Unfriend");
                    declinerq.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        requestRef.child(mUser.getUid()).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("status").getValue().toString().equals("pending")){
                        CurrentSate ="I_sent_pending";
                        sendrq.setText("Cancel Friend Request");
                        declinerq.setVisibility(View.GONE);
                    }
                    if(snapshot.child("status").getValue().toString().equals("decline")){
                        CurrentSate ="I_sent_decline";
                        sendrq.setText("Cancel Friend Request");
                        declinerq.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        requestRef.child(userID).child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("status").getValue().toString().equals("pending")){
                        CurrentSate="he_sent_pending";
                        sendrq.setText("Accept Friend Request");
                        declinerq.setText("Decline Friend");
                        declinerq.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if(CurrentSate.equals("nothing_happen")){
            CurrentSate="nothing_happen";
            sendrq.setText("Send Friend Request");
            declinerq.setVisibility(View.GONE);
        }
    }

    private void SendAction(String userID) {
        //Người lạ
        if (CurrentSate.equals("nothing_happen")){
            HashMap hashMap = new HashMap();
            hashMap.put("satatus", "pending");
            requestRef.child(mUser.getUid()).child(userID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ViewRequestActivity.this,"You have sent Friend Request",Toast.LENGTH_SHORT).show();
                        declinerq.setVisibility(View.GONE);
                        CurrentSate="I_sent_pending";
                        sendrq.setText("Cancel Friend Request");
                    }
                    else {
                        Toast.makeText(ViewRequestActivity.this,""+task.getException().toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        //Hủy yêu cầu
        if (CurrentSate.equals("I_sent_pending")|| CurrentSate.equals("I_sent_decline")){
            requestRef.child(mUser.getUid()).child(userID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ViewRequestActivity.this, "You have cancelled Friend Request", Toast.LENGTH_SHORT).show();
                        CurrentSate="nothingg_happen";
                        sendrq.setText("Send Friend Request");
                        declinerq.setVisibility(View.GONE);
                    }
                    else {
                        Toast.makeText(ViewRequestActivity.this,""+task.getException().toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if (CurrentSate.equals("he_sent_pending")){
            requestRef.child(userID).child(mUser.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        HashMap hashMap = new HashMap();
                        hashMap.put("status", "friend");
                        hashMap.put("username", username);
                        friendRef.child(mUser.getUid()).child(userID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful()){
                                    friendRef.child(userID).child(mUser.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener(){
                                        @Override
                                        public void onComplete(@NonNull Task task) {
                                            Toast.makeText(ViewRequestActivity.this,"You added friend", Toast.LENGTH_SHORT).show();
                                            CurrentSate="friend";
                                            sendrq.setText("Messeger");
                                            declinerq.setText("Unfriend");
                                            declinerq.setVisibility(View.VISIBLE);

                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
        }
        if(CurrentSate.equals("friend")){

        }
    }

    private void LoadUser(){

        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
//                    profileImgUrl=snapshot.child("phoneNumber").getValue().toString();
                    Username=snapshot.child("name").getValue().toString();
                    Gender=snapshot.child("gender").getValue().toString();
//                    profileImg=snapshot.child("Location").getValue().toString();

//                    profileImg.setImageURI(Uri.parse(profileImgUrl));
                    username.setText(Username);
                    gender.setText(Gender);

                }
                else {
                    Toast.makeText(ViewRequestActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ViewRequestActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}