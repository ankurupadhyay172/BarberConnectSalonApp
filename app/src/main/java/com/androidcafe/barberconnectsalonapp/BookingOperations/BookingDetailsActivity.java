package com.androidcafe.barberconnectsalonapp.BookingOperations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcafe.barberconnectsalonapp.Model.BookingModel;
import com.androidcafe.barberconnectsalonapp.Model.ServiceModel;
import com.androidcafe.barberconnectsalonapp.R;
import com.androidcafe.barberconnectsalonapp.Select_Services.Select_Extra_Service;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class BookingDetailsActivity extends AppCompatActivity {


    TextView status,total,customer_name,start_time,end_time,booking_date,payment_status,service_name,cart_total,grand_total,payment_type;
    RoundedImageView customer_image;

    TextView service_charge,timing;
    Gson gson = new Gson();

    LinearLayout complete_booking;
    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        Paper.init(this);
        initViews();
        setViews();



    }

    private void setViews() {

        BookingModel bookingModel = Paper.book().read("customer_booking");

        String[] selected_date = bookingModel.getSlot_date().split("_");
        String last_date = selected_date[0]+"/"+selected_date[1]+"/"+selected_date[2];


        ServiceModel[] serviceModelList = gson.fromJson(bookingModel.getServices(),ServiceModel[].class);

        String services = "";
        for (ServiceModel serviceModel:serviceModelList)
        {
            services = services+serviceModel.getName()+" : ₹"+serviceModel.getPrice()+"\n";
        }

        customer_name.setText("Customer Name : "+bookingModel.getCustomer_name());
        try {
            Picasso.get().load(bookingModel.getCustomer_image()).fit().centerCrop().into(customer_image);
        }catch (IllegalArgumentException e)
        {

        }




        booking_date.setText(last_date);

        payment_status.setText(bookingModel.getPayment_status());
        start_time.setText(bookingModel.getStart_time());
        end_time.setText(bookingModel.getEnd_time());

        service_name.setText(services);
        cart_total.setText("₹"+bookingModel.getTotal_amount());
        grand_total.setText("₹"+bookingModel.getTotal_amount());
        timing.setText("Booking Date : "+last_date+"\nBooking Timing : "+bookingModel.getStart_time()+"-"+bookingModel.getEnd_time());
        total.setText("₹ "+bookingModel.getTotal_amount());
        if (bookingModel.isPayOnline())
        {
            payment_type.setText("(Online)");
        }
        else
            payment_type.setText("(Pay On Shop)");

        if (bookingModel.getBooking_status().equals("Booked"))
        {


            status.setText(bookingModel.getCustomer_name()+" Booked Your Service. \n"+services+"Booking Date : "+last_date+"\nBooking Timing : "+bookingModel.getStart_time()+" : "+bookingModel.getEnd_time()+".\n We Know You Provide Your Best.");


        }
        else if (bookingModel.getBooking_status().equals("Cancelled"))
        {
            try {
                status.setText("Booking Cancelled Because : "+bookingModel.getCancel_reason().toUpperCase()+"\nCustomer Comments : "+bookingModel.getCancel_comments().toUpperCase());
            }catch (Exception e)
            {

            }

            complete_booking.setVisibility(View.GONE);
        }


        else {

            status.setText("Booking Completed");

            complete_booking.setVisibility(View.GONE);
        }


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        complete_booking.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookingDetailsActivity.this, Select_Extra_Service.class));
            }
        });

    }

    private void initViews() {

        status = findViewById(R.id.status);
        total = findViewById(R.id.total);
        customer_name = findViewById(R.id.customer_name);

        start_time = findViewById(R.id.start_time);
        end_time = findViewById(R.id.end_time);
        booking_date = findViewById(R.id.booking_date);
        customer_image = findViewById(R.id.customer_image);
        payment_status = findViewById(R.id.payment_status);
        service_name = findViewById(R.id.service_name);
        cart_total = findViewById(R.id.cart_total);
        grand_total = findViewById(R.id.grand_total);

        service_charge = findViewById(R.id.service_charge);
        timing = findViewById(R.id.timing);
        payment_type = findViewById(R.id.payment_type);

        imgBack = findViewById(R.id.imgBack);
        complete_booking = findViewById(R.id.complete_booking);
    }
}