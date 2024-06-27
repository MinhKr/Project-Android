package com.example.map_chat_app;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText passwordInput, confirmPasswordInput;
    Button changeBtn;
    String phoneNumber , phoneNumberNoCountryCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);

        passwordInput = findViewById(R.id.input_txt_password);
        confirmPasswordInput = findViewById(R.id.confirmInput_txt_password);
        changeBtn = findViewById(R.id.btn_changePassword);

        phoneNumber = getIntent().getStringExtra("phoneNumber");
        phoneNumberNoCountryCode = getIntent().getStringExtra("phoneNumberNoCCP");

        setupPasswordVisibilityToggle(passwordInput);
        setupPasswordVisibilityToggle(confirmPasswordInput);



        changeBtn.setOnClickListener(v -> {
            String password = passwordInput.getText().toString();
            String confirmPassword = confirmPasswordInput.getText().toString();

            if(CheckPassword(password , confirmPassword)){
                updatePasswordInSQLite(phoneNumberNoCountryCode, password);
                updatePasswordInFirestore(phoneNumber, password);
                Toast.makeText(ResetPasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }


        });


    }

    // Ẩn hiện mật khẩu
    @SuppressLint("ClickableViewAccessibility")
    private void setupPasswordVisibilityToggle(EditText editText) {
        editText.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (editText.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                        editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                    editText.setSelection(editText.getText().length());
                    return true;
                }
            }
            return false;
        });
    }

    // Cập nhật mật khẩu mới vào Firestore
    private void updatePasswordInFirestore(String phoneNumber, String newPassword) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .whereEqualTo("phoneNumber", phoneNumber)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        String userId = task.getResult().getDocuments().get(0).getId();
                        db.collection("Users").document(userId)
                                .update("password", newPassword);
                    }
                });
    }

    // Cập nhật mật khẩu mới vào SQLite
    private void updatePasswordInSQLite(String phoneNumber, String newPassword) {
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.updatePassword(phoneNumber, newPassword);
    }


    // Kiểm tra mật khẩu
    private boolean CheckPassword(String password , String confirmPassword){

        // Kiểm tra mật khẩu không được để trống
        if (password.isEmpty()) {
            passwordInput.setError("Mật khẩu không được để trống");
            return false;
        }
        if (confirmPassword.isEmpty()) {
            confirmPasswordInput.setError("Mật khẩu không được để trống");
            return false;
        }

        // Kiểm tra mật khẩu phải có ít nhất 6 ký tự
        if (password.length() < 6) {
            passwordInput.setError("Mật khẩu phải có ít nhất 6 ký tự");
            return false;
        }

        // Kiểm tra mật khẩu có trùng khớp không
        if (!password.equals(confirmPassword)) {
            confirmPasswordInput.setError("Mật khẩu không trùng khớp");
            return false;
        }
        return true;
    }
}