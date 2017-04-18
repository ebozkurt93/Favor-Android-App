package android.ebozkurt.com.favor;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    ImageButton backImageButton;
    TextView actionBarCenterText;
    TextView actionBarRightText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityHelper.initialize(this);
        backImageButton = (ImageButton) findViewById(R.id.sign_up1_action_bar_image_button);
        actionBarCenterText = (TextView) findViewById(R.id.sign_up1_action_bar_middle_text_view);
        actionBarCenterText.setText(R.string.login);
        actionBarRightText = (TextView) findViewById(R.id.sign_up1_action_bar_right_text_view);
        actionBarRightText.setText(R.string.forgot_password);
        actionBarRightText.setTypeface(null, Typeface.ITALIC);


        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        actionBarRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add forgot password screen here
            }
        });
    }
}
