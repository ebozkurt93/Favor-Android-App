package android.ebozkurt.com.favor;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EditProfileNameLastnameActivity extends AppCompatActivity {

    TextInputLayout nameTextInputLayout, surnameTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_name_lastname);
        nameTextInputLayout = (TextInputLayout) findViewById(R.id.activity_edit_profile_name_lastname_name_text_input_layout);
        // nameTextInputLayout.setErrorEnabled(true);
        // nameTextInputLayout.setError("asdasdasd");
    }
}
