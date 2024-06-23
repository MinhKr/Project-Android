package com.example.map_chat_app.Activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.map_chat_app.Adapter.FriendAdapter;
import com.example.map_chat_app.Model.Friend;
import com.example.map_chat_app.R;

import java.util.ArrayList;
import java.util.List;

public class FriendSearch_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle SavedInstanceState) {

        super.onCreate(SavedInstanceState);
        setContentView(R.layout.friend_recycle_view);

        RecyclerView recyclerView = findViewById(R.id.recycleviewFriendSearch);

        List<Friend> items = new ArrayList<Friend>();
        items.add(new Friend("Nguyen Khanh Linh",R.drawable.a));
        items.add(new Friend("Ariana",R.drawable.b));
        items.add(new Friend("Gorgious",R.drawable.c));
        items.add(new Friend("Taylor",R.drawable.e));
        items.add(new Friend("Athena",R.drawable.f));
        items.add(new Friend("Krina",R.drawable.g));
        items.add(new Friend("Doris",R.drawable.h));
        items.add(new Friend("Kristen",R.drawable.i));
        items.add(new Friend("Harry",R.drawable.j));
        items.add(new Friend("Anne",R.drawable.k));
        items.add(new Friend("Robert",R.drawable.l));
        items.add(new Friend("Harley",R.drawable.m));
        items.add(new Friend("Nguyen Khanh Linh",R.drawable.a));
        items.add(new Friend("Ariana",R.drawable.b));
        items.add(new Friend("Gorgious",R.drawable.c));
        items.add(new Friend("Taylor",R.drawable.e));
        items.add(new Friend("Athena",R.drawable.f));
        items.add(new Friend("Krina",R.drawable.g));
        items.add(new Friend("Doris",R.drawable.h));
        items.add(new Friend("Kristen",R.drawable.i));
        items.add(new Friend("Harry",R.drawable.j));
        items.add(new Friend("Anne",R.drawable.k));
        items.add(new Friend("Robert",R.drawable.l));
        items.add(new Friend("Harley",R.drawable.m));


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FriendAdapter(getApplicationContext(),items));

    }
}
