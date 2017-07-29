package android.ebozkurt.com.favor;


import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUp1Activity extends ActivityHelper {

    private static final String TAG = SignUp1Activity.class.getName();

    ImageButton actionBarBack;
    Button nextButton;
    EditText name, surname;
    TextInputLayout nameLayout, surnameLayout;
    View actionBarBackground1, actionBarBackground2, actionBarBackground3, actionBarBackground4;  //not all of them are needed
    TextView termsOfConditions;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);
        ActivityHelper.initialize(this);
        ActivityHelper.hideKeyboardWhenEdittextNotFocused(getWindow().getDecorView().getRootView(), SignUp1Activity.this);


        actionBarBack = (ImageButton) findViewById(R.id.sign_up1_action_bar_image_button);
        nextButton = (Button) findViewById(R.id.activity_sign_up1_next_button);
        name = (EditText) findViewById(R.id.activity_sign_up1_name_editText);
        surname = (EditText) findViewById(R.id.activity_sign_up1_surname_editText);
        nameLayout = (TextInputLayout) findViewById(R.id.activity_sign_up1_name_text_input_layout);
        surnameLayout = (TextInputLayout) findViewById(R.id.activity_sign_up1_surname_text_input_layout);
        nextButton.setEnabled(false);
        nextButton.setClipToOutline(true);
        actionBarBackground1 = (View) findViewById(R.id.sign_up_action_bar_background_view1);
        actionBarBackground2 = (View) findViewById(R.id.sign_up_action_bar_background_view2);
        actionBarBackground3 = (View) findViewById(R.id.sign_up_action_bar_background_view3);
        actionBarBackground4 = (View) findViewById(R.id.sign_up_action_bar_background_view4);
        String formattedTermsOfConditions = String.format(getResources().getString(R.string.by_proceeding_you), getResources().getString(R.string.app_name));
        termsOfConditions = (TextView) findViewById(R.id.activity_sign_up1_terms_and_conditions_textView);
        termsOfConditions.setText(Html.fromHtml(formattedTermsOfConditions));
        termsOfConditions.setMovementMethod(LinkMovementMethod.getInstance());

        //for preventing keyboard auto open
        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        actionBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.right1, R.anim.right2);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp1Activity.this, SignUp2Activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1 && surname.getText().length() > 1) {
                    nextButton.setEnabled(true);
                } else {
                    nextButton.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
/*
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    ObjectAnimator animator = ObjectAnimator.ofInt(actionBarBackground1, "backgroundColor", getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimaryDark)).setDuration(1000);
                    animator.setEvaluator(new ArgbEvaluator());
                    animator.start();
                }
            }
        });
*/

        surname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1 && name.getText().length() > 1) {
                    nextButton.setEnabled(true);
                } else {
                    nextButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
