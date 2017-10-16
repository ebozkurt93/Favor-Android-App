package android.ebozkurt.com.favor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.ebozkurt.com.favor.domain.User;
import android.ebozkurt.com.favor.domain.helpers.JSONResponse;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.AnimationHelper;
import android.ebozkurt.com.favor.helpers.BitmapHelper;
import android.ebozkurt.com.favor.helpers.BottomNavigationViewHelper;
import android.ebozkurt.com.favor.helpers.MapHelper;
import android.ebozkurt.com.favor.network.BoonApiInterface;
import android.ebozkurt.com.favor.network.RetrofitBuilder;
import android.ebozkurt.com.favor.views.LoadingDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {
    AHBottomNavigation bottomNavigationView;
    TextView userPointsTextView;
    //SearchView searchBar;

    GoogleMap map;
    LatLng currentCoordinates = new LatLng(40.891444, 29.379922);
    ; //todo update this for getting current coordinates, or coordinate of a preselected place
    Marker currentPositionMarker;

    int points;
    int activeEventCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActivityHelper.initialize(this);
        ActivityHelper.transparentStatusBar(getWindow(), getResources());

        //getting values from sharedpreferences
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.__sp_key), Context.MODE_PRIVATE);
        points = sharedPreferences.getInt(getString(R.string.__sp_user_point), 0);
        activeEventCount = sharedPreferences.getInt(getString(R.string.__sp_user_active_event_count), 3);


        bottomNavigationView = (AHBottomNavigation) findViewById(R.id.activity_home_bottom_navigation_bar);
        BottomNavigationViewHelper.initialize(this, bottomNavigationView, 0);
        Log.i("dev", "onCreate: " + points);
        userPointsTextView = (TextView) findViewById(R.id.activity_home_points_textview);
        userPointsTextView.setText("" + points);
        //BitmapHelper.currencyIconInitializer(this, userPointsTextView);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_home_mapfragment);
        mapFragment.getMapAsync(this);

        //searchBar = (SearchView) findViewById(R.id.activity_home_searchbar_search_view);
        //searchBar.setQueryHint(String.format(getResources().getString(R.string.search_bar_hint), getResources().getString(R.string.app_name)));
        //        create.setText(getResources().getString(R.string.post_event, getResources().getString(R.string.app_name)));


        userPointsTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                getUserInfo();
                return false;
            }
        });

    }

    private void getEventsNearby(double latitude, double longitude) {
        BoonApiInterface apiService = RetrofitBuilder.returnService();
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.__sp_key), Context.MODE_PRIVATE);
        final String accessToken = sharedPreferences.getString(getString(R.string.__sp_access_token), "");
        Call<JSONResponse> call = apiService.getAllEvents(accessToken, latitude, longitude);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if(response.body().isSuccess()) {

                }
                else {
                    Log.i("dev", "getting event list failed.");
                    //try again...
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.i("dev", "getting event list failed.");
                //try again...
            }
        });
    }

    private void getUserInfo() {
        BoonApiInterface apiService = RetrofitBuilder.returnService();
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.__sp_key), Context.MODE_PRIVATE);
        final String accessToken = sharedPreferences.getString(getString(R.string.__sp_access_token), "");
        Call<JSONResponse> call = apiService.getMyInfo(accessToken);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.body().isSuccess()) {
                    Gson gson = new Gson();
                    String jsonString = new JSONObject((Map) response.body().getPayload()).toString();
                    Log.i("dev", "onResponse: " + jsonString);
                    User user1 = gson.fromJson(jsonString, User.class);
                    Log.i("dev", "onResponse: " + user1.toString());
                    SharedPreferences sharedPreferences = HomeActivity.this.getSharedPreferences(getString(R.string.__sp_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(getString(R.string.__sp_access_token), accessToken);
                    editor.putString(getString(R.string.__sp_user_name), user1.getName());
                    editor.putString(getString(R.string.__sp_user_lastname), user1.getLastname());
                    editor.putString(getString(R.string.__sp_user_email), user1.getEmail());
                    editor.putInt(getString(R.string.__sp_user_point), user1.getPoints());
                    editor.putInt(getString(R.string.__sp_user_active_event_count), user1.getActiveEventCount());
                    editor.putString(getString(R.string.__sp_user_rating), user1.getRating().toString());
                    editor.apply();
                    points = user1.getPoints();
                    userPointsTextView.setText("" + points);
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.i("dev", "getting user info failed.");
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        MapHelper.setMapSettings(map, this, true, true);
        //sabancı coordinates
        if (currentCoordinates == null) {
            currentCoordinates = new LatLng(40.891444, 29.379922);
        }
        map.moveCamera(CameraUpdateFactory.newLatLng(currentCoordinates));
        final MarkerOptions myMarkerOptions = new MarkerOptions()
                .position(currentCoordinates)
                .icon(MapHelper.getDefaultMapIcon(this));

        currentPositionMarker = map.addMarker(myMarkerOptions);
        /*
        Marker testmarker = map.addMarker(new MarkerOptions()
                .position(new LatLng(currentCoordinates.latitude + 0.001, currentCoordinates.longitude + 0.001))
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapHelper.getBitmapFromVectorDrawable(this.getResources().getDrawable(R.drawable.chat)))));
        */

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return marker.getId().equals(currentPositionMarker.getId());
            }
        });

        /*
        map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                searchBar.setAlpha(0.5f);
            }
        });
        map.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                searchBar.setAlpha(1f);

            }
        });
        */

    }
}
