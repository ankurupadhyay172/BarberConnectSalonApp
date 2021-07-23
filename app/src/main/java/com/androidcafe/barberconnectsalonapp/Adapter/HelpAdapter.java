package com.androidcafe.barberconnectsalonapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidcafe.barberconnectsalonapp.Model.HelpModel;
import com.androidcafe.barberconnectsalonapp.R;

import java.util.ArrayList;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.MyViewHolder> {

    Context context;
    private ArrayList<HelpModel> models;

    public HelpAdapter(Context context, ArrayList<HelpModel> models) {
        this.context = context;
        this.models = models;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription;
        ImageView imgArrow;
        CardView cardHelp;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDescription = view.findViewById(R.id.tvDescription);
            imgArrow = view.findViewById(R.id.imgArrow);
            cardHelp = view.findViewById(R.id.cardHelp);
        }
    }
    @Override
    public HelpAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_help, parent, false);
        return new HelpAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final HelpModel lists = models.get(position);
        holder.tvTitle.setText(lists.getTvTitle());
        holder.tvDescription.setText(lists.getTvDescription());

        holder.cardHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lists.isSelected()==false) {
                    holder.imgArrow.setImageResource(R.drawable.ic_arrow_up);
                    holder.tvDescription.setVisibility(View.VISIBLE);
                    lists.setSelected(true);
                }else{
                    holder.imgArrow.setImageResource(R.drawable.ic_arrow_down);
                    holder.tvDescription.setVisibility(View.GONE);
                    lists.setSelected(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}