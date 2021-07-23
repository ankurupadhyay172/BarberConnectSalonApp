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
import android.widget.Toast;

import com.androidcafe.barberconnectsalonapp.Adapter.CancelAdapter;
import com.androidcafe.barberconnectsalonapp.Adapter.MyBookingAdapter;
import com.androidcafe.barberconnectsalonapp.Common;
import com.androidcafe.barberconnectsalonapp.MainActivity;
import com.androidcafe.barberconnectsalonapp.Model.BookingModel;
import com.androidcafe.barberconnectsalonapp.Model.CancelModel;
import com.androidcafe.barberconnectsalonapp.Model.SalonModel;
import com.androidcafe.barberconnectsalonapp.MyFirebase.MyFireStore;
import com.androidcafe.barberconnectsalonapp.R;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

public class OrderFragment extends Fragment implements MyFireStore.OnGetBookings {


    public OrderFragment() {
        // Required empty public constructor
    }

    View view;
    /*Cancel Data is here*/
    private RecyclerView recyclerView;
    private MyBookingAdapter cancelAdapter;
    private ArrayList<CancelModel> cancelModels;

    private Integer imgCleaning[] = {R.drawable.ic_launcher_background,R.drawable.ic_launcher_background};
    private String title1[] = {"Bathroom Cleaning","Bathroom Cleaning"};
    private String tvDateTime[] = {"Wed 24 Jul’20 at 7 am ","Wed 24 Jul’20 at 7 am "};


    MyFireStore myFireStore;


    FirebaseFirestore db;

    LinearLayout li_empty;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order, container, false);

        Paper.init(getActivity());
        myFireStore = new MyFireStore(getActivity());

        li_empty = view.findViewById(R.id.li_empty);
        db = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.rvCancel);
        cancelModels = new ArrayList<>();




            SalonModel salonModel = Paper.book().read("salon_model");
            myFireStore.getBookings(salonModel.getId(),"Booked",this);
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