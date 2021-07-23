package com.androidcafe.barberconnectsalonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.androidcafe.barberconnectsalonapp.Authentication.Register_MobileNo;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {




    SharedPreferences sharedPreferences;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);




        auth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences(Common.SP_LOGIN,MODE_PRIVATE);









        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {

            @Override

            public void run() {


                if (auth.getCurrentUser()!=null)
                {

                    //Intent i = new Intent(SplashScreenActivity.this, GetCurrentLocation.class);
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    //Intent i = new Intent(SplashScreenActivity.this, SalonLocationsActivity.class);
                    startActivity(i);finish();

                }
                else
                {
                    //Intent i = new Intent(SplashScreenActivity.this, GetCurrentLocation.class);
                    Intent i = new Intent(SplashScreenActivity.this, Register_MobileNo.class);
                    //Intent i = new Intent(SplashScreenActivity.this, SalonLocationsActivity.class);
                    startActivity(i);finish();

                }
            }

        }, 3*1000); // wait for 3 seconds


    }
}