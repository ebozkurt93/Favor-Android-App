package android.ebozkurt.com.favor.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.ebozkurt.com.favor.R;
import android.ebozkurt.com.favor.views.LoadingDialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


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

    public static LoadingDialogFragment getLoadingDialog() {
        LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.setCancelable(false);
        return loadingDialogFragment;
    }


}
