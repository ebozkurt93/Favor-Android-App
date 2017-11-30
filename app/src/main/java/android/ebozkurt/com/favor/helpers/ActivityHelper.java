package android.ebozkurt.com.favor.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.ebozkurt.com.favor.R;
import android.ebozkurt.com.favor.views.LoadingDialogFragment;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    public static void transparentStatusBar(Window window, Resources resources) {
        window.setStatusBarColor(resources.getColor(android.R.color.transparent));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

    }

    public static int getTrimmedEditTextLength(EditText editText) {
        return getTrimmedString(editText).length();
    }

    public static String getTrimmedString(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static void DisplayCustomToast(Context context, String message, int length) {
        Toast toast = Toast.makeText(context, message, length);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast_background);
        TextView text = (TextView) view.findViewById(android.R.id.message);
        text.setGravity(Gravity.CENTER);
        text.setTextColor(context.getResources().getColor(R.color.colorAccent));
        int lrPadding = BitmapHelper.dpToPx(context, 12);
        int tbPadding = BitmapHelper.dpToPx(context, 6);
        text.setPadding(lrPadding, tbPadding, lrPadding, tbPadding);

/*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
        toast.show();
    }

    public static void DisplayGeneralErrorToast (Context context) {
        ActivityHelper.DisplayCustomToast(context, context.getResources().getString(R.string.general_error), Toast.LENGTH_LONG);
    }

    public static LoadingDialogFragment getLoadingDialog() {
        LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.setCancelable(false);
        return loadingDialogFragment;
    }

    public static Boolean isNameLastname(String s) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        //[^A-Za-z0-9\s]
        Pattern p = Pattern.compile("[^\\p{L}\\s]");
        Matcher m = p.matcher(s);
        // boolean b = m.matches();

        boolean b = m.find();
        if (b)
            return true;
        else
            return false;
    }

    public static boolean hasInvalidName(Activity activity, EditText editText, TextInputLayout textInputLayout) {
        if (ActivityHelper.isNameLastname(ActivityHelper.getTrimmedString(editText))) {
            textInputLayout.setError(activity.getResources().getString(R.string.invalid_character));
            return true;
        } else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return false;
        }
    }

    public static Calendar stringToDateTime(String stringDateTime) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            cal.setTime(sdf.parse(stringDateTime));// all done
        } catch (ParseException e) {
            return Calendar.getInstance();
            //e.printStackTrace();
        }
        return cal;
    }

}
