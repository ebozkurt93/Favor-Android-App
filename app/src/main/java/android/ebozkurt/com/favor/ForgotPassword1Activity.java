package android.ebozkurt.com.favor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForgotPassword1Activity extends AppCompatActivity {

    TextView topText;
    Button sendMailButton;
    TextView topRightText;
    ImageButton backImageButton;
    EditText email; //TODO check validity of email address

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password1);
        ActivityHelper.initialize(this);

        topText = (TextView) findViewById(R.id.sign_up1_action_bar_middle_text_view);
        topText.setText(R.string.forgot_password);
        topRightText = (TextView) findViewById(R.id.sign_up1_action_bar_right_text_view);
        topRightText.setText(R.string.cancel);
        backImageButton = (ImageButton) findViewById(R.id.sign_up1_action_bar_image_button);
        backImageButton.setVisibility(View.INVISIBLE);
        sendMailButton = (Button) findViewById(R.id.activity_forgot_password1_send_mail_button);


        sendMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ForgotPassword1Activity.this, ForgotPassword2Activity.class);
                startActivity(i);
            }
        });

        topRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
