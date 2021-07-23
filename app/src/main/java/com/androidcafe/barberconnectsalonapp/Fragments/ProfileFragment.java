package com.androidcafe.barberconnectsalonapp.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcafe.barberconnectsalonapp.Barbers.ManageBarbersActivity;
import com.androidcafe.barberconnectsalonapp.ContactUsActivity;
import com.androidcafe.barberconnectsalonapp.Model.SalonModel;
import com.androidcafe.barberconnectsalonapp.MyFirebase.MyFireStore;
import com.androidcafe.barberconnectsalonapp.Payments.DailyTransactionActivity;
import com.androidcafe.barberconnectsalonapp.Profile_Options.EditProfileActivity;
import com.androidcafe.barberconnectsalonapp.R;
import com.androidcafe.barberconnectsalonapp.SplashScreenActivity;
import com.androidcafe.barberconnectsalonapp.TermsandConditions;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class ProfileFragment extends Fragment implements MyFireStore.OnGetProfile {


    public ProfileFragment() {
        // Required empty public constructor
    }


    LinearLayout manage_barbers;

    ImageView imageView1;

    TextView name,address,edit_profile;


    MyFireStore myFireStore;

    LinearLayout llLogout,llContactUs,llTandC,li_transactions;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        /*-------Status Color Code To Change--------*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.main_color));
        }
        myFireStore = new MyFireStore(getActivity());
        Paper.init(getActivity());
        imageView1 = view.findViewById(R.id.imageView1);
        name = view.findViewById(R.id.name);
        address = view.findViewById(R.id.address);
        edit_profile = view.findViewById(R.id.edit_profile);

        li_transactions = view.findViewById(R.id.li_transactions);
        llTandC = view.findViewById(R.id.llTandC);
        llLogout = view.findViewById(R.id.llLogout);
        llContactUs = view.findViewById(R.id.llContactUs);
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });

        llContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ContactUsActivity.class));
            }
        });


        llTandC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TermsandConditions.class));
            }
        });


        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Toast.makeText(getActivity(), "Log out successfully", Toast.LENGTH_SHORT).show();



                Intent intent = new Intent(getActivity(), SplashScreenActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        li_transactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getActivity(), DailyTransactionActivity.class));
            }
        });

        manage_barbers = view.findViewById(R.id.manage_barbers);
        manage_barbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ManageBarbersActivity.class));
            }
        });

        try {

            if (Paper.book().read("salon_model")!=null)
            {
                SalonModel salonModel = Paper.book().read("salon_model");


                myFireStore.getSalonProfile(salonModel.getId(),this);


            }
        }catch (Exception e)
        {

            Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }




        return view;
    }

    @Override
    public void onGetProfile(SalonModel salonModel) {

        Paper.book().write("salon_model",salonModel);
        Picasso.get().load(salonModel.getImage()).placeholder(R.drawable.translogo).fit().centerCrop().into(imageView1);
        name.setText(salonModel.getName());
        address.setText(salonModel.getAddress());

    }

    @Override
    public void onResume() {
        try {

            if (Paper.book().read("salon_model")!=null)
            {
                SalonModel salonModel = Paper.book().read("salon_model");


                myFireStore.getSalonProfile(salonModel.getId(),this);


            }
        }catch (Exception e)
        {

            Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        super.onResume();
    }
}