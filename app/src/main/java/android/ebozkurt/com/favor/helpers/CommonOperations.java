package android.ebozkurt.com.favor.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.ebozkurt.com.favor.HomeActivity;
import android.ebozkurt.com.favor.R;
import android.ebozkurt.com.favor.domain.User;

/**
 * Created by erdem on 26.10.2017.
 */

public class CommonOperations {

    public static String getAccessToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.__sp_key), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.__sp_access_token), "");
    }

    public static void saveUserInfo(Context context, User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.__sp_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.__sp_access_token), getAccessToken(context));
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
        editor.apply();
    }

    public static User getUserInfo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.__sp_key), Context.MODE_PRIVATE);
        User user = new User();
        user.setName(sharedPreferences.getString(context.getString(R.string.__sp_user_name), ""));
        user.setLastname(sharedPreferences.getString(context.getString(R.string.__sp_user_lastname), ""));
        user.setEmail(sharedPreferences.getString(context.getString(R.string.email), ""));
        user.setPoints(sharedPreferences.getInt(context.getString(R.string.__sp_user_point), 0));
        user.setActiveEventCount(sharedPreferences.getInt(context.getString(R.string.__sp_user_active_event_count), 0));
        user.setRating(Double.parseDouble(sharedPreferences.getString(context.getString(R.string.__sp_user_rating), "")));
        return user;
    }
}