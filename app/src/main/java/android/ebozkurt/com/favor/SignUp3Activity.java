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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUp3Activity extends ActivityHelper {

    //TextView termsOfConditions;
    ImageButton actionBarBack;
    TextView actionBarCancelTextView, passwordToggleTextView;
    Button signUp;
    View actionBarBackground1, actionBarBackground2, actionBarBackground3, actionBarBackground4;
    TextInputLayout passwordTextInputLayout;
    EditText passwordEditText;
    //Animation shake;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3);
        ActivityHelper.initialize(this);

        actionBarBackground1 = (View) findViewById(R.id.sign_up_action_bar_background_view1);
        actionBarBackground2 = (View) findViewById(R.id.sign_up_action_bar_background_view2);
        actionBarBackground3 = (View) findViewById(R.id.sign_up_action_bar_background_view3);
        actionBarBackground4 = (View) findViewById(R.id.sign_up_action_bar_background_view4);
        actionBarBackground1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        actionBarBackground2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        actionBarBackground3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        passwordEditText = (EditText) findViewById(R.id.activity_sign_up3_password_editText);
        passwordTextInputLayout = (TextInputLayout) findViewById(R.id.activity_sign_up3_password_text_input_layout);

        actionBarCancelTextView = (TextView) findViewById(R.id.sign_up1_action_bar_right_text_view);
        actionBarCancelTextView.setText(R.string.cancel);
        actionBarCancelTextView.setVisibility(View.VISIBLE);


        passwordToggleTextView = (TextView) findViewById(R.id.activity_sign_up3_password_toggle_editText);
        passwordToggleTextView.bringToFront();
        passwordToggleTextView.setVisibility(View.INVISIBLE);
        passwordEditText.setTransformationMethod(null);

        //shake = AnimationUtils.loadAnimation(this, R.anim.button_shake_animation);


        passwordTextInputLayout.setError(getString(R.string.passwords_must_be));
        //passwordTextInputLayout.setErrorTextAppearance(R.style.SignUpTextInputLayoutErrorInfo);

        actionBarBack = (ImageButton) findViewById(R.id.sign_up1_action_bar_image_button);
        /*
        String formattedTermsOfConditions = String.format(getResources().getString(R.string.by_proceeding_you), getResources().getString(R.string.app_name));
        termsOfConditions = (TextView) findViewById(R.id.activity_sign_up3_terms_and_conditions_textView);
        termsOfConditions.setText(Html.fromHtml(formattedTermsOfConditions));
        termsOfConditions.setMovementMethod(LinkMovementMethod.getInstance());
*/
        signUp = (Button) findViewById(R.id.activity_sign_up3_sign_up_button);
        signUp.setEnabled(false);
        //termsOfConditions.setText(formattedTermsOfConditions);



        actionBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.right1, R.anim.right2);
            }
        });

        actionBarCancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp3Activity.this, InitialActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                //slide down
                overridePendingTransition(R.anim.down1, R.anim.down2);
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
                if (PasswordChecker.enableButtonifOK(passwordEditText.getText().toString(), 6, 1, 1)) {
                    signUp.setEnabled(true);
                } else signUp.setEnabled(false);
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

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if (!PasswordChecker.enableButtonifOK(passwordEditText.getText().toString(), 6, 1, 1)) {
                    signUp.startAnimation(shake);
                } else {
*/
                    Intent i = new Intent(SignUp3Activity.this, SignUp4Activity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(i);
              //  }
            }
        });
    }


}
