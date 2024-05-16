package com.example.map_chat_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hbb20.CountryCodePicker;

public class RegisterActivity extends AppCompatActivity {

    private TextView text_existAccout;
    EditText phoneInput;
    CountryCodePicker countryCodePicker;
    Button registerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        phoneInput = findViewById(R.id.input_txt_phonenumber);
        countryCodePicker = findViewById(R.id.ccp_register);
        registerBtn = findViewById(R.id.register_btn);
        text_existAccout = (TextView)findViewById(R.id.txt_exist_acc);
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        //Quay trở lại đăng nhập nếu đã có tài khoản
        text_existAccout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Register has already have", "TextView clicked!");
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        //Đăng kí với số điện thoại
        //Kiểm tra số điện thoại có hợp lệ không
        countryCodePicker.registerCarrierNumberEditText(phoneInput);
        registerBtn.setOnClickListener((v) -> {
            if(!countryCodePicker.isValidFullNumber()){
                phoneInput.setError("Phone number not valid");
                return;
            }
            Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
            startActivity(intent);
        });
    }
}