package com.example.uberclone.retrofit;

import com.example.uberclone.Models.FCMBody;
import com.example.uberclone.Models.FCMResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMApi {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAArU8yHYQ:APA91bFtLFzmr6hlrULt1D53DKDD6VtBvtSIZF4Cga1ZhvHIMG8Q8WEkql6aDIvuNgVgCglz5CwO9dvdx66GTr0L0TQbsP9yH5gVkFUNMzsbmiPX4gqLUommesXpGCCsHct0G-MmC-Ig"
    })
    @POST("fcm/send")
    Call<FCMResponse> send(@Body FCMBody body);

}
