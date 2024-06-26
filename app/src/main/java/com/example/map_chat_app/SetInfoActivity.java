package com.example.map_chat_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class SetInfoActivity extends AppCompatActivity {
    EditText nameInput;
    Button nextBtn ;
    String gender;

    ImageButton maleIB , fermaleIB , otherGenderIB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set_info);
        nameInput = findViewById(R.id.input_txt_setName);
        nextBtn = findViewById(R.id.next_btn);
        maleIB = findViewById(R.id.male_btn);
        fermaleIB = findViewById(R.id.fermale_btn);
        otherGenderIB = findViewById(R.id.othergender_btn);

        String userId = getIntent().getStringExtra("userId");
        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        String phoneNumberNoCountryCode = getIntent().getStringExtra("phoneNumberNoCountryCode").replace(" ", "");
        String password = getIntent().getStringExtra("password");

        maleIB.setOnClickListener(v -> {
            gender = "Nam";
            maleIB.setBackgroundResource(R.drawable.gender_bg_button_clicked);
            fermaleIB.setBackgroundResource(R.drawable.gender_bg_btn);
            otherGenderIB.setBackgroundResource(R.drawable.gender_bg_btn);
        });

        fermaleIB.setOnClickListener(v -> {
            gender = "Nữ";
            fermaleIB.setBackgroundResource(R.drawable.gender_bg_button_clicked);
            maleIB.setBackgroundResource(R.drawable.gender_bg_btn);
            otherGenderIB.setBackgroundResource(R.drawable.gender_bg_btn);
        });

        otherGenderIB.setOnClickListener(v -> {
            gender = "Khác";
            otherGenderIB.setBackgroundResource(R.drawable.gender_bg_button_clicked);
            maleIB.setBackgroundResource(R.drawable.gender_bg_btn);
            fermaleIB.setBackgroundResource(R.drawable.gender_bg_btn);
        });

        nextBtn.setOnClickListener(v -> {
            // Get the user input
            String name = nameInput.getText().toString();

            // Create a new User object
            User user = new User(userId, phoneNumber, password, name, gender);

            // Save the user to Firebase
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Users")
                    .document(userId)
                    .set(user)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Create a new UserSQLite object
                            UserSQLite userSQLite = new UserSQLite(phoneNumberNoCountryCode, password);

                            // Get an instance of DBHelper
                            DBHelper dbHelper = new DBHelper(SetInfoActivity.this);

                            // Save the user to SQLite
                            dbHelper.addUser(userSQLite);

                            Intent intent = new Intent(SetInfoActivity.this, FindMeActivity.class);
                            intent.putExtra("name", name);
                            startActivity(intent);
                            Toast.makeText(SetInfoActivity.this, "Thông tin đã được lưu", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(SetInfoActivity.this, "Lưu thông tin thất bại", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Log the error
                        Log.e("SetInfoActivity", "Failed to save user to Firestore: ", e);
                    });
        });
    }
}