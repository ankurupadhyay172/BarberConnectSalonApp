package com.androidcafe.barberconnectsalonapp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcafe.barberconnectsalonapp.Adapter.SuggestedProductAdapter;
import com.androidcafe.barberconnectsalonapp.AddServicesActivity;
import com.androidcafe.barberconnectsalonapp.Common;
import com.androidcafe.barberconnectsalonapp.Model.Model_Add_Service;
import com.androidcafe.barberconnectsalonapp.Model.SalonModel;
import com.androidcafe.barberconnectsalonapp.Model.ServiceModel;
import com.androidcafe.barberconnectsalonapp.Model.SuggestedProductModel;
import com.androidcafe.barberconnectsalonapp.MyFirebase.MyFireStore;
import com.androidcafe.barberconnectsalonapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

public class AddMyServiceFragment extends Fragment implements MyFireStore.OnGetService, SuggestedProductAdapter.OnGetData {


    public AddMyServiceFragment() {
        // Required empty public constructor
    }


    RecyclerView recyclerView;
    /*------ Variable Define -------*/
    ImageView imgBack,imgSearch;
    TextView tvTitle;

    /*Suggested Product is here*/
    private SuggestedProductAdapter suggestedProductAdapter;


    private Integer ivProduct[] = {R.drawable.haircutimage,R.drawable.shave,R.drawable.color,R.drawable.massage,R.drawable.threading};
    private String tvProductname[] = {"Hair Cut","Shave","Color" , "Massage","Threading"};
    private String title[] = {"haircut","shave","color","massage","threading"};



    MyFireStore myFireStore;




    View view1;
    int id = 0;

    EditText editText1,editText2;
    EditText editTextD1,editTextD2;
    Model_Add_Service modelclass2;
    Gson gson;
    String data,sname;
    FirebaseFirestore db;


    List<EditText> editTexts = new ArrayList<>();
    List<EditText> editPrices = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_my_service, container, false);



        Paper.init(getActivity());
        recyclerView = view.findViewById(R.id.recyclerview);

        db = FirebaseFirestore.getInstance();
        myFireStore = new MyFireStore(getActivity());
        /*-------Status Color Code To Change--------*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.main_color));
        }



        SalonModel salonModel =Paper.book().read("salon_model");

        myFireStore.getService(salonModel.getCity(),salonModel.getId(),this);








        return view;
    }

    @Override
    public void onGetServices(ServiceModel serviceModel) {
         ArrayList<SuggestedProductModel> suggestedProductModelArrayList = new ArrayList<>();

        for (int i = 0; i < ivProduct.length; i++) {
            if (i==0)
            {
                SuggestedProductModel model = new SuggestedProductModel(ivProduct[i],tvProductname[i],title[i],serviceModel.getHaircut());
                suggestedProductModelArrayList.add(model);
            }if (i==1)
            {
                SuggestedProductModel model = new SuggestedProductModel(ivProduct[i],tvProductname[i],title[i],serviceModel.getShave());
                suggestedProductModelArrayList.add(model);
            }if (i==2)
            {
                SuggestedProductModel model = new SuggestedProductModel(ivProduct[i],tvProductname[i],title[i],serviceModel.getColor());
                suggestedProductModelArrayList.add(model);
            }
            if (i==3)
            {
                SuggestedProductModel model = new SuggestedProductModel(ivProduct[i],tvProductname[i],title[i],serviceModel.getMassage());
                suggestedProductModelArrayList.add(model);
            }if (i==4)
            {
                SuggestedProductModel model = new SuggestedProductModel(ivProduct[i],tvProductname[i],title[i],serviceModel.getThreading());
                suggestedProductModelArrayList.add(model);
            }


        }
        suggestedProductAdapter = new SuggestedProductAdapter(getActivity(), suggestedProductModelArrayList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());



        recyclerView.setAdapter(suggestedProductAdapter);

        Log.d("myallservice",""+serviceModel.getHaircut());
    }

    @Override
    public void onGetData(int pos) {




        addServicesMethod(title[pos]);


//        Intent intent = new Intent(getActivity(), AddServicesActivity.class);
//        intent.putExtra("service",title[pos]);
//        startActivity(intent);

    }











    private void addEditText() {

        LinearLayout editTextLayout = view1.findViewById(R.id.linearalert);

        editText1 = new EditText(getContext());
        editText2 = new EditText(getContext());

        editText1.setHint("Write here Service ");
//        editText1.
        editText2.setHint("Price");



        editText1.setId(id++);
        editText1.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        editTextLayout.addView(editText1);


        editTexts.add(editText1);

        editText2.setId(id++);
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER);

        editTextLayout.addView(editText2);

        //editTexts.add(editText2);
        editPrices.add(editText2);

    }



    private void addServicesMethod(String mainServiceName) {


        Log.d("mylogvalueService",""+mainServiceName);


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        AlertDialog optionDialog = builder.create();

        builder.setMessage("Add Services in "+mainServiceName);



        view1 = LayoutInflater.from(getContext()).inflate(R.layout.addservicealert, null, false);

        Button add = view1.findViewById(R.id.addedittxt);


        Button submitService = view1.findViewById(R.id.Submit);
        editTextD1 = view1.findViewById(R.id.services_name);
        editTextD2 = view1.findViewById(R.id.services_price);

        builder.setView(view1);

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



                if (Common.isEmpty(editTextD1)||Common.isEmpty(editTextD2))
                {

                    if (Common.isEmpty(editTextD1))
                    {
                        editTextD1.setError("Please Enter Value");
                    }
                    if (Common.isEmpty(editTextD2))
                    {
                        editTextD2.setError("Please Enter Price");
                    }
                }
                else
                {



                    SalonModel salonModel =Paper.book().read("salon_model");



                    if (editTexts.size()>0)
                    {
                        List<Model_Add_Service> list= new ArrayList<>();
                        list.add(new Model_Add_Service(editTextD1.getText().toString(),editTextD2.getText().toString()));

                        for (int i=0;i<editTexts.size();i++)
                        {
                            if (Common.isEmpty(editTexts.get(i))||Common.isEmpty(editPrices.get(i))||Common.isEmpty(editTextD1)||Common.isEmpty(editTextD2))
                            {
                                if (Common.isEmpty(editTexts.get(i)))
                                {
                                    editTexts.get(i).setError("Please add value");

                                }
                                if (Common.isEmpty(editPrices.get(i)))
                                    editPrices.get(i).setError("Please enter price");
                            }
                            else
                            {
                                Map<String, Object> todo=new HashMap<>();
                                String str = editTexts.get(i).getText().toString();
                                String price = editPrices.get(i).getText().toString();


                                modelclass2 = new Model_Add_Service(str,price);
                                list.add(modelclass2);
                                data = gson.toJson(list);


                                todo.put("massage",data);

                                Log.d("myalldata",""+todo);



                                db.collection("BarberShops").document(salonModel.getCity()).collection("Shops")
                                        .document(salonModel.getId()).collection("Services")
                                        .document("AllServices").update(todo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {


                                        Toast.makeText(getContext(), "Add Services Successfully", Toast.LENGTH_SHORT).show();
                                        list.clear();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                            // Log.d("mylogvalue","Service "+list.get(i).getName()+"\nPrice"+list.get(i).getPrice());

                        }

                    }
                    else
                    {
                        List<Model_Add_Service> list= new ArrayList<>();

                        Map<String, Object> todo=new HashMap<>();

                        String str = editTextD1.getText().toString();
                        String price = editTextD2.getText().toString();


                        modelclass2 = new Model_Add_Service(str,price);
                        list.add(modelclass2);
                        data = gson.toJson(list);


                        todo.put(mainServiceName,data);

                        Log.d("myalldata",""+todo);



                        db.collection("BarberShops").document(salonModel.getCity()).collection("Shops")
                                .document(salonModel.getId()).collection("Services")
                                .document("AllServices").update(todo).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                optionDialog.dismiss();

                                Toast.makeText(getContext(), "Add Services Successfully", Toast.LENGTH_SHORT).show();
                                list.clear();


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                    }


                }


                          }
        });


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();


    }

    @Override
    public void onResume() {
        SalonModel salonModel =Paper.book().read("salon_model");

        myFireStore.getService(salonModel.getCity(),salonModel.getId(),this);

        super.onResume();
    }
}