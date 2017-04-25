package android.ebozkurt.com.favor;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUp1Activity extends ActivityHelper {

    private static final String TAG = SignUp1Activity.class.getName();

    ImageButton actionBarBack;
    Button nextButton;
    EditText name, surname;
    TextInputLayout nameLayout, surnameLayout;
    View actionBarBackground1, actionBarBackground2, actionBarBackground3, actionBarBackground4;  //not all of them are needed

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);
        ActivityHelper.initialize(this);
/*
        ActionBar actionBar = getSupportActionBar();
        View customView = LayoutInflater.from(this).inflate(R.layout.sign_up1_action_bar, null);
        */
        actionBarBack = (ImageButton) findViewById(R.id.sign_up1_action_bar_image_button);
        nextButton = (Button) findViewById(R.id.activity_sign_up1_next_button);
        name = (EditText) findViewById(R.id.activity_sign_up1_name_editText);
        surname = (EditText) findViewById(R.id.activity_sign_up1_surname_editText);
        nameLayout = (TextInputLayout) findViewById(R.id.activity_sign_up1_name_text_input_layout);
        surnameLayout = (TextInputLayout) findViewById(R.id.activity_sign_up1_surname_text_input_layout);
        nextButton.setEnabled(false); //TODO enable this after testing
        //mailBoxButton.setTextColor(getResources().getColor(R.color.colorDisabledButtonText));
        actionBarBackground1 = (View) findViewById(R.id.sign_up_action_bar_background_view1);
        actionBarBackground2 = (View) findViewById(R.id.sign_up_action_bar_background_view2);
        actionBarBackground3 = (View) findViewById(R.id.sign_up_action_bar_background_view3);
        actionBarBackground4 = (View) findViewById(R.id.sign_up_action_bar_background_view4);



        actionBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp1Activity.this, SignUp2Activity.class);
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

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionBarBackground1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });

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
