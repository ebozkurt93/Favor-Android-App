package android.ebozkurt.com.favor;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SignUp1Activity extends ActivityHelper {

    private static final String TAG = "SignUp1Activity";

    ImageButton actionBarBack;
    Button nextButton;
    EditText name, surname;
    TextInputLayout nameLayout, surnameLayout;

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
        nextButton.setEnabled(false);
        //nextButton.setTextColor(getResources().getColor(R.color.colorDisabledButtonText));


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
