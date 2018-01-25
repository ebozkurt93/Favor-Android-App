package android.ebozkurt.com.favor;

import android.ebozkurt.com.favor.adapters.ChatsAdapter;
import android.ebozkurt.com.favor.domain.Event;
import android.ebozkurt.com.favor.domain.helpers.JSONResponse;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.BottomNavigationViewHelper;
import android.ebozkurt.com.favor.helpers.CommonOperations;
import android.ebozkurt.com.favor.network.BoonApiInterface;
import android.ebozkurt.com.favor.network.RetrofitBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatListActivity extends AppCompatActivity {

    ImageButton backImageButton;
    TextView actionBarCenterText;
    AHBottomNavigation bottomNavigationView;

    private ArrayList<Event> events;


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

        getMyStartedEvents();

        /*RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_chat_list_recyclerview);
        ChatsAdapter adapter = new ChatsAdapter(this, );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/


    }
    public void getMyStartedEvents() {
        BoonApiInterface apiService = RetrofitBuilder.returnService();
        String accessToken = CommonOperations.getAccessToken(this);
        Call<JSONResponse> call = apiService.getMyStartedEvents(accessToken);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.body() instanceof JSONResponse) {
                    if (response.body().isSuccess()) {
                        Gson gson = new Gson();
                        String json = gson.toJson(response.body().getPayload());
                        List<Event> retrievedEvents = gson.fromJson(json, new TypeToken<List<Event>>() {
                        }.getType());
                        events = new ArrayList<Event>();
                        events.addAll(retrievedEvents);
                        ChatsAdapter chatsAdapter = new ChatsAdapter(events, ChatListActivity.this);
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_chat_list_recyclerview);
                        recyclerView.setAdapter(chatsAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ChatListActivity.this));
                    } else {
                        ActivityHelper.DisplayGeneralErrorToast(ChatListActivity.this);
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                ActivityHelper.DisplayGeneralErrorToast(ChatListActivity.this);
            }
        });
    }

}
