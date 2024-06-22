package com.example.map_chat_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FindMeActivity extends AppCompatActivity {

    String nameUser;
    TextView whereRU_txt;

    Button nextBtn;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_find_me);
        whereRU_txt = findViewById(R.id.whereru_name);
        nextBtn = findViewById(R.id.next_btn);

        nameUser = getIntent().getStringExtra("name");

        whereRU_txt.setText("Bạn đang ở đâu, "+ nameUser + "?");

        nextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(FindMeActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}