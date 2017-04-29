package android.ebozkurt.com.favor;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUp2Activity extends ActivityHelper {

    ImageButton actionBarBack;
    EditText email, birthdate;
    TextInputLayout emailTextInputLayout, birthdateTextInputLayout;
    Button nextButton;
    View actionBarBackground1, actionBarBackground2, actionBarBackground3, actionBarBackground4;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        ActivityHelper.initialize(this);

        actionBarBackground1 = (View) findViewById(R.id.sign_up_action_bar_background_view1);
        actionBarBackground2 = (View) findViewById(R.id.sign_up_action_bar_background_view2);
        actionBarBackground3 = (View) findViewById(R.id.sign_up_action_bar_background_view3);
        actionBarBackground4 = (View) findViewById(R.id.sign_up_action_bar_background_view4);
        actionBarBackground1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        actionBarBackground2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));


        actionBarBack = (ImageButton) findViewById(R.id.sign_up1_action_bar_image_button);
        email = (EditText) findViewById(R.id.activity_sign_up2_email_editText);
        birthdate = (EditText) findViewById(R.id.activity_sign_up2_birthdate_editText);
        emailTextInputLayout = (TextInputLayout) findViewById(R.id.activity_sign_up2_email_text_input_layout);
        birthdateTextInputLayout = (TextInputLayout) findViewById(R.id.activity_sign_up2_birthdate_text_input_layout);
        nextButton = (Button) findViewById(R.id.activity_sign_up2_next_button);
        //mailBoxButton.setEnabled(false); //TODO enable this after testing

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

                if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    emailTextInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        birthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                        emailTextInputLayout.setError(getString(R.string.invalid_email_address));
                    } else
                        emailTextInputLayout.setError(null);

                }
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

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp2Activity.this,SignUp3Activity.class);
                startActivity(i);
            }
        });

        emailTextInputLayout.setError("demo error");
    }
}
