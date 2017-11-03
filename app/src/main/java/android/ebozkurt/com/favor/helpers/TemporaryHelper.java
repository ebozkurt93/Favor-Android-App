package android.ebozkurt.com.favor.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.ebozkurt.com.favor.R;
import android.ebozkurt.com.favor.domain.User;
import android.widget.EditText;

/**
 * Created by erdem on 3.11.2017.
 */

public class TemporaryHelper {

    public static void saveLoginInfo(Context context, String email, String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.__sp_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mail", email);
        editor.putString("pass",password);
        editor.apply();
    }

    public static void setLoginInfo(Context context, EditText email, EditText password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.__sp_key), Context.MODE_PRIVATE);
        String emailText = sharedPreferences.getString("mail", "");
        String passText = sharedPreferences.getString("pass", "");
        if (emailText.length() > 1 && passText.length() > 1) {
            email.setText(emailText);
            password.setText(passText);
        }
    }
}
