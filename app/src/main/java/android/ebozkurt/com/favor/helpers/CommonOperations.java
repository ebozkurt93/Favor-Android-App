package android.ebozkurt.com.favor.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.ebozkurt.com.favor.HomeActivity;
import android.ebozkurt.com.favor.LoginActivity;
import android.ebozkurt.com.favor.R;
import android.ebozkurt.com.favor.domain.User;
import android.ebozkurt.com.favor.domain.helpers.JSONResponse;
import android.ebozkurt.com.favor.network.BoonApiInterface;
import android.ebozkurt.com.favor.network.RetrofitBuilder;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by erdem on 26.10.2017.
 */

public class CommonOperations {

    public static void saveAccessToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.__sp_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.__sp_access_token), token);
        editor.apply();
    }

    public static String getAccessToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.__sp_key), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.__sp_access_token), "");
    }

    public static void saveUserInfo(Context context, User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.__sp_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putString(context.getString(R.string.__sp_access_token), getAccessToken(context));
        if (user.getName() != null)
            editor.putString(context.getString(R.string.__sp_user_name), user.getName());
        if (user.getLastname() != null)
            editor.putString(context.getString(R.string.__sp_user_lastname), user.getLastname());
        if (user.getEmail() != null)
            editor.putString(context.getString(R.string.__sp_user_email), user.getEmail());
        if (user.getPoints() != null)
            editor.putInt(context.getString(R.string.__sp_user_point), user.getPoints());
        if (user.getActiveEventCount() != null)
            editor.putInt(context.getString(R.string.__sp_user_active_event_count), user.getActiveEventCount());
        if (user.getRating() != null)
            editor.putString(context.getString(R.string.__sp_user_rating), user.getRating().toString());
        if (user.getId() != null)
            editor.putInt(context.getString(R.string.__sp_user_id), user.getId());
        editor.apply();
    }

    public static User getUserInfo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.__sp_key), Context.MODE_PRIVATE);
        User user = new User();
        user.setName(sharedPreferences.getString(context.getString(R.string.__sp_user_name), ""));
        user.setLastname(sharedPreferences.getString(context.getString(R.string.__sp_user_lastname), ""));
        user.setEmail(sharedPreferences.getString(context.getString(R.string.__sp_user_email), ""));
        user.setPoints(sharedPreferences.getInt(context.getString(R.string.__sp_user_point), 0));
        user.setActiveEventCount(sharedPreferences.getInt(context.getString(R.string.__sp_user_active_event_count), 0));
        user.setRating(Double.parseDouble(sharedPreferences.getString(context.getString(R.string.__sp_user_rating), "")));
        user.setId(sharedPreferences.getInt(context.getString(R.string.__sp_user_id), 0));
        return user;
    }

    public static void updateUserInfo(final Context context, final User user) {
        BoonApiInterface apiService = RetrofitBuilder.returnService();
        final String accessToken = CommonOperations.getAccessToken(context);
        Call<JSONResponse> call = apiService.editProfile(accessToken, user);
        final Activity activity = (Activity) context;
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.body().isSuccess()) {
                    saveUserInfo(context, user);
                    activity.finish();
                } else
                    ActivityHelper.DisplayCustomToast(context, context.getResources().getString(R.string.general_error), Toast.LENGTH_LONG);

            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                ActivityHelper.DisplayCustomToast(context, context.getResources().getString(R.string.general_error), Toast.LENGTH_LONG);
            }
        });
    }
}
