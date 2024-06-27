package com.example.map_chat_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hbb20.CountryCodePicker;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextView backLoginActivity;
    Button verifyBtn;
    EditText phoneNumberInput;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        backLoginActivity = findViewById(R.id.back_txt);
        verifyBtn = findViewById(R.id.btn_sendOTP);
        phoneNumberInput = findViewById(R.id.input_txt_phonenumber);
        countryCodePicker = findViewById(R.id.ccp_login);

        countryCodePicker.registerCarrierNumberEditText(phoneNumberInput);

        backLoginActivity.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        verifyBtn.setOnClickListener(v -> {
            String phoneNumber = countryCodePicker.getFullNumberWithPlus();
            String phoneNumberNoCCP = phoneNumberInput.getText().toString().replace(" ", "");

            DBHelper dbHelper = new DBHelper(this);
            UserSQLite user = dbHelper.getUser(phoneNumberNoCCP);
            if (user != null) {
                // Số điện thoại tồn tại trong SQLite, chuyển đến VerifyFPActivity
                Intent intent = new Intent(ForgotPasswordActivity.this, VerifyFPActivity.class);
                // Truyền phoneNumber qua VerifyFPActivity
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("phoneNumberNoCCP", phoneNumberNoCCP);
                startActivity(intent);
            } else {
                // Số điện thoại không tồn tại trong SQLite
                Toast.makeText(this, "Số điện thoại không tồn tại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}