package android.ebozkurt.com.favor;

import android.content.Intent;
import android.ebozkurt.com.favor.adapters.ActiveEventsAdapter;
import android.ebozkurt.com.favor.adapters.EventRequestsAdapter;
import android.ebozkurt.com.favor.domain.Event;
import android.ebozkurt.com.favor.domain.EventRequest;
import android.ebozkurt.com.favor.domain.User;
import android.ebozkurt.com.favor.domain.helpers.JSONResponse;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.BottomNavigationViewHelper;
import android.ebozkurt.com.favor.helpers.CommonOperations;
import android.ebozkurt.com.favor.helpers.SpacesItemDecoration;
import android.ebozkurt.com.favor.network.BoonApiInterface;
import android.ebozkurt.com.favor.network.RetrofitBuilder;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    ImageView editProfileImageView;
    CircleImageView profilePictureCircleImageView;
    TextView nameLastnameTextView, descriptionTextView, pointsTextView, requestsTextView, ongoingEventsTextView, reviewsTextView;
    AHBottomNavigation bottomNavigationView;
    RecyclerView requestsRecyclerView, ongoingEventsRecyclerView, reviewsRecyclerView;

    String name, lastname, description;
    int points, numberOfActiveEvents, numberOfReviews;
    Drawable profilePic;

    boolean isMyProfile;
    User user;

    List<EventRequest> eventRequests;
    List<Event> ongoingEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActivityHelper.initialize(this);


        bottomNavigationView = (AHBottomNavigation) findViewById(R.id.activity_profile_bottom_navigation_bar);
        BottomNavigationViewHelper.initialize(this, bottomNavigationView, 3);


        editProfileImageView = (ImageView) findViewById(R.id.activity_profile_edit_imageview);
        profilePictureCircleImageView = (CircleImageView) findViewById(R.id.activity_profile_profile_picture_circleimageview);
        nameLastnameTextView = (TextView) findViewById(R.id.activity_profile_name_lastname_textview);
        descriptionTextView = (TextView) findViewById(R.id.activity_profile_personal_description_textview);
        pointsTextView = (TextView) findViewById(R.id.activity_profile_points_textview);
        requestsTextView = (TextView) findViewById(R.id.activity_profile_requests_textview);
        ongoingEventsTextView = (TextView) findViewById(R.id.activity_profile_ongoing_events_textview);
        reviewsTextView = (TextView) findViewById(R.id.activity_profile_review_count_textview);
        requestsRecyclerView = (RecyclerView) findViewById(R.id.activity_profile_requests_recyclerview);
        ongoingEventsRecyclerView = (RecyclerView) findViewById(R.id.activity_profile_ongoing_events_recyclerView);
        reviewsRecyclerView = (RecyclerView) findViewById(R.id.activity_profile_reviews_recyclerview);

        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        requestsRecyclerView.addItemDecoration(decoration);
        ongoingEventsRecyclerView.addItemDecoration(decoration);
        reviewsRecyclerView.addItemDecoration(decoration);


        //if isMyProfile not initialized, assume that is true
        isMyProfile = true;

        if (isMyProfile = true) {
            CommonOperations.getUserInfo(this);
            getEventRequests();
            getMyActiveEvents();
            getEventReviews();
            user = CommonOperations.getUserInfo(this);

            name = user.getName();
            lastname = user.getLastname();
            if (!user.getDescription().isEmpty()) {
                description = user.getDescription();
            } else {
                description = "";
            }
            points = user.getPoints();
            numberOfActiveEvents = user.getActiveEventCount();
            //numberOfReviews = 5; //todo

            if (description.length() == 0) {
                descriptionTextView.setText(Html.fromHtml(getResources().getString(R.string.personal_description_empty)));
                descriptionTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(ProfileActivity.this, EditProfileDescriptionActivity.class);
                        startActivity(i);
                    }
                });
            } else {
                descriptionTextView.setText(description);
            }
            pointsTextView.setText(Integer.toString(points));


        } else {
            editProfileImageView.setVisibility(View.INVISIBLE);
            ongoingEventsRecyclerView.setVisibility(View.INVISIBLE);
            ongoingEventsTextView.setVisibility(View.INVISIBLE);
            requestsRecyclerView.setVisibility(View.INVISIBLE);
            requestsTextView.setVisibility(View.INVISIBLE);
        }


        //todo change later
        profilePic = getDrawable(R.drawable.profile_pic);


        profilePictureCircleImageView.setImageDrawable(profilePic);
        nameLastnameTextView.setText(name + " " + lastname);

        if (numberOfActiveEvents <= 0) {
            ongoingEventsTextView.setVisibility(View.GONE);
            //todo recyclerview also gone
        } else {
            ongoingEventsTextView.setText(String.format(getResources().getString(R.string.ongoing_events), getResources().getString(R.string.app_name)));
        }
        if (numberOfReviews <= 0) {
            //todo recyclerview gone
        }
        reviewsTextView.setText(String.format(getResources().getString(R.string.number_reviews), numberOfReviews));

        editProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(i);
            }
        });
    }

    private void getMyActiveEvents() {
        BoonApiInterface apiService = RetrofitBuilder.returnService();
        String accessToken = CommonOperations.getAccessToken(this);
        Call<JSONResponse> call = apiService.getMyActiveEvents(accessToken);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.body() instanceof JSONResponse) {
                    if (response.body().isSuccess()) {
                        Gson gson = new Gson();
                        String json = gson.toJson(response.body().getPayload());
                        List<Event> events = gson.fromJson(json, new TypeToken<List<Event>>() {
                        }.getType());
                        Collections.reverse(events);
                        ongoingEvents = new ArrayList<Event>();
                        for (Event event : events) {
                            ongoingEvents.add(event);
                        }
                        if (ongoingEvents.size() == 0) {
                            //no active events
                            ongoingEventsRecyclerView.setVisibility(View.GONE);
                        } else {
                            ActiveEventsAdapter activeEventsAdapter = new ActiveEventsAdapter(ongoingEvents, ProfileActivity.this, getSupportFragmentManager(), ProfileActivity.this);
                            ongoingEventsRecyclerView.setAdapter(activeEventsAdapter);
                            ongoingEventsRecyclerView.setLayoutManager(new LinearLayoutManager(ProfileActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        }
                    }
                } else {
                    ongoingEventsRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });

    }

    private void getEventRequests() {
        BoonApiInterface apiService = RetrofitBuilder.returnService();
        String accessToken = CommonOperations.getAccessToken(this);
        Call<JSONResponse> call = apiService.getMyEventRequests(accessToken);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.body() instanceof JSONResponse) {
                    if (response.body().isSuccess()) {
                        Gson gson = new Gson();
                        String json = gson.toJson(response.body().getPayload());
                        List<EventRequest> requests = gson.fromJson(json, new TypeToken<List<EventRequest>>() {
                        }.getType());
                        eventRequests = new ArrayList<EventRequest>();
                        for (EventRequest request : requests) {
                            eventRequests.add(request);
                       /* eventRequests = (ArrayList<EventRequest>) response.body().getPayload();
                        Log.i("dev11", eventRequests.toString());
                        Gson gson = new Gson();
                        String json = gson.toJson(response.body().getPayload());
                        List<EventRequest> requests = gson.fromJson(json, new TypeToken<List<EventRequest>>() {}.getType());
                        for (EventRequest request : requests) {
                            eventRequests.add(request);*/
                        }
                        if (eventRequests.size() == 0) {
                            //no event requests
                            requestsRecyclerView.setVisibility(View.GONE);
                        } else {
                            EventRequestsAdapter eventRequestsAdapter = new EventRequestsAdapter(eventRequests, ProfileActivity.this);
                            requestsRecyclerView.setAdapter(eventRequestsAdapter);
                            requestsRecyclerView.setLayoutManager(new LinearLayoutManager(ProfileActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        }

                    } else {
                        requestsRecyclerView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });
    }
    private void getEventReviews() {
        //reviewsRecyclerView.setVisibility(View.GONE); //wont work
    }

}
