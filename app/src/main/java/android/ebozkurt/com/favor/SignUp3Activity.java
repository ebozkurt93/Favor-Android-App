package android.ebozkurt.com.favor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class SignUp3Activity extends AppCompatActivity {

    TextView termsOfConditions;
    ImageButton actionBarBack;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3);
        ActivityHelper.initialize(this);

        actionBarBack = (ImageButton) findViewById(R.id.sign_up1_action_bar_image_button);
        String formattedTermsOfConditions = String.format(getResources().getString(R.string.by_proceeding_you), getResources().getString(R.string.app_name));
        termsOfConditions = (TextView) findViewById(R.id.activity_sign_up3_terms_and_conditions_textView);
        termsOfConditions.setText(Html.fromHtml(formattedTermsOfConditions));
        termsOfConditions.setMovementMethod(LinkMovementMethod.getInstance());

        signUp = (Button) findViewById(R.id.activity_sign_up3_sign_up_button);
        //signUp.setEnabled(false); //TODO enable this after testing
        //termsOfConditions.setText(formattedTermsOfConditions);


        actionBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp3Activity.this, SignUp4Activity.class);
                startActivity(i);
            }
        });
    }

}
