package com.androidcafe.barberconnectsalonapp.Barbers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcafe.barberconnectsalonapp.Common;
import com.androidcafe.barberconnectsalonapp.Model.SalonModel;
import com.androidcafe.barberconnectsalonapp.MyFirebase.UploadFile;
import com.androidcafe.barberconnectsalonapp.Profile_Options.EditProfileActivity;
import com.androidcafe.barberconnectsalonapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.paperdb.Paper;

public class AddBarberActivity extends AppCompatActivity implements UploadFile.OnImageUpload {

    TextView add_image;
    SalonModel salonModel;
    EditText salon_name,salon_mobile;
    ImageView profile_image;

    Button btnUpdate;

    ProgressBar btnProgress;

    FirebaseFirestore db;

    UploadFile uploadFile;
    Uri saveuri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_barber);
        db = FirebaseFirestore.getInstance();

        Paper.init(this);
        uploadFile = new UploadFile(this,this);


        salonModel = Paper.book().read("salon_model");

        initViews();
      //  setViews();
        handleClicks();


    }

    private void handleClicks() {


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.isEmpty(salon_name)||Common.isEmpty(salon_mobile))
                {
                    if (Common.isEmpty(salon_name))
                    {
                        salon_mobile.setError("Enter your name");
                    }
                    if (Common.isEmpty(salon_mobile))
                        salon_mobile.setError("Enter your mobile no");

                }
                else
                {
                    btnProgress.setVisibility(View.VISIBLE);
                    btnUpdate.setVisibility(View.GONE);
                    if (saveuri==null)
                        updateUserDetails("na");

                    else
                        uploadFile.uploadonfirestorage(saveuri,Common.Image_Folder,salon_mobile.getText().toString());


                }

            }
        });



        add_image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, Common.Choose_Gallery);

                } else {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Common.Request_Gallery);
                }

            }
        });

    }

    private void setViews() {



        try {
            Picasso.get().load(salonModel.getImage()).fit().centerCrop().into(profile_image);
        }catch (IllegalArgumentException e)
        {

        }


        salon_name.setText(salonModel.getName());
        salon_mobile.setText(salonModel.getPhone());



    }

    private void initViews() {
        add_image = findViewById(R.id.add_image);
        salon_name = findViewById(R.id.salon_name);

        salon_mobile = findViewById(R.id.salon_mobile);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnProgress = findViewById(R.id.btnProgress);
        profile_image = findViewById(R.id.profile_image);

    }




    private void updateUserDetails(String image) {


        Map<String,Object> todo = new HashMap<>();
        todo.put("mobile",salon_mobile.getText().toString());
        todo.put("name",salon_name.getText().toString());


        String id = UUID.randomUUID().toString();
        if (!image.equals("na"))
            todo.put("image",image);


        db.collection("BarberShops").document("Dungarpur").collection("Shops").document(salonModel.getId()).collection(Common.BARBERS).document(id).set(todo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(AddBarberActivity.this, "Barber Added Successfully", Toast.LENGTH_SHORT).show();

                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                btnProgress.setVisibility(View.GONE);
                btnUpdate.setVisibility(View.VISIBLE);
                Toast.makeText(AddBarberActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void getUrl(String url) {
        updateUserDetails(url);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Common.Choose_Gallery && resultCode == RESULT_OK) {
            profile_image.setVisibility(View.VISIBLE);
            saveuri = data.getData();
            profile_image.setImageURI(data.getData());
        }

    }
}