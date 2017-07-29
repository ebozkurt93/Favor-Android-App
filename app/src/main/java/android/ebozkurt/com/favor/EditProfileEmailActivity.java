package android.ebozkurt.com.favor;

import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.PasswordChecker;
import android.ebozkurt.com.favor.helpers.PasswordHintToggler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EditProfileEmailActivity extends AppCompatActivity {

    TextInputLayout emailTextInputLayout, passwordTextInputLayout;
    EditText emailEditText, passwordEditText;
    TextView passwordToggleTextView;
    Button saveButton;

    String email;
    //actionbar components
    TextView title;
    ImageView back;

    Animation shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_email);
        ActivityHelper.initialize(this);
        ActivityHelper.hideKeyboardWhenEdittextNotFocused(getWindow().getDecorView().getRootView(), EditProfileEmailActivity.this);

        emailTextInputLayout = (TextInputLayout) findViewById(R.id.activity_edit_profile_email_email_text_input_layout);
        emailEditText = (EditText) findViewById(R.id.activity_edit_profile_email_email_editText);
        passwordTextInputLayout = (TextInputLayout) findViewById(R.id.activity_edit_profile_email_password_text_input_layout);
        passwordEditText = (EditText) findViewById(R.id.activity_edit_profile_email_password_editText);
        passwordEditText.setTransformationMethod(null);

        passwordToggleTextView = (TextView) findViewById(R.id.activity_edit_profile_email_password_toggle_editText);
        passwordToggleTextView.setVisibility(View.INVISIBLE);
        PasswordHintToggler.passwordToggleState(passwordEditText, passwordToggleTextView);

        saveButton = (Button) findViewById(R.id.activity_edit_profile_email_save_button);


        title = (TextView) findViewById(R.id.sign_up1_action_bar_middle_text_view);
        title.setText(R.string.edit_email);
        back = (ImageView) findViewById(R.id.sign_up1_action_bar_image_button);

        //todo use this on error
        shake = AnimationUtils.loadAnimation(this, R.anim.button_shake_animation);


        //temporary email
        email = "example@boon-app.com";
        emailEditText.setText(email);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches() || emailEditText.getText().length() == 0) {
                    emailTextInputLayout.setError(null);
                    emailTextInputLayout.setErrorEnabled(false);
                    saveButtonConditionChecker();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 1) {
                    passwordToggleTextView.setVisibility(View.VISIBLE);
                    saveButtonConditionChecker();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus && passwordEditText.getText().toString().length() > 0) {
                    passwordToggleTextView.setVisibility(View.VISIBLE);
                } else {
                    passwordToggleTextView.setVisibility(View.INVISIBLE);
                }
            }
        });

        passwordToggleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordHintToggler.passwordToggleState(passwordEditText, passwordToggleTextView);
            }
        });

    }

    public void saveButtonConditionChecker() {
        if (emailEditText.getText().toString().trim().length() > 0 && !emailEditText.getText().toString().equals(email) && Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()
                && PasswordChecker.passwordFitsConditions(passwordEditText.getText().toString(), 6, 1, 1)) {
            saveButton.setEnabled(true);
        } else saveButton.setEnabled(false);

    }
}
