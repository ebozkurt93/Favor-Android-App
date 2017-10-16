package android.ebozkurt.com.favor.network;

import android.ebozkurt.com.favor.domain.helpers.EventCreate;
import android.ebozkurt.com.favor.domain.helpers.JSONResponse;
import android.ebozkurt.com.favor.domain.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by erdem on 17.09.2017.
 */

public interface BoonApiInterface {

    @POST("user/register")
    Call<JSONResponse> registerUser(@Body User user);

    @POST("user/login")
    Call<JSONResponse> login(@Body User user);

    @POST("user/isemailregistered")
    Call<JSONResponse> isEmailRegistered(@Body String email);

    @POST("user/secure/getmyinfo")
    Call<JSONResponse> getMyInfo(@Header("Authorization") String authorizationHeader);

    @POST("event/secure/createevent")
    Call<JSONResponse> createEvent(@Header("Authorization") String authorizationHeader, @Body EventCreate eventCreate);

    @GET("event/secure/getallevents/{latitude}/{longitude}")
    Call<JSONResponse> getAllEvents(@Header("Authorization") String authorizationHeader, @Path("latitude") double latitude, @Path("longitude") double longitude);
}
