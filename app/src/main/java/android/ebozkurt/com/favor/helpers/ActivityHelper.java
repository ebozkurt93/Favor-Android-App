package android.ebozkurt.com.favor.helpers;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.ebozkurt.com.favor.R;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class ActivityHelper extends AppCompatActivity {

    public static void initialize(Activity activity) {
        //Do all sorts of common task for your activities here including:

        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right1, R.anim.right2);
    }

    public static void hideKeyboardWhenEdittextNotFocused(View view, final Activity activity) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    KeyboardHelper.hideSoftKeyboard(activity);
                    return false;
                }
            });
        }
        //If a layout container, iterate over children
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                hideKeyboardWhenEdittextNotFocused(innerView, activity);
            }
        }
    }

}
