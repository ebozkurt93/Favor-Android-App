package android.ebozkurt.com.favor;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class InitialActivity extends AppCompatActivity {

    private static final String TAG = "InitialActivity";

    Button login, signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        login = (Button) findViewById(R.id.activity_initial_login_button);
        signup = (Button) findViewById(R.id.activity_initial_signup_button);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InitialActivity.this, SignUp1Activity.class);
                //Intent i = new Intent(InitialActivity.this, SignUp2Activity.class);
                startActivity(i);

            }
        });
    }


}
