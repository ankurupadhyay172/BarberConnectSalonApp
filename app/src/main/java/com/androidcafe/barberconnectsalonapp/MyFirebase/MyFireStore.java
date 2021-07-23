package com.androidcafe.barberconnectsalonapp.MyFirebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidcafe.barberconnectsalonapp.Common;
import com.androidcafe.barberconnectsalonapp.MainActivity;
import com.androidcafe.barberconnectsalonapp.Model.BookingModel;
import com.androidcafe.barberconnectsalonapp.Model.CancelModel;
import com.androidcafe.barberconnectsalonapp.Model.Model_Barber_Details;
import com.androidcafe.barberconnectsalonapp.Model.SalonModel;
import com.androidcafe.barberconnectsalonapp.Model.ServiceModel;
import com.google.android.gms.common.api.Batch;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyFireStore {


    Context context;
    FirebaseFirestore db;

    public MyFireStore(Context context) {
        this.context = context;
        db = FirebaseFirestore.getInstance();
    }



    public void getBarbers(String salonid,String city,OnGetBarbers onGetBarbers)
    {
        db.collection("BarberShops").document(city).collection("Shops").document(salonid).collection("Barbers").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<Model_Barber_Details> list = new ArrayList<>();
                if (!queryDocumentSnapshots.isEmpty())
                {
                    for (QueryDocumentSnapshot document:queryDocumentSnapshots)
                    {
                        Model_Barber_Details model_barber_details = document.toObject(Model_Barber_Details.class);
                        model_barber_details.setId(document.getId());
                        list.add(model_barber_details);

                    }
                    onGetBarbers.onGetBarberlist(list);

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }


    public void getCancelled(String salon_id,OnGetCancel onGetCancel)
    {
        db.collection("Cancel_Orders").whereEqualTo("salon_id",salon_id).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error!=null)
                {

                }else
                {

                    List<CancelModel> list = new ArrayList<>();
                    for (DocumentSnapshot document:value)
                    {
                        CancelModel model = document.toObject(CancelModel.class);
                        model.setId(document.getId());
                        list.add(model);
                    }
                    onGetCancel.onGetCancel(list);
                }


            }
        });



    }

    public void getBookings(String salon_id,String where,OnGetBookings onGetBookings)
    {






        db.collection("AllBookings").whereEqualTo("salon_id",salon_id).whereEqualTo("booking_status",where).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                List<BookingModel> list = new ArrayList<>();

                    if (!value.isEmpty())
                    {

                        for (DocumentSnapshot document:value)
                        {
                            BookingModel  model = document.toObject(BookingModel.class);
                            model.setId(document.getId());


                            if (model.getBooking_status().equals("Booked"))
                            {

                                String inputPattern = "dd_MM_yyyy HH:mm a";
                                String outputPattern = "dd_MM_yyyy h:mm a";
                                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);


                                String input_format = model.getSlot_date()+" "+model.getEnd_time();


                                try {
                                    Date parse = outputFormat.parse(input_format);
                                    Date now = Calendar.getInstance().getTime();

                                    if (parse.compareTo(now)<1)
                                    {
                                        Log.d("compare2",""+parse+"\n"+now);


                                        WriteBatch batch = db.batch();

                                        Map<String,Object> todo = new HashMap<>();
                                        todo.put("booking_status","Cancelled");

                                        DocumentReference barber_doc =db.collection(Common.BARBER_SHOPS).document(model.getCity()).collection(Common.ALL_SHOPS).document(model.getSalon_id()).collection("Barbers").document(model.getBarber_id()).collection(model.getSlot_date()).document(String.valueOf(model.getSlot_no()));
                                        batch.update(barber_doc,todo);


                                        DocumentReference user_doc = db.collection("Users").document(model.getUser_id()).collection("Booking").document(model.getId());
                                        batch.update(user_doc,todo);


                                        DocumentReference booking_doc = db.collection("AllBookings").document(model.getId());
                                        batch.update(booking_doc,todo);



                                        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {


                                            }
                                        });






                                    }
                                    else
                                    {
                                        Log.d("compare3",""+parse+"\n"+now);
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                            }







                            list.add(model);
                        }


                    }
                onGetBookings.onGetBookings(list);



            }
        });


    }


    public void updateToken(String token,String city,String salon_id)
    {
        db.collection("BarberShops").document(city).collection("Shops").document(salon_id).update("token",token).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

    }


    public void getSalonProfile(String salon_id,OnGetProfile onGetProfile)
    {

        db.collection("BarberShops").document("Dungarpur").collection("Shops").document(salon_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists())
                {
                    SalonModel model = documentSnapshot.toObject(SalonModel.class);
                    model.setId(documentSnapshot.getId());
                    onGetProfile.onGetProfile(model);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }





    public void getService(String city,String salon_id,OnGetService onGetService)
    {
        db.collection("BarberShops").document(city).collection("Shops").document(salon_id).collection("Services").document("AllServices").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error!=null)
                {

                }else
                {

                    ServiceModel model = value.toObject(ServiceModel.class);
                    onGetService.onGetServices(model);

                }

            }
        });
    }

    public interface OnGetProfile
    {
        public void onGetProfile(SalonModel salonModel);
    }

    public interface OnGetBookings
    {
        public void onGetBookings(List<BookingModel> bookingModelList);
    }
    public interface OnGetBarbers
    {
        public void onGetBarberlist(List<Model_Barber_Details> list);
    }


    public interface OnGetCancel
    {
        public void onGetCancel(List<CancelModel> cancelModels);
    }

    public interface OnGetService
    {
        public void onGetServices(ServiceModel serviceModel);
    }

}
