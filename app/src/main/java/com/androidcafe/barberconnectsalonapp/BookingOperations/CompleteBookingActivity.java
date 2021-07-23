package com.androidcafe.barberconnectsalonapp.BookingOperations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcafe.barberconnectsalonapp.Common;
import com.androidcafe.barberconnectsalonapp.MainActivity;
import com.androidcafe.barberconnectsalonapp.Model.BookingModel;
import com.androidcafe.barberconnectsalonapp.Model.ServiceModel;
import com.androidcafe.barberconnectsalonapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class CompleteBookingActivity extends AppCompatActivity {


    ImageView profile_image;
    EditText customer_name,services,amount;
    WriteBatch batch;
    ProgressBar btnProgress;

    FirebaseFirestore db;
    Button btnUpdate;
    Gson gson = new Gson();
    BookingModel bookingModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_booking);

        db = FirebaseFirestore.getInstance();
        batch = db.batch();

        Paper.init(this);

        initViews();
        setValues();
        handleClicks();

    }

    private void handleClicks() {

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnUpdate.setVisibility(View.GONE);
                btnProgress.setVisibility(View.VISIBLE);
                updateBookingStatus();




            }
        });




    }

    private void updateBookingStatus() {


        Map<String,Object> todo = new HashMap<>();
        todo.put("booking_status","Completed");
        todo.put("done",true);
        todo.put("payment_status","Paid");
        todo.put("completed_timestamp", Timestamp.now());


        DocumentReference barber_doc =db.collection(Common.BARBER_SHOPS).document(bookingModel.getCity()).collection(Common.ALL_SHOPS).document(bookingModel.getSalon_id()).collection("Barbers").document(bookingModel.getBarber_id()).collection(bookingModel.getSlot_date()).document(String.valueOf(bookingModel.getSlot_no()));
        batch.update(barber_doc,todo);


        DocumentReference user_doc = db.collection("Users").document(bookingModel.getUser_id()).collection("Booking")
                .document(bookingModel.getId());
        batch.update(user_doc,todo);


        DocumentReference booking_doc = db.collection("AllBookings").document(bookingModel.getId());
        batch.update(booking_doc,todo);



        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(CompleteBookingActivity.this, "Hurrey ! Booking Completed Successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CompleteBookingActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



//        db.collection("AllBookings").document(bookingModel.getId()).update(todo).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(CompleteBookingActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });


    }

    private void setValues() {


        bookingModel = Paper.book().read("customer_booking");
        customer_name.setText(bookingModel.getCustomer_name());

        ServiceModel[] serviceModelList = gson.fromJson(bookingModel.getServices(),ServiceModel[].class);

        String service = "";
        for (ServiceModel serviceModel:serviceModelList)
        {
            service = service+serviceModel.getName()+" : ₹"+serviceModel.getPrice()+"\n";
        }



        services.setText(service);
        amount.setText("₹"+bookingModel.getTotal_amount());




        try {
            Picasso.get().load(bookingModel.getCustomer_image()).placeholder(R.drawable.translogo).fit().centerCrop().into(profile_image);
        }catch (IllegalArgumentException e)
        {

        }



    }

    private void initViews() {

        profile_image = findViewById(R.id.profile_image);
        customer_name = findViewById(R.id.customer_name);
        services  = findViewById(R.id.services);
        amount = findViewById(R.id.amount);
        btnProgress = findViewById(R.id.btnProgress);
        btnUpdate = findViewById(R.id.btnUpdate);

    }
}