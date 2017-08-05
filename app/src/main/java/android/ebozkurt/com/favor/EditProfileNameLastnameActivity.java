package android.ebozkurt.com.favor;

import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfileNameLastnameActivity extends AppCompatActivity {

    TextInputLayout nameTextInputLayout, lastnameTextInputLayout;
    EditText nameEditText, lastnameEditText;
    Button saveButton;
    String name, lastname;
    //actionbar components
    TextView title;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_name_lastname);
        ActivityHelper.initialize(this);
        ActivityHelper.hideKeyboardWhenEdittextNotFocused(getWindow().getDecorView().getRootView(), EditProfileNameLastnameActivity.this);

        nameTextInputLayout = (TextInputLayout) findViewById(R.id.activity_edit_profile_name_lastname_name_text_input_layout);
        lastnameTextInputLayout = (TextInputLayout) findViewById(R.id.activity_edit_profile_name_lastname_lastname_text_input_layout);
        nameEditText = (EditText) findViewById(R.id.activity_edit_profile_name_lastname_name_editText);
        lastnameEditText = (EditText) findViewById(R.id.activity_edit_profile_name_lastname_lastname_editText);
        saveButton = (Button) findViewById(R.id.activity_edit_profile_name_lastname_save_button);
        title = (TextView) findViewById(R.id.sign_up1_action_bar_middle_text_view);
        title.setText(R.string.edit_name);
        back = (ImageView) findViewById(R.id.sign_up1_action_bar_image_button);

        //temporary name and lastname
        name = "ExampleName";
        lastname = "ExampleLastname";

        nameEditText.setText(name);
        lastnameEditText.setText(lastname);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                enableSaveButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        lastnameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                enableSaveButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private boolean hasInvalidCharacter(EditText editText, TextInputLayout textInputLayout) {
        Pattern p = Pattern.compile("[^A-Za-z]");//replace this with your needs
        Matcher m = p.matcher(editText.getText());
        // boolean b = m.matches();

        boolean b = m.find();
        if (b == true) {
            textInputLayout.setError(getString(R.string.invalid_character));
            return true;
        } else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return false;
        }
    }

    private boolean variablesChanged() {
        if (ActivityHelper.getTrimmedString(nameEditText).equals(name) && ActivityHelper.getTrimmedString(lastnameEditText).equals(lastname)) {
            return false;
        } else return true;
    }

    private void enableSaveButton() {

        //Do not remove, for an unknown reason it doesnt work without log message
        Log.i("dev", "variablesChanged(): " + variablesChanged() + "\n!hasinvalidCharactername: " + !hasInvalidCharacter(nameEditText, nameTextInputLayout) + "\n!hasinvalidCharacterlastname: " + !hasInvalidCharacter(lastnameEditText, lastnameTextInputLayout));

        if (variablesChanged() && !hasInvalidCharacter(nameEditText, nameTextInputLayout) && !hasInvalidCharacter(lastnameEditText, lastnameTextInputLayout) &&
                nameEditText.getText().toString().trim().length() > 0 && lastnameEditText.getText().toString().trim().length() > 0) {
            saveButton.setEnabled(true);
        } else {
            saveButton.setEnabled(false);
        }
    }


}
