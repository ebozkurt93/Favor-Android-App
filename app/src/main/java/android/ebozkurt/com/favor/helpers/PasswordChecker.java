package android.ebozkurt.com.favor.helpers;

import android.widget.EditText;

/**
 * Created by erdem on 3.05.2017.
 */

public class PasswordChecker {


    public static boolean passwordFitsConditions(CharSequence password, int minPasswordLength, int minDigitCount, int minLetterCount ) {
        //s ppassword edit text
        //CharSequence password = passwordEditText.getText().toString();
        if (password.length() >= minPasswordLength) {
            int digitCounter = 0;
            int letterCounter = 0;

            for (int i = 0; i < password.length(); i++) {
                if (Character.isLetter(password.charAt(i))) {
                    letterCounter++;
                    //Log.i("letter counter", Integer.toString(letterCounter));
                } else if (Character.isDigit(password.charAt(i))) {
                    digitCounter++;
                    //Log.i("digit counter", Integer.toString(digitCounter));

                }
            }
            if (digitCounter >= minDigitCount && letterCounter >= minLetterCount) {

                return true;
                // generatePasswordButton.setEnabled(true);
            } else return false;

        } else return false;
    }
}
