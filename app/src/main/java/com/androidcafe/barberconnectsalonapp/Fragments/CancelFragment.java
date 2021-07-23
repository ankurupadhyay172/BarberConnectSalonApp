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
import com.androidcafe.barberconnectsalonapp.Model.BookingModel;
import com.androidcafe.barberconnectsalonapp.Model.CancelModel;
import com.androidcafe.barberconnectsalonapp.Model.SalonModel;
import com.androidcafe.barberconnectsalonapp.MyFirebase.MyFireStore;
import com.androidcafe.barberconnectsalonapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class CancelFragment extends Fragment implements MyFireStore.OnGetCancel {


    public CancelFragment() {
        // Required empty public constructor
    }
    View view;
    /*Cancel Data is here*/
    private RecyclerView recyclerView;
    private CancelAdapter cancelAdapter;
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
        view =inflater.inflate(R.layout.fragment_cancel, container, false);

        Paper.init(getActivity());
        myFireStore = new MyFireStore(getActivity());

        li_empty = view.findViewById(R.id.li_empty);
        db = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.rvCancel);
        cancelModels = new ArrayList<>();



        SalonModel salonModel = Paper.book().read("salon_model");
       // myFireStore.getBookings(salonModel.getId(),"Cancelled",this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        myFireStore.getCancelled(salonModel.getId(),this);

        return view;

    }

    @Override
    public void onGetCancel(List<CancelModel> cancelModels) {
        cancelAdapter = new CancelAdapter(getActivity(),cancelModels);
        recyclerView.setAdapter(cancelAdapter);
        if (cancelModels.size()>0)
        {
            li_empty.setVisibility(View.GONE);
        }
        else
        {
            li_empty.setVisibility(View.VISIBLE);
        }

    }
}