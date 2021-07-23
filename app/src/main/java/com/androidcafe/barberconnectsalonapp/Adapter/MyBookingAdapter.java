package com.androidcafe.barberconnectsalonapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidcafe.barberconnectsalonapp.BookingOperations.BookingDetailsActivity;
import com.androidcafe.barberconnectsalonapp.Model.BookingModel;
import com.androidcafe.barberconnectsalonapp.Model.ServiceModel;
import com.androidcafe.barberconnectsalonapp.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.MyViewHolder> {


    Context context;
    List<BookingModel> list;

    FirebaseFirestore db;

    public MyBookingAdapter(Context context, List<BookingModel> list) {
        this.context = context;
        this.list = list;
        db = FirebaseFirestore.getInstance();
    }

    Gson gson = new Gson();
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cancel, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        BookingModel model = list.get(position);
        holder.tvTitle.setText(model.getCustomer_name());

        try {
            String[] date = model.getSlot_date().split("_");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
            Picasso.get().load(list.get(position).getCustomer_image()).placeholder(R.drawable.translogo).fit().centerCrop().into(holder.imgCleaning);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");



            ServiceModel[] serviceModelList = gson.fromJson(model.getServices(),ServiceModel[].class);

            String services = "";
            for (ServiceModel serviceModel:serviceModelList)
            {
                services = services+serviceModel.getName()+" : ₹"+serviceModel.getPrice()+"\n";
            }




            String inputPattern = "dd_MM_yyyy HH:mm a";
            String outputPattern = "dd_MM_yyyy h:mm a";
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);


            String input_format = model.getSlot_date()+" "+model.getEnd_time();
            Log.d("mytimedate",""+outputFormat.parse(input_format));


            Log.d("mytimedate2",""+calendar.getTime());


            Date parse = outputFormat.parse(input_format);
            Date now = Calendar.getInstance().getTime();


            if (parse.compareTo(now)<1)
            {
                Log.d("compare2",""+parse+"\n"+now);

              

            }
            else
            {
                Log.d("compare3",""+parse+"\n"+now);
            }



            holder.tvDateTime.setText(services+"\nTotal Amount = ₹"+model.getTotal_amount()+"\nBooking Date : "+calendar.get(Calendar.DATE)+"/"+simpleDateFormat.format(calendar.getTime())+"/"+calendar.get(Calendar.YEAR)+"\nBooking Timing : "+model.getStart_time()+" - "+model.getEnd_time());

        }catch (Exception e)
        {

        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().write("customer_booking",model);
                context.startActivity(new Intent(context, BookingDetailsActivity.class));

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgCleaning;
        TextView tvTitle,tvDateTime;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            imgCleaning = itemView.findViewById(R.id.imgCleaning);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);

        }
    }
}
