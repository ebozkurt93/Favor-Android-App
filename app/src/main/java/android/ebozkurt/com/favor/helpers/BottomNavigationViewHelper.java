package android.ebozkurt.com.favor.helpers;

import android.ebozkurt.com.favor.R;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.lang.reflect.Field;

/**
 * Created by erdem on 23.06.2017.
 */

public class BottomNavigationViewHelper {
/*
    public static void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }
    }
    */

public static void initialize(AHBottomNavigation bottomNavigationView, int place){
    AHBottomNavigationItem home = new AHBottomNavigationItem(R.string.home, R.drawable.home, R.color.colorAccent);
    AHBottomNavigationItem chat = new AHBottomNavigationItem(R.string.chat, R.drawable.chat, R.color.colorAccent);
    AHBottomNavigationItem create = new AHBottomNavigationItem(R.string.create, R.drawable.create, R.color.colorAccent);
    AHBottomNavigationItem profile = new AHBottomNavigationItem(R.string.profile, R.drawable.profile, R.color.colorAccent);
    AHBottomNavigationItem more = new AHBottomNavigationItem(R.string.more, R.drawable.more, R.color.colorAccent);
    bottomNavigationView.addItem(home);
    bottomNavigationView.addItem(chat);
    bottomNavigationView.addItem(create);
    bottomNavigationView.addItem(profile);
    bottomNavigationView.addItem(more);
    bottomNavigationView.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);
    bottomNavigationView.setColored(true);
    bottomNavigationView.setCurrentItem(place);
}

//todo add some function to set up and carry notifications



}