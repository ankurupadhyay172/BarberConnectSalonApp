package com.androidcafe.barberconnectsalonapp.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcafe.barberconnectsalonapp.Common;
import com.androidcafe.barberconnectsalonapp.MainActivity;
import com.androidcafe.barberconnectsalonapp.Model.SalonModel;
import com.androidcafe.barberconnectsalonapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import io.paperdb.Paper;

public class Register_MobileNo extends AppCompatActivity implements OtpVerification.OnVerficationComplete {

    Button login;

    EditText mobileno,otp;
    ProgressBar progressBar,progressBar2;

    LinearLayout li_mobile,li_otp;
    TextView error1,error2;
    Button submit_otp;

    FirebaseFirestore db;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__mobile_no);
        sharedPreferences = getSharedPreferences(Common.SP_LOGIN,MODE_PRIVATE);
        editor = sharedPreferences.edit();


        Paper.init(this);

        login = findViewById(R.id.login);
        mobileno = findViewById(R.id.mobile_no);
        progressBar = findViewById(R.id.progressbar);
        li_mobile = findViewById(R.id.li_mobile);
        li_otp = findViewById(R.id.li_otp);
        otp = findViewById(R.id.otp);
        submit_otp = findViewById(R.id.submit_otp);
        error1 = findViewById(R.id.error);
        error2 = findViewById(R.id.error2);
        progressBar2 = findViewById(R.id.progressbar2);


        db = FirebaseFirestore.getInstance();
        OtpVerification otpVerification = new OtpVerification(this,this);






        mobileno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().length()==10)
                {

                    InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);


                    //  getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });




        otp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().length()==6)
                {

                    InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);


                    //  getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });






        submit_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (Common.isConnected(Register_MobileNo.this))
                {

                    error1.setVisibility(View.GONE);
                    progressBar2.setVisibility(View.VISIBLE);
                    otpVerification.verifyCode(otp.getText().toString(), new OtpVerification.OnVerficationComplete() {
                        @Override
                        public void getVerficationStatus(String status) {



                            progressBar2.setVisibility(View.GONE);
                            error2.setVisibility(View.VISIBLE);
                            error2.setText(status);
                            if (status.equals("Successfull"))
                            {




                                    Toast.makeText(Register_MobileNo.this, "Successfully Login", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(Register_MobileNo.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();



                                }
                            else
                            {
                                Toast.makeText(Register_MobileNo.this, "Please enter valid otp or try again", Toast.LENGTH_SHORT).show();
                            }



                            }




                    });


                }
                else
                {
                    error1.setText("Please connect with internet first");
                    error1.setVisibility(View.VISIBLE);
                }

            }
        });





        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.isEmpty(mobileno)||mobileno.getText().toString().trim().length() != 10)
                {
                    mobileno.setError("Enter valid mobile no");
                }else
                {
                    progressBar.setVisibility(View.VISIBLE);



                    db.collection("BarberShops").document("Dungarpur").collection("Shops").whereEqualTo("phone",mobileno.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            if (!queryDocumentSnapshots.isEmpty())
                            {

                                for (QueryDocumentSnapshot document:queryDocumentSnapshots)
                                {
                                    SalonModel model = document.toObject(SalonModel.class);
                                    model.setId(document.getId());

                                    Paper.book().write("salon_model",model);

                                }

                                otpVerification.sendOtp(Register_MobileNo.this,mobileno.getText().toString(),progressBar,li_mobile,li_otp,otp,error1);

                            }
                            else
                            {
                                Toast.makeText(Register_MobileNo.this, "you are not registred in app ! Please contact us for more detail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Register_MobileNo.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });



//                    Intent intent =new Intent(RagistrationHomeActivity.this,OtpVerificationActivity.class);
//                    intent.putExtra("mobile",mobileno.getText().toString());
//                    startActivity(intent);
                }

            }
        });


    }

    @Override
    public void getVerficationStatus(String status) {

        if (status.equals("Successfull"))
        {


            Toast.makeText(this, "Successfully Login", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Register_MobileNo.this, MainActivity.class);
            startActivity(intent);
            finish();



        }
        else
        {
            Toast.makeText(this, "Please enter valid otp or try again", Toast.LENGTH_SHORT).show();
        }



    }
}