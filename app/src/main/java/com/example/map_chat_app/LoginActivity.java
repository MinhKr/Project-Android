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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class LoginActivity extends AppCompatActivity {

    EditText phoneNumberInput, passwordInput;
    Button btn_login;
    TextView toRegisterActivity, forgotPassword;
    FirebaseAuth mAuth;
    CountryCodePicker countryCodePicker;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        toRegisterActivity = findViewById(R.id.createAcc_txt);
        phoneNumberInput = findViewById(R.id.input_txt_phonenumber);
        passwordInput = findViewById(R.id.input_txt_password);
        forgotPassword = findViewById(R.id.forgotPass_txt);
        countryCodePicker = findViewById(R.id.ccp_login);
        mAuth = FirebaseAuth.getInstance();

        countryCodePicker.registerCarrierNumberEditText(phoneNumberInput);

        //Ẩn hiện mật khẩu
        passwordInput.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (passwordInput.getRight() - passwordInput.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
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

        // Chưa có tài khoản
        toRegisterActivity.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Đã có tài khoản
        btn_login.setOnClickListener(v -> {
            String phoneNumber = phoneNumberInput.getText().toString().replace(" ", "");
            String password = passwordInput.getText().toString();

            String fullPhoneNumber = countryCodePicker.getFullNumberWithPlus();

            // Giả sử bạn sử dụng SQLite để lưu trữ người dùng cục bộ
            DBHelper dbHelper = new DBHelper(this);
            UserSQLite user = dbHelper.getUser(phoneNumber);

            if (user == null) {
                // Số điện thoại không tồn tại
                Toast.makeText(this, "Số điện thoại không tồn tại", Toast.LENGTH_SHORT).show();
            } else if (!user.getPassword().equals(password)) {
                // Mật khẩu không đúng
                Toast.makeText(this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            } else {
                // Đăng nhập thành công, tìm username từ Firebase
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
                usersRef.orderByChild("phoneNumber").equalTo(fullPhoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                String username = userSnapshot.child("name").getValue(String.class);
                                String id = userSnapshot.getKey();// Lấy username
                                // Chuyển sang MainActivity với username
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("username", username);
                                intent.putExtra("userId", id);
                                startActivity(intent);
                                finish(); // Kết thúc LoginActivity
                                break; // Dừng lại sau khi tìm thấy username đầu tiên khớp
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Không tìm thấy người dùng với số điện thoại này.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Firebase", "Failed to read value.", databaseError.toException());
                    }
                });
            }
        });

        // Quên mật khẩu
        forgotPassword.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }
}
