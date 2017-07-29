package android.ebozkurt.com.favor;

import android.content.Context;
import android.content.Intent;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.PasswordChecker;
import android.ebozkurt.com.favor.helpers.PasswordHintToggler;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ResetPasswordActivity extends ActivityHelper {

    TextView actionBarMiddleTextView, actionBarRightTextView;
    ImageButton actionBarImageButton;
    TextInputLayout passwordTextInputLayout;
    EditText passwordEditText;
    Button generatePasswordButton;
    TextView passwordToggleTextView;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ActivityHelper.initialize(this);
        ActivityHelper.hideKeyboardWhenEdittextNotFocused(getWindow().getDecorView().getRootView(), ResetPasswordActivity.this);



        actionBarMiddleTextView = (TextView) findViewById(R.id.sign_up1_action_bar_middle_text_view);
        actionBarMiddleTextView.setText(R.string.new_password);
        actionBarRightTextView = (TextView) findViewById(R.id.sign_up1_action_bar_right_text_view);
        actionBarRightTextView.setText(R.string.close);
        actionBarImageButton = (ImageButton) findViewById(R.id.sign_up1_action_bar_image_button);
        actionBarImageButton.setVisibility(View.INVISIBLE);
        passwordTextInputLayout = (TextInputLayout) findViewById(R.id.activity_reset_password_password_text_input_layout);
        passwordTextInputLayout.setError(getResources().getString(R.string.passwords_must_be));
        generatePasswordButton = (Button) findViewById(R.id.activity_reset_password_generate_new_password_button);
        generatePasswordButton.setEnabled(false);
        passwordEditText = (EditText) findViewById(R.id.activity_reset_password_password_editText);
        passwordEditText.setTransformationMethod(null);
        passwordToggleTextView = (TextView) findViewById(R.id.activity_reset_password_password_toggle_textView);
        passwordToggleTextView.bringToFront();
        passwordToggleTextView.setVisibility(View.INVISIBLE);

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 1) {
                    passwordToggleTextView.setVisibility(View.VISIBLE);
                }
                if (PasswordChecker.passwordFitsConditions(passwordEditText.getText().toString(), 6, 1, 1)) {
                    generatePasswordButton.setEnabled(true);
                } else generatePasswordButton.setEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordToggleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordHintToggler.passwordToggleState(passwordEditText, passwordToggleTextView);
            }
        });

        generatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ResetPasswordActivity.this, "Success", Toast.LENGTH_SHORT).show();
                //generatePasswordButton.startAnimation(shake);
            }
        });

        actionBarRightTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ResetPasswordActivity.this, InitialActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                overridePendingTransition(R.anim.down1, R.anim.down2);
            }
        });
    }

}
