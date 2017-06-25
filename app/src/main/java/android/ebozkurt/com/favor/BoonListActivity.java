package android.ebozkurt.com.favor;

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

        bottomNavigationView = (AHBottomNavigation) findViewById(R.id.activity_boon_list_bottom_navigation_bar);
        //BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);

        AHBottomNavigationItem home = new AHBottomNavigationItem(R.string.home, R.drawable.google_vector, R.color.colorPrimary);
        AHBottomNavigationItem chat = new AHBottomNavigationItem(R.string.chat, R.drawable.google_vector, R.color.colorPrimary);
        AHBottomNavigationItem create = new AHBottomNavigationItem(R.string.create, R.drawable.google_vector, R.color.colorPrimary);
        AHBottomNavigationItem profile = new AHBottomNavigationItem(R.string.profile, R.drawable.google_vector, R.color.colorPrimary);
        AHBottomNavigationItem more = new AHBottomNavigationItem(R.string.more, R.drawable.google_vector, R.color.colorPrimary);

        bottomNavigationView.addItem(home);
        bottomNavigationView.addItem(chat);
        bottomNavigationView.addItem(create);
        bottomNavigationView.addItem(profile);
        bottomNavigationView.addItem(more);
        bottomNavigationView.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);
      //  bottomNavigationView.setColored(true);
//        bottomNavigationView.setForceTint(true);
        bottomNavigationView.setCurrentItem(0);
        bottomNavigationView.setNotification("Berkin",3);
    }
}
