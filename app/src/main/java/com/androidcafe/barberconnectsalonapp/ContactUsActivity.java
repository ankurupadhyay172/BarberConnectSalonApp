package com.androidcafe.barberconnectsalonapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcafe.barberconnectsalonapp.Model.SalonModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.paperdb.Paper;

public class ContactUsActivity extends AppCompatActivity {

    ImageView imgBack;
    TextView tvTitle,call;


    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser user;

    EditText title,message;
    Gson gson = new Gson();
    Button btnNext;
    ProgressBar btnProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);


        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        user = auth.getCurrentUser();


        call = findViewById(R.id.call);

        title = findViewById(R.id.title);
        message = findViewById(R.id.message);
        btnNext = findViewById(R.id.btnNext);
        btnProgress = findViewById(R.id.btnProgress);

        /*-------Status Color Code To Change--------*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.green_15d1a4));
        }
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Contact us");




        call.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                {
                    String contact = "+919636964321"; // use country code with your phone number
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact));
                    startActivity(intent);
                }
                else
                {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},101);
                }
            }
        });



            SalonModel salonModel = Paper.book().read("salon_model");


        btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (Common.isEmpty(title)||Common.isEmpty(message))
                            {

                                title.setError("Please enter title");
                                message.setError("Please enter some message");



                            }
                            else
                            {
                                btnNext.setVisibility(View.GONE);
                                btnProgress.setVisibility(View.VISIBLE);

                                String uid = UUID.randomUUID().toString();

                                Map<String,Object> todo = new HashMap<>();
                                todo.put("user_json",gson.toJson(salonModel));
                                todo.put("user_contact",salonModel.getPhone());
                                todo.put("title",title.getText().toString());
                                todo.put("message",message.getText().toString());

                                db.collection("Contact_Us").document(uid).set(todo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(ContactUsActivity.this, "Successfully Contact ! You get reply within 2 working days", Toast.LENGTH_SHORT).show();

                                        finish();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        btnNext.setVisibility(View.VISIBLE);
                                        btnProgress.setVisibility(View.GONE);
                                        Toast.makeText(ContactUsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }


                        }
                    });
















    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);



        if (requestCode==101&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            String contact = "+919636964321"; // use country code with your phone number
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact));
            startActivity(intent);
        }

        if (requestCode==Common.Request_Gallery&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {

        }
    }
}