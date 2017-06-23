package android.ebozkurt.com.favor;

import android.ebozkurt.com.favor.helpers.BottomNavigationViewHelper;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BoonListActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boon_list);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.activity_boon_list_bottom_navigation_bar);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);


    }
}
