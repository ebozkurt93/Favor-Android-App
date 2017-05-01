package android.ebozkurt.com.favor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUp4Activity extends AppCompatActivity {

    View actionBarBackground1, actionBarBackground2, actionBarBackground3, actionBarBackground4;
    TextView topText;
    Button mailBoxButton;
    TextView topRightText;
    ImageButton backImageButton;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up4);
        ActivityHelper.initialize(this);

        actionBarBackground1 = (View) findViewById(R.id.sign_up_action_bar_background_view1);
        actionBarBackground2 = (View) findViewById(R.id.sign_up_action_bar_background_view2);
        actionBarBackground3 = (View) findViewById(R.id.sign_up_action_bar_background_view3);
        actionBarBackground4 = (View) findViewById(R.id.sign_up_action_bar_background_view4);
        actionBarBackground1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        actionBarBackground2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        actionBarBackground3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        actionBarBackground4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        String formattedTopText = String.format(getResources().getString(R.string.congratulations_you_are), getResources().getString(R.string.app_name));
        topText = (TextView) findViewById(R.id.activity_sign_up4_top_text);
        topText.setText(formattedTopText);
        mailBoxButton = (Button) findViewById(R.id.activity_sign_up4_mailbox_button);
        topRightText = (TextView) findViewById(R.id.sign_up1_action_bar_right_text_view);
        topRightText.setText(R.string.close);
        backImageButton = (ImageButton) findViewById(R.id.sign_up1_action_bar_image_button);
        backImageButton.setVisibility(View.INVISIBLE);

        mailBoxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                startActivity(intent);
            }
        });

        topRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp4Activity.this, InitialActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });


    }
}
