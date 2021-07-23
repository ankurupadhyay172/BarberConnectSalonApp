package com.androidcafe.barberconnectsalonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcafe.barberconnectsalonapp.Fragments.AddMyServiceFragment;
import com.androidcafe.barberconnectsalonapp.Fragments.AddServiceFragment;
import com.androidcafe.barberconnectsalonapp.Fragments.BookingFragment;
import com.androidcafe.barberconnectsalonapp.Fragments.HelpFragment;
import com.androidcafe.barberconnectsalonapp.Fragments.ProfileFragment;
import com.androidcafe.barberconnectsalonapp.Model.SalonModel;
import com.androidcafe.barberconnectsalonapp.MyFirebase.MyFireStore;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

import io.paperdb.Paper;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    /*-----Variable Define------*/
    LinearLayout linear1, linear2, linear3, linear4;
    ImageView img1, img2, img3, img4;
    TextView txt1, txt2, txt3, txt4;
    FrameLayout frameLayout;
    Fragment fragment;



    MyFireStore myFireStore;
    FirebaseMessaging messaging;

    CompositeDisposable compositeDisposable;

    SalonModel salonModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        compositeDisposable = new CompositeDisposable();
        Paper.init(this);
        messaging = FirebaseMessaging.getInstance();
        myFireStore =new MyFireStore(this);

        frameLayout = findViewById(R.id.framlayout);

        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);
        linear3 = findViewById(R.id.linear3);
        linear4 = findViewById(R.id.linear4);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);

        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);

        linear1.setOnClickListener(this);
        linear2.setOnClickListener(this);
        linear3.setOnClickListener(this);
        linear4.setOnClickListener(this);

        fragment = new BookingFragment();
        loadFragment(fragment);



        salonModel = Paper.book().read("salon_model");

        messaging.getToken().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                myFireStore.updateToken(s,salonModel.getCity(),salonModel.getId());


                //Common.sendnotificationmethod("test notification","this is test notification",compositeDisposable,s);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


      //  myFireStore.updateToken(messaging.getToken().getResult());

    }

    @Override
    public void onClick(View view) {
        Fragment fragment;
        switch (view.getId()) {
            case R.id.linear1:
                img1.setImageResource(R.drawable.home_green);
                img2.setImageResource(R.drawable.bookings_grey);
                img3.setImageResource(R.drawable.help_grey);
                img4.setImageResource(R.drawable.user_grey);

                img1.setColorFilter(getResources().getColor(R.color.green_15d1a4));
                img2.setColorFilter(getResources().getColor(R.color.grey_828990));
                img3.setColorFilter(getResources().getColor(R.color.grey_828990));
                img4.setColorFilter(getResources().getColor(R.color.grey_828990));

                txt1.setVisibility(View.VISIBLE);
                txt2.setVisibility(View.GONE);
                txt3.setVisibility(View.GONE);
                txt4.setVisibility(View.GONE);

                linear1.setBackgroundResource(R.drawable.rect_lightgreen);
                linear2.setBackgroundResource(R.drawable.rect_transperent);
                linear3.setBackgroundResource(R.drawable.rect_transperent);
                linear4.setBackgroundResource(R.drawable.rect_transperent);

                fragment = new BookingFragment();
                loadFragment(fragment);
                break;

            case R.id.linear2:
                img1.setImageResource(R.drawable.home_green);
                img2.setImageResource(R.drawable.bookings_grey);
                img3.setImageResource(R.drawable.help_grey);
                img4.setImageResource(R.drawable.user_grey);

                img1.setColorFilter(getResources().getColor(R.color.grey_828990));
                img2.setColorFilter(getResources().getColor(R.color.green_15d1a4));
                img3.setColorFilter(getResources().getColor(R.color.grey_828990));
                img4.setColorFilter(getResources().getColor(R.color.grey_828990));

                txt1.setVisibility(View.GONE);
                txt2.setVisibility(View.VISIBLE);
                txt3.setVisibility(View.GONE);
                txt4.setVisibility(View.GONE);

                linear1.setBackgroundResource(R.drawable.rect_transperent);
                linear2.setBackgroundResource(R.drawable.rect_lightgreen);
                linear3.setBackgroundResource(R.drawable.rect_transperent);
                linear4.setBackgroundResource(R.drawable.rect_transperent);

                //fragment = new AddServiceFragment();
                fragment = new AddMyServiceFragment();
                loadFragment(fragment);
                break;

            case R.id.linear3:
                img1.setImageResource(R.drawable.home_green);
                img2.setImageResource(R.drawable.bookings_grey);
                img3.setImageResource(R.drawable.help_grey);
                img4.setImageResource(R.drawable.user_grey);

                img1.setColorFilter(getResources().getColor(R.color.grey_828990));
                img2.setColorFilter(getResources().getColor(R.color.grey_828990));
                img3.setColorFilter(getResources().getColor(R.color.green_15d1a4));
                img4.setColorFilter(getResources().getColor(R.color.grey_828990));

                txt1.setVisibility(View.GONE);
                txt2.setVisibility(View.GONE);
                txt3.setVisibility(View.VISIBLE);
                txt4.setVisibility(View.GONE);

                linear1.setBackgroundResource(R.drawable.rect_transperent);
                linear2.setBackgroundResource(R.drawable.rect_transperent);
                linear3.setBackgroundResource(R.drawable.rect_lightgreen);
                linear4.setBackgroundResource(R.drawable.rect_transperent);

                fragment = new HelpFragment();
                loadFragment(fragment);
                break;

            case R.id.linear4:
                img1.setImageResource(R.drawable.home_green);
                img2.setImageResource(R.drawable.bookings_grey);
                img3.setImageResource(R.drawable.help_grey);
                img4.setImageResource(R.drawable.user_grey);

                img1.setColorFilter(getResources().getColor(R.color.grey_828990));
                img2.setColorFilter(getResources().getColor(R.color.grey_828990));
                img3.setColorFilter(getResources().getColor(R.color.grey_828990));
                img4.setColorFilter(getResources().getColor(R.color.green_15d1a4));

                txt1.setVisibility(View.GONE);
                txt2.setVisibility(View.GONE);
                txt3.setVisibility(View.GONE);
                txt4.setVisibility(View.VISIBLE);

                linear1.setBackgroundResource(R.drawable.rect_transperent);
                linear2.setBackgroundResource(R.drawable.rect_transperent);
                linear3.setBackgroundResource(R.drawable.rect_transperent);
                linear4.setBackgroundResource(R.drawable.rect_lightgreen);
                fragment = new ProfileFragment();
                loadFragment(fragment);
                break;
        }
    }

    /*load fragment method can be here*/
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framlayout, fragment);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}