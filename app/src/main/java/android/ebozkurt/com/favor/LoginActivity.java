package android.ebozkurt.com.favor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    ImageButton backImageButton;
    TextView actionBarCenterText, actionBarRightText;
    Button signInButton;
    TextView termsOfConditions;
    TextInputLayout emailTextInputLayout, passwordTextInputLayout;
    EditText emailEditText, passwordEditText;
    TextView passwordToggleTextView;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityHelper.initialize(this);
        backImageButton = (ImageButton) findViewById(R.id.sign_up1_action_bar_image_button);
        actionBarCenterText = (TextView) findViewById(R.id.sign_up1_action_bar_middle_text_view);
        actionBarCenterText.setText(R.string.login);
        actionBarRightText = (TextView) findViewById(R.id.sign_up1_action_bar_right_text_view);
        actionBarRightText.setText(R.string.forgot_password);
        actionBarRightText.setTypeface(null, Typeface.ITALIC);
        signInButton = (Button) findViewById(R.id.activity_login_sign_in_button);
        signInButton.setEnabled(false);

        termsOfConditions = (TextView) findViewById(R.id.activity_login_terms_and_conditions_textView);
        String formattedTermsOfConditions = String.format(getResources().getString(R.string.by_proceeding_you), getResources().getString(R.string.app_name));
        termsOfConditions.setText(Html.fromHtml(formattedTermsOfConditions));
        termsOfConditions.setMovementMethod(LinkMovementMethod.getInstance());

        emailEditText = (EditText) findViewById(R.id.activity_login_email_editText);
        passwordEditText = (EditText) findViewById(R.id.activity_login_password_editText);
        passwordEditText.setTransformationMethod(null);

        emailTextInputLayout = (TextInputLayout) findViewById(R.id.activity_login_email_text_input_layout);
        passwordTextInputLayout = (TextInputLayout) findViewById(R.id.activity_login_password_text_input_layout);
        passwordToggleTextView = (TextView) findViewById(R.id.activity_login_password_toggle_editText);
        passwordToggleTextView.setVisibility(View.INVISIBLE);

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        actionBarRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ForgotPassword1Activity.class);
                // i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });

        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches() && emailEditText.length() > 0) {
                    emailTextInputLayout.setError(getString(R.string.invalid_email_address));
                }
            }
        });

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()) {
                    emailTextInputLayout.setError(null);
                }
                if (emailEditText.getText().length() == 0) {
                    emailTextInputLayout.setError(null);
                }

                enableButtonifOK();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 1) {
                    passwordToggleTextView.setVisibility(View.VISIBLE);
                }
                enableButtonifOK();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordToggleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("dev", "onClick: ");
                passwordToggleState();
            }
        });
    }

    public void passwordToggleState() {
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

    public void enableButtonifOK() {
        //s ppassword edit text
        CharSequence s = passwordEditText.getText().toString();
        if (s.length() >= 6) {
            int digitCounter = 0;
            int letterCounter = 0;

            for (int i = 0; i < s.length(); i++) {
                if (Character.isLetter(s.charAt(i))) {
                    letterCounter++;
                    Log.i("letter counter", Integer.toString(letterCounter));
                } else if (Character.isDigit(s.charAt(i))) {
                    digitCounter++;
                    Log.i("digit counter", Integer.toString(digitCounter));

                }
            }
            if (digitCounter > 0 && letterCounter > 0 && Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches() && emailEditText.length() > 0) {
                signInButton.setEnabled(true);
            } else signInButton.setEnabled(false);

        } else signInButton.setEnabled(false);
    }


}

