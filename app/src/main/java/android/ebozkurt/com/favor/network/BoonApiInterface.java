package android.ebozkurt.com.favor.network;

import android.ebozkurt.com.favor.domain.helpers.JSONResponse;
import android.ebozkurt.com.favor.domain.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
}
