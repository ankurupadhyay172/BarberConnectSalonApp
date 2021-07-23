package com.androidcafe.barberconnectsalonapp.Notifications;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMApi {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAN4nK4Og:APA91bEWE7xA1bH-HpAhvTJfG7GIY2j3LI1gU-t-JxhdXU6unqt34AK3am7Qz87VWgQTDGyMPU0z-rPBdSzWDvPzbCjBRohOcYYs4E9jrIMuJX_5hTSOdaYh8c4n7WHe06tWhrFFHZt9"
    })


    @POST("fcm/send")
    Observable<FCMResponse> sendNotification(@Body FCMSendData body);


}
