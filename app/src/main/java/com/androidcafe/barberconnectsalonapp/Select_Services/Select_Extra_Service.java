package com.androidcafe.barberconnectsalonapp.Select_Services;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcafe.barberconnectsalonapp.BookingOperations.CompleteBookingActivity;
import com.androidcafe.barberconnectsalonapp.Common;
import com.androidcafe.barberconnectsalonapp.Model.BookingModel;
import com.androidcafe.barberconnectsalonapp.Model.SalonModel;
import com.androidcafe.barberconnectsalonapp.Model.ServiceModel;
import com.androidcafe.barberconnectsalonapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.paperdb.Paper;

public class Select_Extra_Service extends AppCompatActivity implements View.OnClickListener {

    /*----Variable Define----*/
    ImageView imgBack;
    Spinner spHairCut,spShaving,spColor,spMassage,spThreading;
    RadioButton rbMale, rbFeMale;
    LinearLayout liMale, liFeMale;

    Intent intent;
    FirebaseFirestore db;


    Button btnContinue;


    TextView total_amount;
    int cutting, shaving, coloring, child_hair;
    int mytotal,hair_cutting_price,shave_price,color_price,massage_price,threading_price;

    String select_cutting, select_shaving, select_coloring, select_child_hair,select_threading;


    ServiceModel selected_hair_model,selected_shave_model,selected_color_model,selected_massage_model,selected_threading_model;
    Gson gson = new Gson();


    List<ServiceModel> selected_Services;
    BookingModel booking_model;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__extra__service);

        Paper.init(this);

        total_amount = findViewById(R.id.total_amount);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        spMassage = findViewById(R.id.spMassage);
        spColor = findViewById(R.id.spColor);
        spThreading = findViewById(R.id.spThreading);
        spShaving = findViewById(R.id.spShaving);
        btnContinue = findViewById(R.id.btnContinue);
        rbMale = findViewById(R.id.rbMale);
        rbFeMale = findViewById(R.id.rbFeMale);
        liMale = findViewById(R.id.liMale);
        liFeMale = findViewById(R.id.liFeMale);
        rbFeMale.setOnClickListener(this);
        rbMale.setOnClickListener(this);

        SalonModel salonModel =Paper.book().read("salon_model");
        booking_model =Paper.book().read("customer_booking");
        getAllService();
        editServices();


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selected_Services = new ArrayList<>();
                if (selected_hair_model!=null)
                    selected_Services.add(selected_hair_model);
                if (selected_shave_model!=null)
                    selected_Services.add(selected_shave_model);
                if (selected_color_model!=null)
                    selected_Services.add(selected_color_model);
                if (selected_massage_model!=null)
                    selected_Services.add(selected_massage_model);
                if (selected_threading_model!=null)
                    selected_Services.add(selected_threading_model);



                Log.d("myselectedservices",""+gson.toJson(selected_Services));

                booking_model.setServices(gson.toJson(selected_Services));
                booking_model.setTotal_amount(mytotal);

                Paper.book().write("customer_booking",booking_model);
                startActivity(new Intent(Select_Extra_Service.this,CompleteBookingActivity.class));
            }
        });






    }

    private void editServices() {





        ServiceModel[] serviceModels = gson.fromJson(booking_model.getServices(),ServiceModel[].class);

        List<ServiceModel> serviceModelList = Arrays.asList(serviceModels);


        //spHairCut.setSelection(serviceModelList.indexOf("Normal Hair Cut(₹100)"));

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rbMale:
                liMale.setVisibility(View.VISIBLE);
                liFeMale.setVisibility(View.GONE);
                rbFeMale.setChecked(false);
                rbMale.setChecked(true);
                break;
            case R.id.rbFeMale:
                liMale.setVisibility(View.GONE);
                liFeMale.setVisibility(View.VISIBLE);
                rbFeMale.setChecked(true);
                rbMale.setChecked(false);
                break;
        }
    }


    private void getAllService() {


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        List<String> myhaircut = new ArrayList<>();

        List<String> myshave = new ArrayList<>();

        List<String> mycolor = new ArrayList<>();
        List<String> mymassage = new ArrayList<>();
        List<String> myThread = new ArrayList<>();

        myhaircut.add("Select Hair Cut");
        myshave.add("Select Shave Cut");
        mycolor.add("Select Coloring");
        mymassage.add("Select Massage");
        myThread.add("Select Threading");





        SalonModel salonModel =Paper.book().read("salon_model");

        db.collection(Common.BARBER_SHOPS).document("Dungarpur").collection(Common.ALL_SHOPS).document(salonModel.getId())
                .collection("Services").document("AllServices").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                Log.d("mylogvalue23",""+documentSnapshot.getData());

                if (documentSnapshot.exists())
                {

                    ServiceModel model = documentSnapshot.toObject(ServiceModel.class);






                    ServiceModel[] threadmodel = gson.fromJson(model.getThreading(),ServiceModel[].class);


                    //handle hair cut

                    try {
                        ServiceModel[] hairmodel = gson.fromJson(model.getHaircut(), ServiceModel[].class);


                        for (ServiceModel data : hairmodel) {
                            if (!myhaircut.contains(data.getName() + " (₹" + data.getPrice()+")"));
                            myhaircut.add(data.getName() + " (₹" + data.getPrice()+")");
                        }





                        spHairCut = findViewById(R.id.spHairstyle);
                        ArrayAdapter<String> dataAdapter7 = new ArrayAdapter<String>(Select_Extra_Service.this, R.layout.spinner_color_choose,
                                R.id.tvColorChoose, myhaircut);
                        spHairCut.setAdapter(dataAdapter7);

                        spHairCut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                if (position == 0) {
                                    selected_hair_model = null;



                                    hair_cutting_price = 0;

                                } else {
                                    selected_hair_model = hairmodel[position - 1];




                                    hair_cutting_price = Integer.parseInt(selected_hair_model.getPrice());


                                }

                                mytotal = hair_cutting_price+shave_price+color_price+massage_price+threading_price;

                                if (mytotal > 0) {

                                    btnContinue.setVisibility(View.VISIBLE);
                                    btnContinue.setEnabled(true);
                                    btnContinue.setBackground(getResources().getDrawable(R.drawable.rectangle_gradient_orange));
                                } else {
                                    btnContinue.setVisibility(View.GONE);
                                    btnContinue.setEnabled(true);
                                    btnContinue.setBackground(null);
                                }

                                total_amount.setText("Total ₹ "+mytotal);


                                Log.d("mylogvalue3", "" + selected_hair_model);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                    }catch (Exception e)
                    {

                    }







                    //Spinner shave model



                    try {

                        ServiceModel[] shavemodel = gson.fromJson(model.getShave(),ServiceModel[].class);



                        for (ServiceModel data : shavemodel) {
                            if (!myshave.contains(data.getName() + " (₹" + data.getPrice()+")"));
                            myshave.add(data.getName() + " (₹" + data.getPrice()+")");
                        }
                        ;
                        ArrayAdapter<String> dataAdapter8 = new ArrayAdapter<String>(Select_Extra_Service.this, R.layout.spinner_color_choose,
                                R.id.tvColorChoose, myshave);
                        spShaving.setAdapter(dataAdapter8);



                        spShaving.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {




                                if (position == 0) {
                                    selected_shave_model = null;



                                    shave_price = 0;

                                } else {
                                    selected_shave_model = shavemodel[position - 1];



                                    shave_price = Integer.parseInt(selected_shave_model.getPrice());


                                }




                                mytotal = hair_cutting_price+shave_price+color_price+massage_price+threading_price;
                                total_amount.setText("Total ₹ "+mytotal);

                                Log.d("mylogvalueshave",""+shave_price+mytotal);

                                Log.d("mylogvalue3", "" + selected_shave_model);

                                if (mytotal > 0) {

                                    btnContinue.setVisibility(View.VISIBLE);
                                    btnContinue.setEnabled(true);
                                    btnContinue.setBackground(getResources().getDrawable(R.drawable.rectangle_gradient_orange));
                                } else {
                                    btnContinue.setVisibility(View.GONE);
                                    btnContinue.setEnabled(true);
                                    btnContinue.setBackground(null);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }catch (Exception e)
                    {

                    }



//                      Addd Color model



                    try {
                        ServiceModel[] colormodel = gson.fromJson(model.getColor(),ServiceModel[].class);



                        for (ServiceModel data : colormodel) {
                            if (!mycolor.contains(data.getName() + " (₹" + data.getPrice()+")"));
                            mycolor.add(data.getName() + " (₹" + data.getPrice()+")");
                        }
                        ;
                        ArrayAdapter<String> dataAdapter9 = new ArrayAdapter<String>(Select_Extra_Service.this, R.layout.spinner_color_choose,
                                R.id.tvColorChoose, mycolor);
                        spColor.setAdapter(dataAdapter9);



                        spColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {




                                if (position == 0) {
                                    selected_color_model = null;



                                    color_price = 0;

                                } else {
                                    selected_color_model = colormodel[position - 1];



                                    color_price = Integer.parseInt(selected_color_model.getPrice());


                                }




                                mytotal = hair_cutting_price+shave_price+color_price+massage_price+threading_price;
                                total_amount.setText("Total ₹ "+mytotal);

                                Log.d("mylogvalueshave",""+shave_price+mytotal);

                                Log.d("mylogvalue3", "" + selected_shave_model);

                                if (mytotal > 0) {

                                    btnContinue.setVisibility(View.VISIBLE);
                                    btnContinue.setEnabled(true);
                                    btnContinue.setBackground(getResources().getDrawable(R.drawable.rectangle_gradient_orange));
                                } else {
                                    btnContinue.setVisibility(View.GONE);
                                    btnContinue.setEnabled(true);
                                    btnContinue.setBackground(null);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                    }catch ( Exception e)
                    {

                    }



                    //--------------------------------------handle massage



                    try {
                        ServiceModel[] massagemodel = gson.fromJson(model.getMassage(),ServiceModel[].class);


                        for (ServiceModel data : massagemodel) {
                            if (!mymassage.contains(data.getName() + " (₹" + data.getPrice()+")"));
                            mymassage.add(data.getName() + " (₹" + data.getPrice()+")");
                        }
                        ;
                        ArrayAdapter<String> dataAdapter10 = new ArrayAdapter<String>(Select_Extra_Service.this, R.layout.spinner_color_choose,
                                R.id.tvColorChoose, mymassage);
                        spMassage.setAdapter(dataAdapter10);



                        spMassage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {




                                if (position == 0) {
                                    selected_massage_model = null;



                                    massage_price = 0;

                                } else {
                                    selected_massage_model = massagemodel[position - 1];



                                    massage_price = Integer.parseInt(selected_massage_model.getPrice());


                                }




                                mytotal = hair_cutting_price+shave_price+color_price+massage_price+threading_price;
                                total_amount.setText("Total ₹ "+mytotal);

                                Log.d("mylogvalueshave",""+shave_price+mytotal);

                                Log.d("mylogvalue3", "" + selected_shave_model);

                                if (mytotal > 0) {

                                    btnContinue.setVisibility(View.VISIBLE);
                                    btnContinue.setEnabled(true);
                                    btnContinue.setBackground(getResources().getDrawable(R.drawable.rectangle_gradient_orange));
                                } else {
                                    btnContinue.setVisibility(View.GONE);
                                    btnContinue.setEnabled(true);
                                    btnContinue.setBackground(null);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });



                    }catch (Exception e)
                    {

                    }




                    //--------------------------------------handle Threading



                    try {
                        for (ServiceModel data : threadmodel) {
                            if (!myThread.contains(data.getName() + " (₹" + data.getPrice()+")"));
                            myThread.add(data.getName() + " (₹" + data.getPrice()+")");
                        }
                        ;
                        ArrayAdapter<String> dataAdapter11 = new ArrayAdapter<String>(Select_Extra_Service.this, R.layout.spinner_color_choose,
                                R.id.tvColorChoose, myThread);
                        spThreading.setAdapter(dataAdapter11);



                        spThreading.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {




                                if (position == 0) {
                                    selected_threading_model = null;



                                    threading_price = 0;

                                } else {
                                    selected_threading_model = threadmodel[position - 1];



                                    threading_price = Integer.parseInt(selected_threading_model.getPrice());


                                }




                                mytotal = hair_cutting_price+shave_price+color_price+massage_price+threading_price;
                                total_amount.setText("Total ₹ "+mytotal);

                                Log.d("mylogvalueshave",""+shave_price+mytotal);

                                Log.d("mylogvalue3", "" + selected_shave_model);

                                if (mytotal > 0) {

                                    btnContinue.setVisibility(View.VISIBLE);
                                    btnContinue.setEnabled(true);
                                    btnContinue.setBackground(getResources().getDrawable(R.drawable.rectangle_gradient_orange));
                                } else {
                                    btnContinue.setVisibility(View.GONE);
                                    btnContinue.setEnabled(true);
                                    btnContinue.setBackground(null);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }catch (Exception e)
                    {

                    }






                    ServiceModel[] serviceModels = gson.fromJson(booking_model.getServices(),ServiceModel[].class);
                    List<ServiceModel> serviceModelList = Arrays.asList(serviceModels);

                    for (ServiceModel serviceModel:serviceModels)
                    {
                        String service = serviceModel.getName()+" (₹"+serviceModel.getPrice()+")";
                        Log.d("myselectedservice",""+serviceModel.getName()+" (₹"+serviceModel.getPrice()+")");

                        if (myhaircut.contains(service)||mycolor.contains(service)||myshave.contains(service)||mymassage.contains(service)||myThread.contains(service))
                        {
                            if (myhaircut.contains(service))
                                spHairCut.setSelection(myhaircut.indexOf(service));

                            if (mycolor.contains(service))
                                spColor.setSelection(mycolor.indexOf(service));

                            if (myshave.contains(service))
                                spShaving.setSelection(myshave.indexOf(service));

                            if (mymassage.contains(service))
                                spMassage.setSelection(mymassage.indexOf(service));


                            if (myThread.contains(service))
                                spThreading.setSelection(myThread.indexOf(service));


                        }


                    }



                }
                else
                {
                    Toast.makeText(Select_Extra_Service.this, "No Service Available", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.d("mylogvalue2", "" + e.getMessage());


            }
        });


    }



}