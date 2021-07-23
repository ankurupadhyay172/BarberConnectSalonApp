package com.androidcafe.barberconnectsalonapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import com.androidcafe.barberconnectsalonapp.Notifications.FCMResponse;
import com.androidcafe.barberconnectsalonapp.Notifications.FCMSendData;
import com.androidcafe.barberconnectsalonapp.Notifications.IFCMApi;
import com.androidcafe.barberconnectsalonapp.Notifications.RetrofitClient;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class Common {

    public static int Choose_Gallery = 100;
    public static int Request_Gallery = 1000;
    public static String Image_Folder = "Profile_Images";
    public static String BARBER_SHOPS = "BarberShops";
    public static String ALL_SHOPS = "Shops";
    public static String SLOTS = "Slots";
    public static String DB_USERS="Users";

    public static String BARBERS = "Barbers";

    public static String SERVICES = "Hair Cut";

    public static String Notification_Db ="Notification";
    public static FirebaseAuth myAuth;
    public static String Verification_id;

    public static String SP_LOGIN = "login";

    public static String db_user ="Users";

    public static String SP_USER_NAME = "name";
    public static String SP_USER_IMAGE = "image";
    public static String SP_USER_ID = "mobile";
    public static String SP_ISLOGIN = "islogin";
    public static String SP_EMAIL = "email";
    public static String SP_JSON = "user_json";


    public static boolean isConnected(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null&&networkInfo.isConnected();

    }

    public static boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }


    public static boolean isEmailValid(CharSequence email)
    {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }







    public static void sendnotificationmethod(String title, String body, CompositeDisposable compositeDisposable, String category) {






        IFCMApi ifcmApi = RetrofitClient.getInstance().create(IFCMApi.class);
        Map<String, String> notiData = new HashMap<>();
        notiData.put("title", title);
        notiData.put("body", body);

        FCMSendData sendData = new FCMSendData();
//        sendData.setTo("/topics/"+school_code+category);

        sendData.setTo("/"+category);

        sendData.setData(notiData);

        Log.d("mytest","send category");

        compositeDisposable.add(ifcmApi.sendNotification(sendData).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FCMResponse>() {
                    @Override
                    public void accept(FCMResponse fcmResponse) throws Exception {

                        if (fcmResponse.getSuccess() == 1) {
                        } else {
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));


    }


    //------------------------------------------------get time in minutes

    public static CharSequence gethours(long timestamp) {
        long time = timestamp * 1000L;
        long now = System.currentTimeMillis();

        CharSequence relativetime = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE);


        return relativetime;
    }


    public static  String getDateFromFireStore(Timestamp timestamp)
    {
        String mydate = DateFormat.getDateInstance().format(timestamp.toDate());


        return mydate;


    }

}
