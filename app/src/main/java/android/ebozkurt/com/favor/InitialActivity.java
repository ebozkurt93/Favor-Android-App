package android.ebozkurt.com.favor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class InitialActivity extends AppCompatActivity {

    private static final String TAG = "InitialActivity";

    Button login, signup;
    LinearLayout linearLayout;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        ActivityHelper.initialize(this);


        login = (Button) findViewById(R.id.activity_initial_login_button);
        signup = (Button) findViewById(R.id.activity_initial_signup_button);
        linearLayout = (LinearLayout) findViewById(R.id.activity_initial_linear_layout);
        linearLayout.bringToFront();



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InitialActivity.this, SignUp1Activity.class);
                //Intent i = new Intent(InitialActivity.this, SignUp2Activity.class);
                startActivity(i);

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InitialActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    public void layoutClick (View v) {
        Intent i = new Intent(InitialActivity.this, SignUp3Activity.class);
        startActivity(i);
    }




}
