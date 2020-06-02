package com.example.mobilesporta.notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAATIFXWDc:APA91bEOpi_w555h9IEw4vrVFnPGoIUXekiGjYlcTZLQCLWvL--j0H6-8lv_N7x7y3LqxWNoEYiQFAjQOZaE11IL4XyreAAnCC19ts0i-AaSvTj5m8EbB7hmguI3Jn0wKRTmRLw7i_99"
    })

    @POST("fcm/send")
    Call<Response> sendNotification (@Body Sender body);
}
