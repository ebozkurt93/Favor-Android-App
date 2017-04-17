package android.ebozkurt.com.favor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class SignUp3Activity extends AppCompatActivity {

    TextView termsOfConditions;
    ImageButton actionBarBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3);
        ActivityHelper.initialize(this);

        actionBarBack = (ImageButton) findViewById(R.id.sign_up1_action_bar_image_button);
        String formattedTermsOfConditions = String.format(getResources().getString(R.string.sign_up_3_terms_of_service), getResources().getString(R.string.app_name));
        termsOfConditions = (TextView) findViewById(R.id.activity_sign_up3_terms_and_conditions_textView);
        termsOfConditions.setMovementMethod(LinkMovementMethod.getInstance());

        termsOfConditions.setText(formattedTermsOfConditions);



    actionBarBack.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    });
    }

}
