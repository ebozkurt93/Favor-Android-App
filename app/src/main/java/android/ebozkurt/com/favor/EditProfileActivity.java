package android.ebozkurt.com.favor;

import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ActivityHelper.initialize(this);

    }
}
