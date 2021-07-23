package com.androidcafe.barberconnectsalonapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androidcafe.barberconnectsalonapp.Adapter.MyBookingAdapter;
import com.androidcafe.barberconnectsalonapp.Model.BookingModel;
import com.androidcafe.barberconnectsalonapp.Model.SalonModel;
import com.androidcafe.barberconnectsalonapp.MyFirebase.MyFireStore;
import com.androidcafe.barberconnectsalonapp.R;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class CompletedFragment extends Fragment implements MyFireStore.OnGetBookings {


    public CompletedFragment() {
        // Required empty public constructor
    }

    MyFireStore myFireStore;

    LinearLayout li_empty;
    View view;
    private RecyclerView recyclerView;
    private MyBookingAdapter cancelAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_completed, container, false);
        //view = inflater.inflate(R.layout.fragment_order, container, false);

        li_empty = view.findViewById(R.id.li_empty);

        Paper.init(getActivity());
        myFireStore = new MyFireStore(getActivity());

        recyclerView = view.findViewById(R.id.rvCancel);



        SalonModel salonModel = Paper.book().read("salon_model");
        myFireStore.getBookings(salonModel.getId(),"Completed",this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());




        return view;
    }

    @Override
    public void onGetBookings(List<BookingModel> bookingModelList) {

        if (bookingModelList.size()>0)
        {
            li_empty.setVisibility(View.GONE);
        }
        else
        {
            li_empty.setVisibility(View.VISIBLE);
        }
        cancelAdapter = new MyBookingAdapter(getActivity(),bookingModelList);
        recyclerView.setAdapter(cancelAdapter);
    }
}