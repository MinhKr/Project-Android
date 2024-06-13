package com.example.map_chat_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyActivity extends AppCompatActivity {

    EditText otpInput;

    TextView resendOtpTxt;
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    Button verifyBtn;
    FirebaseAuth mAuth;

    String verificationId, phoneNumber, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify);
        otpInput = findViewById(R.id.input_txt_otp);
        verifyBtn = findViewById(R.id.verify_btn);
        resendOtpTxt = findViewById(R.id.resend_otp);

        mAuth = FirebaseAuth.getInstance();

        verificationId = getIntent().getStringExtra("verificationId");
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        password = getIntent().getStringExtra("password");

        SendOTP(phoneNumber , false);

        verifyBtn.setOnClickListener(v -> {
            String otp = otpInput.getText().toString();
            if(otp.isEmpty()){
                Toast.makeText(VerifyActivity.this,"Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show();
            }else{
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
                signIn(credential);
            }
        });

        resendOtpTxt.setOnClickListener(v -> {
            SendOTP(phoneNumber , true);
        });
    }

    void SendOTP(String phoneNumber , boolean isResend){
        PhoneAuthOptions.Builder optionsBuilder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signIn(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VerifyActivity.this,"Xác minh thất bại", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                resendingToken = forceResendingToken;
                                Toast.makeText(VerifyActivity.this,"Mã OTP đã được gửi", Toast.LENGTH_SHORT).show();
                            }
                        });          // OnVerificationStateChangedCallbacks

        if (isResend) {
            optionsBuilder.setForceResendingToken(resendingToken); // Resend the OTP
        }

        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build());
    }

    void signIn(PhoneAuthCredential phoneAuthCredential){
        //Login
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success
                        FirebaseUser user = task.getResult().getUser();
                        Intent intent = new Intent(VerifyActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Sign in failed
                        Toast.makeText(VerifyActivity.this,"Xác minh thất bại", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}