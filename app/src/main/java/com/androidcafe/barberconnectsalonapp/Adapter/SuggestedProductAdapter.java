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


import com.androidcafe.barberconnectsalonapp.AddSingleServiceActivity;
import com.androidcafe.barberconnectsalonapp.Model.ServiceModel;
import com.androidcafe.barberconnectsalonapp.Model.SuggestedProductModel;
import com.androidcafe.barberconnectsalonapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SuggestedProductAdapter extends RecyclerView.Adapter<SuggestedProductAdapter.MyViewHolder> {

    Context context;
    private ArrayList<SuggestedProductModel> suggestedProductModelArrayList;

    OnGetData onGetData;
    Gson gson = new Gson();
    public SuggestedProductAdapter(Context context, ArrayList<SuggestedProductModel> suggestedProductModelArrayList,OnGetData onGetData) {
        this.context = context;
        this.suggestedProductModelArrayList = suggestedProductModelArrayList;
        this.onGetData = onGetData;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_product, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final SuggestedProductModel model = suggestedProductModelArrayList.get(position);

        try {
            holder.ivProduct.setImageResource(model.getIvProduct());

            holder.tvTitle.setText(model.getTvProductname());


            ServiceModel[] serviceModel = gson.fromJson(model.getService(),ServiceModel[].class);


            String myservice = "";
            for (ServiceModel serviceModel1:serviceModel)
            {
                myservice = myservice+serviceModel1.getName()+" = ₹"+serviceModel1.getPrice()+"\n";

            }
                holder.details.setText(myservice);



        }catch (Exception e)
        {

        }




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //context.startActivity(new Intent(context, AddSingleServiceActivity.class));

                onGetData.onGetData(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return suggestedProductModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct,ivCheck;
        TextView tvTitle,details,tvAdd;

        public MyViewHolder(View view) {
            super(view);
            ivProduct = view.findViewById(R.id.ivProduct);
            tvTitle = view.findViewById(R.id.tvTitle);
          //  tvPrice = view.findViewById(R.id.tvPrice);
            details = view.findViewById(R.id.details);
            tvAdd = view.findViewById(R.id.tvAdd);

        }


    }

    public interface OnGetData
    {
        public void onGetData(int pos);
    }
}