package com.androidcafe.barberconnectsalonapp.Fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidcafe.barberconnectsalonapp.Adapter.HelpAdapter;
import com.androidcafe.barberconnectsalonapp.Model.HelpModel;
import com.androidcafe.barberconnectsalonapp.R;

import java.util.ArrayList;

public class HelpFragment extends Fragment {


    public HelpFragment() {
        // Required empty public constructor
    }



    /*----Variable Define------*/
    ImageView imgBack;
    TextView tvTitle;

    /*Help Data is here*/
    private RecyclerView recyclerView;
    private HelpAdapter helpAdapter;
    private ArrayList<HelpModel> helpModels;
    private String title1[] = {"Need help with booking?","How to get pay for booking?","Need help with cancel booking?"};
    private String tvDescription[] = {"Email Id : Barberconnectofficial@gmail.com\nContact No : 9636964321 ",
            "If Payment Status Show Paid Then You Will Get Your Money In Your Account Between 4 - 7 Working Days.Otherwise You Can Get Money Directly From The Customer If He/She Not Paid Online",
            "If Customer Not Reach Untill Booking End Time Booking Will Automatically Cancelled .Otherwise If Use Cancel Booking Then App Can Show The Reason Of Booking Cancellation"};

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_help, container, false);

        /*-------Status Color Code To Change--------*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.main_color));
        }
        imgBack = view.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText("Help");
        /*Help reyclcerview code is here*/

        recyclerView = view.findViewById(R.id.rvHelp);
        helpModels = new ArrayList<>();

        for (int i = 0; i < title1.length; i++) {
            HelpModel model = new HelpModel(title1[i],tvDescription[i]);
            helpModels.add(model);
        }

        helpAdapter = new HelpAdapter(getContext(), helpModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(helpAdapter);

        return view;
    }
}