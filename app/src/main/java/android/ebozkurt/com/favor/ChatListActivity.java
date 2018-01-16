package android.ebozkurt.com.favor;

import android.ebozkurt.com.favor.adapters.ChatsAdapter;
import android.ebozkurt.com.favor.domain.Event;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.BottomNavigationViewHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {

    ImageButton backImageButton;
    TextView actionBarCenterText;
    AHBottomNavigation bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        ActivityHelper.initialize(this);
        ActivityHelper.transparentStatusBar(getWindow(), getResources());

        backImageButton = (ImageButton) findViewById(R.id.sign_up1_action_bar_image_button);
        actionBarCenterText = (TextView) findViewById(R.id.sign_up1_action_bar_middle_text_view);
        actionBarCenterText.setText(R.string.chat);

        bottomNavigationView = (AHBottomNavigation) findViewById(R.id.activity_chat_list_bottom_navigation_bar);
        BottomNavigationViewHelper.initialize(this, bottomNavigationView, 1);

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /*RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_chat_list_recyclerview);
        ChatsAdapter adapter = new ChatsAdapter(this, );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/


    }

}
