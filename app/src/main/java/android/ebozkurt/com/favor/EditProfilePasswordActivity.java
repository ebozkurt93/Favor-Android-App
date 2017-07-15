package android.ebozkurt.com.favor;

import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.PasswordChecker;
import android.ebozkurt.com.favor.helpers.PasswordHintToggler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EditProfilePasswordActivity extends AppCompatActivity {

    TextInputLayout currentPasswordTextInputLayout, newPasswordTextInputLayout;
    EditText currentPasswordEditText, newPasswordEditText;
    TextView currentPasswordToggleTextView, newPasswordToggleTextView;
    Button saveButton;

    //actionbar components
    TextView title;
    ImageView back;

    Animation shake;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_password);
        ActivityHelper.initialize(this);

        currentPasswordTextInputLayout = (TextInputLayout) findViewById(R.id.activity_edit_profile_password_current_password_text_input_layout);
        currentPasswordEditText = (EditText) findViewById(R.id.activity_edit_profile_password_current_password_editText);
        currentPasswordToggleTextView = (TextView) findViewById(R.id.activity_edit_profile_password_current_password_toggle_editText);
        newPasswordTextInputLayout = (TextInputLayout) findViewById(R.id.activity_edit_profile_password_new_password_text_input_layout);
        newPasswordTextInputLayout.setError(getResources().getString(R.string.passwords_must_be));
        newPasswordEditText = (EditText) findViewById(R.id.activity_edit_profile_password_new_password_editText);
        newPasswordToggleTextView = (TextView) findViewById(R.id.activity_edit_profile_password_new_password_toggle_editText);
        saveButton = (Button) findViewById(R.id.activity_edit_profile_password_save_button);

        //todo use this on error
        shake = AnimationUtils.loadAnimation(this, R.anim.button_shake_animation);


        title = (TextView) findViewById(R.id.sign_up1_action_bar_middle_text_view);
        title.setText(R.string.edit_password);
        back = (ImageView) findViewById(R.id.sign_up1_action_bar_image_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        currentPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && currentPasswordEditText.getText().toString().length() > 0) {
                    currentPasswordToggleTextView.setVisibility(View.VISIBLE);
                } else {
                    currentPasswordToggleTextView.setVisibility(View.INVISIBLE);
                }
            }
        });

        newPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && newPasswordEditText.getText().toString().length() > 0) {
                    newPasswordToggleTextView.setVisibility(View.VISIBLE);
                } else {
                    newPasswordToggleTextView.setVisibility(View.INVISIBLE);
                }
            }
        });

        currentPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveButtonEnabler();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        newPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveButtonEnabler();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        currentPasswordToggleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordHintToggler.passwordToggleState(currentPasswordEditText, currentPasswordToggleTextView);
            }
        });

        newPasswordToggleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordHintToggler.passwordToggleState(newPasswordEditText, newPasswordToggleTextView);
            }
        });
    }

    private void saveButtonEnabler() {
        if (PasswordChecker.passwordFitsConditions(currentPasswordEditText.getText().toString(), 6, 1, 1) && PasswordChecker.passwordFitsConditions(newPasswordEditText.getText().toString(), 6, 1, 1)) {
            saveButton.setEnabled(true);
        } else saveButton.setEnabled(false);
    }
}
