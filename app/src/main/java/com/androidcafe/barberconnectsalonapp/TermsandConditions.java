package com.androidcafe.barberconnectsalonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TermsandConditions extends AppCompatActivity {

    ImageView img_back;

    TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsand_conditions);


        img_back = findViewById(R.id.imgBack);
        tvTitle = findViewById(R.id.tvTitle);

        tvTitle.setText(getString(R.string.tandctitle));

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}