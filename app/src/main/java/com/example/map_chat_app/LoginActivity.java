package com.example.map_chat_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText phoneNumberInput, passwordInput;
    Button btn_login;
    TextView toRegisterActivity;
    FirebaseAuth mAuth;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        btn_login = (Button)findViewById(R.id.btn_login);
        toRegisterActivity = findViewById((R.id.createAcc_txt));
        phoneNumberInput = findViewById(R.id.input_txt_phonenumber);
        passwordInput = findViewById(R.id.input_txt_password);
        mAuth = FirebaseAuth.getInstance();


        //Ẩn hiện mật khẩu
        passwordInput.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;

            if(event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (passwordInput.getRight() - passwordInput.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    // toggle password visibility
                    if (passwordInput.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                        // Show password
                        passwordInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        // Hide password
                        passwordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                    // Keep the cursor at the end of the text
                    passwordInput.setSelection(passwordInput.getText().length());
                    return true;
                }
            }
            return false;
        });

        //Chưa có tài khoản
        toRegisterActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        //Đã có tài khoản
        btn_login.setOnClickListener((v) -> {
            String phoneNumber = phoneNumberInput.getText().toString().replace(" ", "");
            String password = passwordInput.getText().toString();

            DBHelper dbHelper = new DBHelper(this);
            UserSQLite user = dbHelper.getUser(phoneNumber);

            if (user == null) {
                // Phone Number
                Toast.makeText(this, "Số điện thoại không tồn tại", Toast.LENGTH_SHORT).show();
            } else if (!user.getPassword().equals(password)) {
                // Password
                Toast.makeText(this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            } else {
                // Thành công
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}