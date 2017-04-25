package android.ebozkurt.com.favor;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    EditText passwordTextView;
    Button generatePasswordButton;

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

        generatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ResetPasswordActivity.this,"Add something here", Toast.LENGTH_SHORT).show();
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
}
