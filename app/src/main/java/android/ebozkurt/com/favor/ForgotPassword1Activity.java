package android.ebozkurt.com.favor;

import android.content.Context;
import android.content.Intent;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForgotPassword1Activity extends ActivityHelper {

    Animation shake;

    TextView topText;
    Button sendMailButton;
    TextView topRightText;
    ImageButton backImageButton;
    EditText emailEditText; //TODO check validity of email address
    TextInputLayout emailTextInputLayout;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password1);
        ActivityHelper.initialize(this);
        ActivityHelper.hideKeyboardWhenEdittextNotFocused(getWindow().getDecorView().getRootView(), ForgotPassword1Activity.this);
        ActivityHelper.transparentStatusBar(getWindow(), getResources());


        shake = AnimationUtils.loadAnimation(this, R.anim.button_shake_animation);


        topText = (TextView) findViewById(R.id.sign_up1_action_bar_middle_text_view);
        topText.setText(R.string.forgot_password);
        topRightText = (TextView) findViewById(R.id.sign_up1_action_bar_right_text_view);
        topRightText.setText(R.string.cancel);
        backImageButton = (ImageButton) findViewById(R.id.sign_up1_action_bar_image_button);
        backImageButton.setVisibility(View.INVISIBLE);
        sendMailButton = (Button) findViewById(R.id.activity_forgot_password1_send_mail_button);
        sendMailButton.setEnabled(false);

        emailEditText = (EditText) findViewById(R.id.activity_forgot_password1_email_editText);
        emailTextInputLayout = (TextInputLayout) findViewById(R.id.activity_forgot_password1_email_text_input_layout);
/*
        emailEditText.setFilters(new InputFilter[] {
                new InputFilter.AllCaps() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        return String.valueOf(source).toLowerCase(Locale.ENGLISH);
                    }
                }
        });
        */

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()) {
                    emailTextInputLayout.setError(null);
                    emailTextInputLayout.setErrorEnabled(false);
                    sendMailButton.setEnabled(true);
                } else sendMailButton.setEnabled(false);

                if (emailEditText.getText().length() == 0) {
                    emailTextInputLayout.setError(null);
                    emailTextInputLayout.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        sendMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ForgotPassword1Activity.this, ForgotPassword2Activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
                //todo if error
                //sendMailButton.setAnimation(shake);
            }
        });

        topRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                //slide down
                overridePendingTransition(R.anim.down1, R.anim.down2);
            }
        });
    }
}
