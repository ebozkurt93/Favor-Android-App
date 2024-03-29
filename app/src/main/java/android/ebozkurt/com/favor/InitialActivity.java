package android.ebozkurt.com.favor;

import android.content.Context;
import android.content.Intent;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class InitialActivity extends ActivityHelper {

    private static final String TAG = "InitialActivity";

    FragmentPagerAdapter adapterViewPager;

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
        ActivityHelper.transparentStatusBar(getWindow(), getResources());

        ViewPager vpPager = (ViewPager) findViewById(R.id.activity_initial_viewPager);
        TabLayout vpPagerTabLayout = (TabLayout) findViewById(R.id.activity_initial_viewPager_dots);
        vpPagerTabLayout.setupWithViewPager(vpPager, true);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), this);
        vpPager.setAdapter(adapterViewPager);


        login = (Button) findViewById(R.id.activity_initial_login_button);
        signup = (Button) findViewById(R.id.activity_initial_signup_button);
        linearLayout = (LinearLayout) findViewById(R.id.activity_initial_linear_layout);
        linearLayout.bringToFront();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InitialActivity.this, LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.left1, R.anim.left2);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InitialActivity.this, SignUp1Activity.class);
                startActivity(i);
                overridePendingTransition(R.anim.left1, R.anim.left2);
            }
        });

        login.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent(InitialActivity.this, CreateEvent1Activity.class);
                startActivity(i);
                return false;
            }
        });

        signup.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent(InitialActivity.this, HomeActivity.class);
                startActivity(i);
                return false;
            }
        });

        //ActivityHelper.DisplayCustomToast(InitialActivity.this, "test", Toast.LENGTH_LONG);
       //ActivityHelper.getLoadingDialog().show(getSupportFragmentManager(), "");
    }


    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;
        private Context context;

        public MyPagerAdapter(FragmentManager fragmentManager, Context current) {
            super(fragmentManager);
            this.context = current;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return InitialViewPager1Fragment.newInstance(0, context.getResources().getString(R.string.initial_viewpager_1_title, context.getResources().getString(R.string.app_name)), context.getResources().getString(R.string.initial_viewpager_1_text), R.drawable.welcome_01);
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return InitialViewPager1Fragment.newInstance(0, context.getResources().getString(R.string.initial_viewpager_2_title), context.getResources().getString(R.string.initial_viewpager_2_text), R.drawable.welcome_02);
                case 2: // Fragment # 1 - This will show SecondFragment
                    return InitialViewPager1Fragment.newInstance(0, context.getResources().getString(R.string.initial_viewpager_3_title), context.getResources().getString(R.string.initial_viewpager_3_text), R.drawable.welcome_03);

                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

    }


}