package com.example.map_chat_app;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.os.Bundle;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {

    TextView text_existAccout;
    EditText phoneInput ,  inputPassword , reInputPassword;
    CountryCodePicker countryCodePicker;
    Button registerBtn;

    FirebaseAuth mAuth;

    String passWord , rePassword , phoneNumber;
    String verificationId;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        phoneInput = findViewById(R.id.input_txt_phonenumber);
        countryCodePicker = findViewById(R.id.ccp_register);
        registerBtn = findViewById(R.id.register_btn);
        text_existAccout = (TextView)findViewById(R.id.txt_exist_acc);
        inputPassword = findViewById(R.id.input_txt_password);
        reInputPassword = findViewById(R.id.reInput_txt_password);


        //Quay trở lại đăng nhập nếu đã có tài khoản
        text_existAccout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Register has already have", "TextView clicked!");
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


        //Ấn nút Đăng kí
        registerBtn.setOnClickListener((v) -> {
            if (CheckPhoneNumber() && CheckPassword()) {
                Intent intent = new Intent(RegisterActivity.this, VerifyActivity.class);
                intent.putExtra("phoneNumber", countryCodePicker.getFullNumberWithPlus());
                startActivity(intent);
            }
        });
    }



    private boolean CheckPhoneNumber() {
        countryCodePicker.registerCarrierNumberEditText(phoneInput);
        // Kiểm tra số điện thoại có hợp lệ không
        if (!countryCodePicker.isValidFullNumber()) {
            phoneInput.setError("Số điện thoại không hợp lệ");
            return false;
        }
        return true;
    }

    private boolean CheckPassword() {
        passWord = inputPassword.getText().toString();
        rePassword = reInputPassword.getText().toString();
        // Kiểm tra mật khẩu không được để trống
        if (passWord.isEmpty()) {
            inputPassword.setError("Mật khẩu không được để trống");
            return false;
        }
        if (rePassword.isEmpty()) {
            reInputPassword.setError("Mật khẩu không được để trống");
            return false;
        }

        // Kiểm tra mật khẩu phải có ít nhất 6 ký tự
        if (passWord.length() < 6) {
            inputPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            return false;
        }

        // Kiểm tra mật khẩu có trùng khớp không
        if (!passWord.equals(rePassword)) {
            reInputPassword.setError("Mật khẩu không trùng khớp");
            return false;
        }
        return true;
    }

}
