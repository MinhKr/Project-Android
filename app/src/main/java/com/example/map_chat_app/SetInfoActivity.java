package com.example.map_chat_app;

import android.content.Intent;
import android.os.Bundle;
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

        maleIB.setOnClickListener(v -> gender = "Nam");
        fermaleIB.setOnClickListener(v -> gender = "Nữ");
        otherGenderIB.setOnClickListener(v -> gender = "Khác");

        nextBtn.setOnClickListener(v -> {
            // Get the user input
            String name = nameInput.getText().toString();
            // Get other user input as needed

            // Create a new User object
            User user = new User(userId, phoneNumber, password, name, gender);

            // Save the user to Firebase
            FirebaseDatabase.getInstance().getReference("Users")
                    .child(userId)
                    .setValue(user)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Create a new UserSQLite object
                            UserSQLite userSQLite = new UserSQLite(phoneNumberNoCountryCode, password);

                            // Get an instance of DBHelper
                            DBHelper dbHelper = new DBHelper(SetInfoActivity.this);

                            // Save the user to SQLite
                            dbHelper.addUser(userSQLite);

                            startActivity(new Intent(SetInfoActivity.this, FindMeActivity.class));
                            Toast.makeText(SetInfoActivity.this, "Thông tin đã được lưu", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(SetInfoActivity.this, "Lưu thông tin thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}