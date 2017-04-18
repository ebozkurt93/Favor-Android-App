package android.ebozkurt.com.favor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignUp4Activity extends AppCompatActivity {

    TextView topText;
    Button mailBoxButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up4);
        ActivityHelper.initialize(this);

        String formattedTopText = String.format(getResources().getString(R.string.sign_up4_top_text), getResources().getString(R.string.app_name));
        topText = (TextView) findViewById(R.id.activity_sign_up4_top_text);
        topText.setText(formattedTopText);
        mailBoxButton = (Button) findViewById(R.id.activity_sign_up4_mailbox_button);

    mailBoxButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
            startActivity(intent);
        }
    });


    }
}
