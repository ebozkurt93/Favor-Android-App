package android.ebozkurt.com.favor.helpers;

import android.app.Activity;
import android.content.Intent;
import android.ebozkurt.com.favor.ChatListActivity;
import android.ebozkurt.com.favor.CreateEvent1Activity;
import android.ebozkurt.com.favor.EditProfileActivity;
import android.ebozkurt.com.favor.HomeActivity;
import android.ebozkurt.com.favor.ProfileActivity;
import android.ebozkurt.com.favor.R;
import android.graphics.drawable.Drawable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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

    public static void initialize(final Activity activity, AHBottomNavigation bottomNavigationView, final int place) {
        //mutate here prevents drawable being changed in all other screens also
        Drawable homeDrawable = activity.getDrawable(R.drawable.home).mutate();
        Drawable chatDrawable = activity.getDrawable(R.drawable.chat).mutate();
        Drawable createDrawable = activity.getDrawable(R.drawable.create_disabled).mutate();
        Drawable profileDrawable = activity.getDrawable(R.drawable.profile).mutate();
        Drawable moreDrawable = activity.getDrawable(R.drawable.more).mutate();



        /*
    List<Drawable> drawables = new ArrayList<Drawable>();
    drawables.add(homeDrawable);
    drawables.add(chatDrawable);
    drawables.add(createDrawable);
    drawables.add(profileDrawable);
    drawables.add(moreDrawable);
*/
        int color = activity.getResources().getColor(R.color.colorPrimary);

        switch (place) {
            case 0:
                homeDrawable.setTint(color);
                break;
            case 1:
                chatDrawable.setTint(color);
                break;
            case 2:
                createDrawable = activity.getResources().getDrawable(R.drawable.create);
                break;
            case 3:
                profileDrawable.setTint(color);
                break;
            case 4:
                moreDrawable.setTint(color);
                break;

        }

        bottomNavigationView.removeAllItems();
        bottomNavigationView.removeAllViews();
        bottomNavigationView.clearFocus();
        final AHBottomNavigationItem home = new AHBottomNavigationItem(R.string.home, homeDrawable, R.color.colorAccent);
        AHBottomNavigationItem chat = new AHBottomNavigationItem(R.string.chat, chatDrawable, R.color.colorAccent);
        AHBottomNavigationItem create = new AHBottomNavigationItem(R.string.create, createDrawable, R.color.colorAccent);
        AHBottomNavigationItem profile = new AHBottomNavigationItem(R.string.profile, profileDrawable, R.color.colorAccent);
        AHBottomNavigationItem more = new AHBottomNavigationItem(R.string.more, moreDrawable, R.color.colorAccent);
        bottomNavigationView.addItem(home);
        bottomNavigationView.addItem(chat);
        bottomNavigationView.addItem(create);
        bottomNavigationView.addItem(profile);
        bottomNavigationView.addItem(more);
        bottomNavigationView.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);
        bottomNavigationView.setColored(true);
        bottomNavigationView.setCurrentItem(place);

        bottomNavigationView.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                Intent i;

                if (place != position) {
                    switch (position) {
                        case 0:
                            i = new Intent(activity, HomeActivity.class);
                            nextActivity(i, activity, true);
                            break;
                        case 1:
                            i = new Intent(activity, ChatListActivity.class);
                            nextActivity(i, activity, true);
                            break;
                        case 2:
                            i = new Intent(activity, CreateEvent1Activity.class);
                            nextActivity(i, activity, true);
                            break;
                        case 3:
                            i = new Intent(activity, ProfileActivity.class);
                            nextActivity(i, activity, false);
                            break;
                        default:
                            Toast.makeText(activity.getApplicationContext(), Integer.toString(position), Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
    }

    private static void nextActivity(Intent i, Activity activity, boolean clear) {
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(i);
        if (clear)
            activity.finish();
    }

//todo add some function to set up and carry notifications


}