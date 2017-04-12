package android.ebozkurt.com.favor;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class SignUp2Activity extends AppCompatActivity {

    ImageButton actionBarBack;
    EditText email, birthdate;
    TextInputLayout emailTextInputLayout, birthdateTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        actionBarBack = (ImageButton) findViewById(R.id.sign_up1_action_bar_image_button);
        email = (EditText) findViewById(R.id.activity_sign_up2_email_editText);
        birthdate = (EditText) findViewById(R.id.activity_sign_up2_birthdate_editText);
        emailTextInputLayout = (TextInputLayout) findViewById(R.id.activity_sign_up2_email_text_input_layout);
        birthdateTextInputLayout = (TextInputLayout) findViewById(R.id.activity_sign_up2_birthdate_text_input_layout);

        /*
        final CharSequence birthdateHintShort = getString(R.string.birth_date);
        final CharSequence birthdateHintLong = getString(R.string.birth_date_you_must_be_over_18);
        */

        actionBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    emailTextInputLayout.setError(getString(R.string.invalid_email_address));
                } else
                    emailTextInputLayout.setError(null);
            }
        });


/*
//doesnt work properly
        birthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    birthdateTextInputLayout.setHint(birthdateHintShort);
                } else {
                    birthdateTextInputLayout.setHint(birthdateHintLong);
                }
            }
        });
*/
    }
}
