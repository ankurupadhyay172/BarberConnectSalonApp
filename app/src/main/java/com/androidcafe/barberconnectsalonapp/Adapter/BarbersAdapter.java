package com.androidcafe.barberconnectsalonapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidcafe.barberconnectsalonapp.Model.Model_Barber_Details;
import com.androidcafe.barberconnectsalonapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BarbersAdapter extends RecyclerView.Adapter<BarbersAdapter.MyViewHolder> {


    Context context;
    List<Model_Barber_Details> list ;

    public BarbersAdapter(Context context, List<Model_Barber_Details> list) {
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.item_cancel,parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Model_Barber_Details barber_model = list.get(0);
        holder.tvTitle.setText(list.get(position).getName());
        holder.tvDateTime.setText(""+barber_model.getRating());
        try {
            Picasso.get().load(list.get(position).getImage()).fit().centerCrop().into(holder.imgCleaning);
        }catch (IllegalArgumentException e)
        {

        }

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
