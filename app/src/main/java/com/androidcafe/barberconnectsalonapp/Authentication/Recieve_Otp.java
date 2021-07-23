package com.androidcafe.barberconnectsalonapp.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.autofill.AutofillValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcafe.barberconnectsalonapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.core.View;

import java.util.concurrent.TimeUnit;

public class Recieve_Otp extends AppCompatActivity {

    FirebaseAuth mauth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    PhoneAuthProvider provider;
    EditText editText;
    String mon;
    String id,code;
    PhoneAuthProvider.ForceResendingToken resend_otp;
    Button verifyOtpButton;
    TextView resendotpclick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve__otp);
        editText = findViewById(R.id.OTP);
        verifyOtpButton = findViewById(R.id.sendotp);
        resendotpclick = findViewById(R.id.resendotp);

        mauth = FirebaseAuth.getInstance();
        provider = PhoneAuthProvider.getInstance();

        Intent intent = getIntent();
        mon = intent.getStringExtra("number");

        sendOtp();

        resendotpclick.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                //provider.verifyPhoneNumber("+91"+mon,60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,callbacks,resend_otp);
            }
        });

        verifyOtpButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                verifyotp(editText.getText().toString());

            }
        });

    }

    private void sendOtp() {

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                Toast.makeText(Recieve_Otp.this, "OTP Send Successfully", Toast.LENGTH_SHORT).show();
                id = s;
                resend_otp = forceResendingToken;


            }


            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                code = phoneAuthCredential.getSmsCode();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    editText.autofill(AutofillValue.forText(code));
                }

                editText.setText(code);

                signinWithCredential(phoneAuthCredential);

                if (code!= null)
                {
                    verifyotp(code);
                }

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Toast.makeText(Recieve_Otp.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };


       // provider.verifyPhoneNumber("+91"+mon,60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,callbacks);


    }

    private void verifyotp(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id,code);
        signinWithCredential(credential);

    }

    private void signinWithCredential(PhoneAuthCredential phoneAuthCredential) {

        mauth.signInWithCredential(phoneAuthCredential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                Toast.makeText(Recieve_Otp.this, "Verify Successfully", Toast.LENGTH_SHORT).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(Recieve_Otp.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}