package com.androidcafe.barberconnectsalonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcafe.barberconnectsalonapp.Model.Model_Add_Service;
import com.androidcafe.barberconnectsalonapp.Model.SalonModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class AddServicesActivity extends AppCompatActivity {


    Button submit;
    EditText name,price;
    String service;


    FirebaseFirestore db;

    Gson gson = new Gson();

    TextView service_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_services);
        Paper.init(this);
        db = FirebaseFirestore.getInstance();

        service_name = findViewById(R.id.service_name);
        submit = findViewById(R.id.submit);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);


        Intent intent = getIntent();
        if (intent.hasExtra("service"))
        {
            service = intent.getStringExtra("service");

            service_name.setText(service);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Common.isEmpty(name)||Common.isEmpty(price))
                    {
                        if (Common.isEmpty(name))
                            name.setError("Please enter the service name");
                        if (Common.isEmpty(price))
                            price.setError("Please enter service price");

                    }
                    else
                    {
                        uploadOnFirebase(service);
                    }



                }
            });



        }


    }

    private void uploadOnFirebase(String service) {

        SalonModel salonModel = Paper.book().read("salon_model");

        List<Model_Add_Service> list = new ArrayList<>();
        list.add(new Model_Add_Service(name.getText().toString(),price.getText().toString()));

        db.collection("BarberShops").document(salonModel.getCity()).collection("Shops")
                .document(salonModel.getId()).collection("Services")
                .document("AllServices").update(service,gson.toJson(list)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(AddServicesActivity.this, "Successfully added service", Toast.LENGTH_SHORT).show();

                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(AddServicesActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });






    }
}