package com.example.map_chat_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FindMeActivity extends AppCompatActivity {

    String nameUser;
    TextView whereRU_txt;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_find_me);
        whereRU_txt = findViewById(R.id.whereru_name);

        nameUser = getIntent().getStringExtra("name");

        whereRU_txt.setText("Bạn đang ở đâu, "+ nameUser + "?");
    }
}