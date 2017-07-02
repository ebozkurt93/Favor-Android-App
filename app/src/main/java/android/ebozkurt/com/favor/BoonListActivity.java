package android.ebozkurt.com.favor;

import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.BottomNavigationViewHelper;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class BoonListActivity extends AppCompatActivity {

    private AHBottomNavigation bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boon_list);
        ActivityHelper.initialize(this);

        bottomNavigationView = (AHBottomNavigation) findViewById(R.id.activity_boon_list_bottom_navigation_bar);
        BottomNavigationViewHelper.initialize(bottomNavigationView, 0);

    }
}
