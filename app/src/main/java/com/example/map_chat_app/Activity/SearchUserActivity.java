package com.example.map_chat_app.Activity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.map_chat_app.Adapter.SearchUserRecycleAdapter;
import com.example.map_chat_app.R;
import com.example.map_chat_app.Model.User;
import com.example.map_chat_app.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class SearchUserActivity extends AppCompatActivity {
    EditText search_username_input;
    ImageButton search_user_btn;
    RecyclerView recyclerView;
    SearchUserRecycleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        search_username_input = findViewById(R.id.search_username_input);
        search_user_btn = findViewById(R.id.search_user_btn);
        recyclerView = findViewById(R.id.recycleviewChat);

        search_username_input.requestFocus();

        search_user_btn.setOnClickListener(v ->{
            String searchTerm = search_username_input.getText().toString();
            if(searchTerm.isEmpty() || searchTerm.length()<2){
                search_username_input.setError("Invalid Username");
                return;
            }
            setupSearchRecycleView(searchTerm);
        });
    }

    void setupSearchRecycleView(String searchTerm) {
        Query query = FirebaseUtil.allUserCollectionReference()
                .whereGreaterThanOrEqualTo("name",searchTerm);
        Log.d("SearchUserActivity", "Query created: " + query);
        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query,User.class).build();
        adapter = new SearchUserRecycleAdapter(options,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter!=null)
            adapter.startListening();
        else{search_username_input.setError("Adapter is null");
            Log.e("SearchUserActivity", "Adapter is null");}
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter!=null)
            adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter!=null)
            adapter.startListening();
    }
}
