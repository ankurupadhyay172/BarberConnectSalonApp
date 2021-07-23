package com.androidcafe.barberconnectsalonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.androidcafe.barberconnectsalonapp.Fragments.AddServiceFragment;

public class AddSingleServiceActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_single_service);


        getSupportFragmentManager().beginTransaction().add(R.id.main_layout,new AddServiceFragment()).commit();



    }
}