package com.androidcafe.barberconnectsalonapp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcafe.barberconnectsalonapp.Common;
import com.androidcafe.barberconnectsalonapp.Model.Model_Add_Service;
import com.androidcafe.barberconnectsalonapp.Model.SalonModel;
import com.androidcafe.barberconnectsalonapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

public class AddServiceFragment extends Fragment {


    Button addservice,shave,color,massage,threading;
    View view1;
    int id = 0;
    List<Model_Add_Service> list;
    EditText editText1,editText2;
    EditText editTextD1,editTextD2;
    Model_Add_Service modelclass2;
    Gson gson;
    String data,sname;
    FirebaseFirestore firestore;
    Map<String, Object> todo;



    List<EditText> editTexts = new ArrayList<>();
    List<EditText> editPrices = new ArrayList<>();
    public AddServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_service, container, false);

        list = new ArrayList<>();

        addservice = view.findViewById(R.id.addServiceshaircut);
        shave = view.findViewById(R.id.addServicesShave);
        color = view.findViewById(R.id.addServicesColour);
        massage = view.findViewById(R.id.addServicesMassage);
        threading = view.findViewById(R.id.addServicesThreading);
        firestore = FirebaseFirestore.getInstance();

        //click add Service in haircut
        addservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sname = "haircut";
                addServicesMethod(sname);

            }
        });


        // click add Services in Shave
        shave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sname = "shave";
                addServicesMethod(sname);

            }
        });

        //Click add Services in Color
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sname = "color";
                addServicesMethod(sname);

            }
        });


        //Click add Services in massage
        massage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sname = "massage";
                addServicesMethod(sname);

            }
        });

        // Click add Services in Threading

        threading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sname = "threading";
                addServicesMethod(sname);

            }
        });

        return view;
    }

    private void addServicesMethod(String mainServiceName) {

        todo = new HashMap<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Add Services in "+mainServiceName);

        view1 = LayoutInflater.from(getContext()).inflate(R.layout.addservicealert, null, false);

        Button add = view1.findViewById(R.id.addedittxt);


        Button submitService = view1.findViewById(R.id.Submit);
        editTextD1 = view1.findViewById(R.id.services_name);
        editTextD2 = view1.findViewById(R.id.services_price);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Create EditText", Toast.LENGTH_SHORT).show();
                addEditText();

            }
        });


        submitService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gson = new Gson();
                list.clear();




                list.add(new Model_Add_Service(editTextD1.getText().toString(),editTextD2.getText().toString()));
                for (int i=0;i<editTexts.size();i++)
                {
                    if (Common.isEmpty(editTexts.get(i)))
                    {
                        editTexts.get(i).setError("Please enter value");
                    }
                    else
                    {
                        String str = editTexts.get(i).getText().toString();
                        String price = editPrices.get(i).getText().toString();


                        modelclass2 = new Model_Add_Service(str,price);
                        list.add(modelclass2);
                        data = gson.toJson(list);


                        todo.put(mainServiceName,data);

                    }


                }



                SalonModel salonModel =Paper.book().read("salon_model");


//                firestore.collection("BarberShops").document(salonModel.getCity()).collection("Shops")
//                        .document(salonModel.getId()).collection("Services")
//                        .document("AllServices").update(todo).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//
//                        Toast.makeText(getContext(), "Add Services Successfully", Toast.LENGTH_SHORT).show();
//                        list.clear();
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                        Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                });



                for (int i=0;i<editTexts.size();i++)
                {
                    if (Common.isEmpty(editTexts.get(i))||Common.isEmpty(editPrices.get(i)))
                    {
                        editTexts.get(i).setError("Please add value");
                        editPrices.get(i).setError("Please enter price");
                    }
                   // Log.d("mylogvalue","Service "+list.get(i).getName()+"\nPrice"+list.get(i).getPrice());

                }
            }
        });

        builder.setView(view1);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();


    }




    private void addEditText() {

        LinearLayout editTextLayout = view1.findViewById(R.id.linearalert);

         editText1 = new EditText(getContext());
         editText2 = new EditText(getContext());

        editText1.setHint("Write here Service ");
//        editText1.
        editText2.setHint("Price");



        editText1.setId(id++);
        editTextLayout.addView(editText1);


        editTexts.add(editText1);

        editText2.setId(id++);

        editTextLayout.addView(editText2);

        //editTexts.add(editText2);
        editPrices.add(editText2);

    }




}