package com.androidcafe.barberconnectsalonapp.Barbers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.androidcafe.barberconnectsalonapp.Adapter.BarbersAdapter;
import com.androidcafe.barberconnectsalonapp.Model.Model_Barber_Details;
import com.androidcafe.barberconnectsalonapp.Model.SalonModel;
import com.androidcafe.barberconnectsalonapp.MyFirebase.MyFireStore;
import com.androidcafe.barberconnectsalonapp.R;

import java.util.List;

import io.paperdb.Paper;

public class ManageBarbersActivity extends AppCompatActivity implements MyFireStore.OnGetBarbers {


    BarbersAdapter adapter;
    RecyclerView recyclerView;
    MyFireStore myFireStore;
    SalonModel salonModel;

    TextView option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_barbers);

        Paper.init(this);

        option = findViewById(R.id.option);
        myFireStore = new MyFireStore(this);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        salonModel = Paper.book().read("salon_model");


        option.setVisibility(View.VISIBLE);

        option.setText("Add Barber");
        myFireStore.getBarbers(salonModel.getId(),salonModel.getCity(),this);





        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageBarbersActivity.this,AddBarberActivity.class));
            }
        });


    }

    @Override
    public void onGetBarberlist(List<Model_Barber_Details> list) {


        adapter = new BarbersAdapter(this,list);
        recyclerView.setAdapter(adapter);

    }


    @Override
    protected void onRestart() {
        myFireStore.getBarbers(salonModel.getId(),salonModel.getCity(),this);

        super.onRestart();
    }
}