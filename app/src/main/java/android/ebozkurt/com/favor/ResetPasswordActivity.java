package android.ebozkurt.com.favor;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class ResetPasswordActivity extends AppCompatActivity {

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
                enableButtonifOK();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordToggleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordToggleState();
            }
        });

        generatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ResetPasswordActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });

        actionBarRightTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ResetPasswordActivity.this, InitialActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
    }

    public void enableButtonifOK() {
        //s ppassword edit text
        CharSequence s = passwordEditText.getText().toString();
        if (s.length() >= 6) {
            int digitCounter = 0;
            int letterCounter = 0;

            for (int i = 0; i < s.length(); i++) {
                if (Character.isLetter(s.charAt(i))) {
                    letterCounter++;
                    //Log.i("letter counter", Integer.toString(letterCounter));
                } else if (Character.isDigit(s.charAt(i))) {
                    digitCounter++;
                    //Log.i("digit counter", Integer.toString(digitCounter));

                }
            }
            if (digitCounter > 0 && letterCounter > 0) {
                generatePasswordButton.setEnabled(true);
            } else generatePasswordButton.setEnabled(false);

        } else generatePasswordButton.setEnabled(false);
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



         /*
        //for password mode
        passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
        //for clear mode
        passwordEditText.setTransformationMethod(null);
        */


    }
}
