package android.ebozkurt.com.favor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPassword2Activity extends AppCompatActivity {
    TextView topText;
    TextView topRightText;
    ImageButton topBackButton;
    Button mailBoxButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password2);
        ActivityHelper.initialize(this);

        topText = (TextView) findViewById(R.id.sign_up1_action_bar_middle_text_view);
        topText.setVisibility(View.INVISIBLE);
        topRightText = (TextView) findViewById(R.id.sign_up1_action_bar_right_text_view);
        topRightText.setText(R.string.close);
        topBackButton = (ImageButton) findViewById(R.id.sign_up1_action_bar_image_button);
        topBackButton.setVisibility(View.GONE);
        mailBoxButton = (Button) findViewById(R.id.activity_forgot_password2_goto_mailbox_button);

        topRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ForgotPassword2Activity.this, InitialActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });

        mailBoxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                startActivity(intent);
                */
                Toast.makeText(ForgotPassword2Activity.this, "This is a temporary action, please remove it", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ForgotPassword2Activity.this, ResetPasswordActivity.class);
                startActivity(i);
            }
        });
    }
}
