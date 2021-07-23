package com.androidcafe.barberconnectsalonapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidcafe.barberconnectsalonapp.BookingOperations.BookingDetailsActivity;
import com.androidcafe.barberconnectsalonapp.Common;
import com.androidcafe.barberconnectsalonapp.Model.BookingModel;
import com.androidcafe.barberconnectsalonapp.Model.CancelModel;
import com.androidcafe.barberconnectsalonapp.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class CancelAdapter extends RecyclerView.Adapter<CancelAdapter.MyViewHolder> {

    Context context;
    private List<CancelModel> models;
    Gson gson = new Gson();
    public CancelAdapter(Context context, List<CancelModel> models) {
        this.context = context;
        this.models = models;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCleaning;
        TextView tvTitle,tvDateTime;

        public MyViewHolder(View view) {
            super(view);
            imgCleaning = view.findViewById(R.id.imgCleaning);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDateTime = view.findViewById(R.id.tvDateTime);
        }
    }
    @Override
    public CancelAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cancel, parent, false);
        return new CancelAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        CancelModel lists = models.get(position);
        holder.tvTitle.setText("Booking Cancelled \nCancel Reason : "+lists.getCancel_reason());
        holder.tvDateTime.setText("Customer Comments : "+lists.getComments()+"\nCancel On : "+ Common.gethours(lists.getTimestamp().getSeconds()));

        BookingModel model = gson.fromJson(lists.getBooking_json(),BookingModel.class);
        model.setBooking_status("Cancelled");

        model.setCancel_reason(lists.getCancel_reason());
        model.setCancel_comments(lists.getComments());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().write("customer_booking",model);
                context.startActivity(new Intent(context, BookingDetailsActivity.class));

            }
        });


        holder.imgCleaning.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
