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

public class EditProfileDescriptionActivity extends AppCompatActivity {

    TextInputLayout descriptionTextInputLayout;
    EditText descriptionEditText;
    Button saveButton;
    String personalDescription;
    //actionbar components
    TextView title;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_description);
        ActivityHelper.initialize(this);
        ActivityHelper.hideKeyboardWhenEdittextNotFocused(getWindow().getDecorView().getRootView(), EditProfileDescriptionActivity.this);


        descriptionTextInputLayout = (TextInputLayout) findViewById(R.id.activity_edit_profile_description_description_text_input_layout);
        descriptionEditText = (EditText) findViewById(R.id.activity_edit_profile_description_description_editText);
        saveButton = (Button) findViewById(R.id.activity_edit_description_save_button);

        title = (TextView) findViewById(R.id.sign_up1_action_bar_middle_text_view);
        title.setText(R.string.edit_description);
        back = (ImageView) findViewById(R.id.sign_up1_action_bar_image_button);

        personalDescription = "This is an example personal descriptionEditText, try changing it.";
        descriptionEditText.setText(personalDescription);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        descriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // Log.i("dev", Boolean.toString(descriptionEditText.getText().toString().trim().equals(personalDescription)));
                if (!descriptionEditText.getText().toString().trim().equals(personalDescription) && descriptionEditText.getText().toString().trim().length() > 0) {
                    saveButton.setEnabled(true);
                } else saveButton.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                for(int i = s.length(); i > 0; i--) {

                    if(s.subSequence(i-1, i).toString().equals("\n"))
                        s.replace(i-1, i, "");
                }

                String myTextString = s.toString();
            }
        });

    }
}
