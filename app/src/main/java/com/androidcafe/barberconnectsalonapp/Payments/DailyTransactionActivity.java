package com.androidcafe.barberconnectsalonapp.Payments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcafe.barberconnectsalonapp.Adapter.CompleteOrdersAdapter;
import com.androidcafe.barberconnectsalonapp.Common;
import com.androidcafe.barberconnectsalonapp.Model.BookingModel;
import com.androidcafe.barberconnectsalonapp.Model.SalonModel;
import com.androidcafe.barberconnectsalonapp.Model.ServiceModel;
import com.androidcafe.barberconnectsalonapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.paperdb.Paper;

public class DailyTransactionActivity extends AppCompatActivity {


    FrameLayout date_filter;
    TextView tvTitle;
    FirebaseFirestore db;
    SalonModel salon_model;
    public static String TAG = "mytag123";
    ProgressBar progress_bar;

    RecyclerView recyclerView;

    CompleteOrdersAdapter adapter;


    TextView total_amount,online_payment,cash_payment;

    Gson gson = new Gson();

    ImageView imgBack;

    LinearLayout li_empty;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_transaction);
        Paper.init(this);
        salon_model = Paper.book().read("salon_model");

        initViews();
        handleClick();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(calendar.getTime());
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH)+1;
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        String date= mDay+"_"+mMonth+"_"+mYear;
        getPurchased(date);



    }

    private void getPurchased(String date) {

        db = FirebaseFirestore.getInstance();
//        29_3_2021

        db.collection("AllBookings").whereEqualTo("salon_id",salon_model.getId())
                .whereEqualTo("booking_status","Completed")
                .whereEqualTo("slot_date",date).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {



                progress_bar.setVisibility(View.GONE);


                if (!queryDocumentSnapshots.isEmpty())
                {
                    li_empty.setVisibility(View.GONE);

                    List<BookingModel> list = new ArrayList<>();
                    int total_sale =0;
                    int online_pay= 0;
                    int offline_pay =0;
                    for (DocumentSnapshot document:queryDocumentSnapshots)
                    {
                        BookingModel model = document.toObject(BookingModel.class);
                        model.setId(document.getId());
                        list.add(model);
                        Log.d(TAG, "onSuccess: "+document.getData());

                        Type listType = new TypeToken<List<ServiceModel>>(){}.getType();

                        List<ServiceModel> cartTableList = gson.fromJson(model.getServices(),listType);


                        for (ServiceModel serviceModel:cartTableList) {
                            total_sale = total_sale+Integer.parseInt(serviceModel.getPrice());

                            if (model.isPayOnline())
                            {
                                online_pay = online_pay+Integer.parseInt(serviceModel.getPrice());
                            }
                            else
                            {
                                offline_pay = offline_pay+Integer.parseInt(serviceModel.getPrice());
                            }

                        }
                        total_amount.setText("₹ "+total_sale);
                        cash_payment.setText("Cash Payment = ₹ "+offline_pay);
                        online_payment.setText("Online Payment = ₹ "+online_pay);

                        adapter = new CompleteOrdersAdapter(DailyTransactionActivity.this,list);
                        recyclerView.setAdapter(adapter);
                    }



                    }

                    else
                {
                    li_empty.setVisibility(View.VISIBLE);


                }



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }
        });


    }

    private void handleClick() {

        date_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dateDialog = new DatePickerDialog(DailyTransactionActivity.this, datePickerListener, mYear, mMonth, mDay);
                dateDialog.show();

            }


            private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    Calendar c2 = Calendar.getInstance();
                    c2.set(year,monthOfYear,dayOfMonth);

                    Log.d("mydatecalendar",""+dayOfMonth);


                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    dateFormat.setCalendar(c2);
                    String dateYouChoosed = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                    tvTitle.setText(dateFormat.format(c2.getTime()));


                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("d_M_yyyy");
                    getPurchased(dateFormat2.format(c2.getTime()));

                }
            };
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initViews() {
        date_filter = findViewById(R.id.date_filter);
        tvTitle = findViewById(R.id.tvTitle);
        progress_bar = findViewById(R.id.progress_bar);
        total_amount = findViewById(R.id.total_amount);
        online_payment = findViewById(R.id.online_payment);
        cash_payment = findViewById(R.id.cash_payment);
        imgBack = findViewById(R.id.imgBack);
        li_empty = findViewById(R.id.li_empty);


        tvTitle.setText(Common.getDateFromFireStore(Timestamp.now()));
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
}