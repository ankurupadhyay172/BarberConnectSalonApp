package com.androidcafe.barberconnectsalonapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidcafe.barberconnectsalonapp.BookingOperations.BookingDetailsActivity;
import com.androidcafe.barberconnectsalonapp.Model.BookingModel;
import com.androidcafe.barberconnectsalonapp.Model.ServiceModel;
import com.androidcafe.barberconnectsalonapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.List;

import io.paperdb.Paper;

public class CompleteOrdersAdapter extends RecyclerView.Adapter<CompleteOrdersAdapter.MyViewHolder> {

    Context context;
    List<BookingModel> list;

    public CompleteOrdersAdapter(Context context, List<BookingModel> list) {
        this.context = context;
        this.list = list;
    }

    FirebaseFirestore db;
    Gson gson = new Gson();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Paper.init(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mangeaddress,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        BookingModel model = list.get(position);

        Type listType = new TypeToken<List<ServiceModel>>(){}.getType();

        holder.user_name.setText(model.getCustomer_name());



        List<ServiceModel> cartTableList = gson.fromJson(model.getServices(),listType);

        int total_price=0;
        String services = "";
        for (ServiceModel serviceModel:cartTableList)
        {
            total_price = total_price+Integer.parseInt(serviceModel.getPrice());
            services = services+serviceModel.getName()+"= "+serviceModel.getPrice()+"\n";
        }

        holder.user_detail.setText("Barber = "+model.getBarber_name().toUpperCase()+"\n"+services);

        holder.price.setText("Total =₹ "+total_price);


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
        ImageView user_image;
        TextView user_name,user_detail,price;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            price = itemView.findViewById(R.id.price);
            user_image = itemView.findViewById(R.id.user_image);
            user_name = itemView.findViewById(R.id.user_name);
            user_detail = itemView.findViewById(R.id.user_detail);
        }
    }
}
