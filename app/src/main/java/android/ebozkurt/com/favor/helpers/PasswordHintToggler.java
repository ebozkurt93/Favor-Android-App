package android.ebozkurt.com.favor.helpers;

import android.ebozkurt.com.favor.R;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by erdem on 3.05.2017.
 */

public class PasswordHintToggler {

    public static void passwordToggleState(EditText passwordEditText, TextView passwordToggleTextView) {
        int i = passwordEditText.getSelectionStart();
        if (passwordEditText.getTransformationMethod() == null) {
            passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
            passwordToggleTextView.setText(R.string.show);
        } else {
            passwordEditText.setTransformationMethod(null);
            passwordToggleTextView.setText(R.string.hide);
        }
        passwordEditText.setSelection(i);
    }

     /*
        //for password mode
        passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
        //for clear mode
        passwordEditText.setTransformationMethod(null);
     */
}