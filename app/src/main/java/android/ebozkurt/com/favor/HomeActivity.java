package android.ebozkurt.com.favor;

import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.ebozkurt.com.favor.adapters.EventsAdapter;
import android.ebozkurt.com.favor.domain.Event;
import android.ebozkurt.com.favor.domain.User;
import android.ebozkurt.com.favor.domain.helpers.JSONResponse;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.BottomNavigationViewHelper;
import android.ebozkurt.com.favor.helpers.CommonOperations;
import android.ebozkurt.com.favor.helpers.MapHelper;
import android.ebozkurt.com.favor.helpers.TimeHelper;
import android.ebozkurt.com.favor.network.BoonApiInterface;
import android.ebozkurt.com.favor.network.RetrofitBuilder;
import android.ebozkurt.com.favor.helpers.SpacesItemDecoration;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {

    AHBottomNavigation bottomNavigationView;
    TextView userPointsTextView;
    ImageView myLocationImageView;
    //SearchView searchBar;

    GoogleMap map;
    LocationRequest locationRequest;
    LatLng currentCoordinates //= new LatLng(40.891444, 29.379922);
            ; //todo update this for getting current coordinates, or coordinate of a preselected place
    Marker currentPositionMarker;

    int points;
    int activeEventCount;

    //used for lccation requests
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    //used for location permission, value of this int doesn't matter, just needs to be unique
    public static final int LOCATION_PERMISSION_CODE = 99;

    private ArrayList<Event> events;
    private ArrayList<Marker> eventMarkers;

    RecyclerView eventsRecyclerView;
    TextView eventsCounterTextView;
    int selectedItemPosition = 0;


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
        userPointsTextView = (TextView) findViewById(R.id.activity_home_points_textview);
        userPointsTextView.setText("" + points);
        myLocationImageView = (ImageView) findViewById(R.id.activity_home_my_location_imageview);

        //BitmapHelper.currencyIconInitializer(this, userPointsTextView);

        startLocationUpdates();
        //getEventsNearby(currentCoordinates.latitude, currentCoordinates.longitude);

        eventsCounterTextView = (TextView) findViewById(R.id.activity_home_events_counter_textview);
        eventsRecyclerView = (RecyclerView) findViewById(R.id.activity_home_events_recycler_view);
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        eventsRecyclerView.addItemDecoration(decoration);
        eventsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager layoutManager = ((LinearLayoutManager)eventsRecyclerView.getLayoutManager());
                    int pos = layoutManager.findFirstVisibleItemPosition();
                    //eventsRecyclerView.scrollToPosition(pos);
                    updateSelectedMarker(pos);
                    Marker marker = eventMarkers.get(pos);
                    map.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                /*LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int pos = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                Log.i("dev", "pos "+ pos);
                if (pos != -1) {
                    updateSelectedMarker(pos);
                    Marker marker = eventMarkers.get(pos);
                    map.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                }*/


            }

        });

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

        myLocationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentCoordinates != null) {
                    map.moveCamera(CameraUpdateFactory.newLatLng(currentCoordinates));

                } else {
                    ActivityHelper.DisplayCustomToast(HomeActivity.this, getResources().getString(R.string.getting_location), Toast.LENGTH_LONG);
                }
            }
        });


    }
    @Override
    public boolean onKeyDown ( int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            killDetailsFragment();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void startLocationUpdates() {
        if (checkLocationPermission()) {
            GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API).build();
            googleApiClient.connect();

            // Create the location request to start receiving updates
            locationRequest = new LocationRequest();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(UPDATE_INTERVAL);
            locationRequest.setFastestInterval(FASTEST_INTERVAL);

            // Create LocationSettingsRequest object using location request
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
            builder.addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                    final Status status = locationSettingsResult.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            //Log.i(TAG, "All location settings are satisfied.");
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            //Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the result
                                // in onActivityResult().
                                status.startResolutionForResult(HomeActivity.this, LOCATION_PERMISSION_CODE);
                            } catch (IntentSender.SendIntentException e) {
                                Log.i("dev", "PendingIntent unable to execute request.");
                            }
                            break;/*
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                            break;*/
                    }
                }
            });

            LocationSettingsRequest locationSettingsRequest = builder.build();

            // Check whether location settings are satisfied
            // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
            SettingsClient settingsClient = LocationServices.getSettingsClient(this);
            settingsClient.checkLocationSettings(locationSettingsRequest);

            // new Google API SDK v11 uses getFusedLocationProviderClient(this)
            getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            // do work here
                            boolean coordinatesSetBefore = currentCoordinates != null;
                            onLocationChanged(locationResult.getLastLocation());
                            if (!coordinatesSetBefore) {
                                map.moveCamera(CameraUpdateFactory.newLatLng(currentCoordinates));
                                if (currentCoordinates != null)
                                    getEventsNearby(currentCoordinates.latitude, currentCoordinates.longitude);
                            }
                        }
                    },
                    Looper.myLooper());
        }
    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
/*
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(HomeActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        LOCATION_PERMISSION_CODE);
                            }
                        })
                        .create()
                        .show();

*/
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_CODE);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        startLocationUpdates();
                        //Request location updates:
                        //locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {
                    ActivityHelper.DisplayCustomToast(HomeActivity.this, getString(R.string.permission_denied), Toast.LENGTH_LONG);
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }


    public void onLocationChanged(Location location) {
        // New location has now been determined
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        currentCoordinates = new LatLng(location.getLatitude(), location.getLongitude());
        addCurrentPositionMapMarker();
    }

    public void Test(View v) {
        getEventsNearby(currentCoordinates.latitude, currentCoordinates.longitude);
    }

    private void getEventsNearby(double latitude, double longitude) {
        BoonApiInterface apiService = RetrofitBuilder.returnService();
        String accessToken = CommonOperations.getAccessToken(this);
        Call<JSONResponse> call = apiService.getAllEvents(accessToken, latitude, longitude);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.body().isSuccess()) {
                    Gson gson = new Gson();
                    String json = gson.toJson(response.body().getPayload());
                    List<Event> eventList = gson.fromJson(json, new TypeToken<List<Event>>() {
                    }.getType());
                    //if (eventList.size() > 0) {
                    events = new ArrayList<Event>();
                    if (eventMarkers != null) {
                        for (Marker m : eventMarkers)
                            m.remove();
                    }
                    eventMarkers = new ArrayList<Marker>();

                    for (Event e : eventList) {
                        events.add(e);
                        String mapMarkerState;
                        if (TimeHelper.isEventNow(ActivityHelper.stringToDateTime(e.getLatestStartDate())))
                            mapMarkerState = "NOW";
                        else
                            mapMarkerState = "LATER";
                        LatLng eventLocation = new LatLng(e.getLatitude(), e.getLongitude());
                        final MarkerOptions myMarkerOptions = new MarkerOptions()
                                .position(eventLocation)
                                .icon(MapHelper.getMapIcon(HomeActivity.this, e.getCategory(), mapMarkerState));

                        Marker eventMarker = map.addMarker(myMarkerOptions);
                        eventMarkers.add(eventMarker);
                    }
                    if (events.size() > 1) {
                        updateSelectedMarker(0);
                        eventsCounterTextView.setVisibility(View.VISIBLE);
                    } else eventsCounterTextView.setVisibility(View.INVISIBLE);

                    //add all events to screen via recyclerview
                    EventsAdapter adapter = new EventsAdapter(events, HomeActivity.this, getSupportFragmentManager(), currentCoordinates);
                    eventsRecyclerView.setAdapter(adapter);
                    eventsRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    //}

                } else {
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

    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);
        checkLocationPermission();
        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("HomeActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }

    private void getUserInfo() {
        BoonApiInterface apiService = RetrofitBuilder.returnService();
        final String accessToken = CommonOperations.getAccessToken(this);
        Call<JSONResponse> call = apiService.getMyInfo(accessToken);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.body().isSuccess()) {
                    Gson gson = new Gson();
                    String jsonString = new JSONObject((Map) response.body().getPayload()).toString();
                    User user1 = gson.fromJson(jsonString, User.class);
                    CommonOperations.saveUserInfo(HomeActivity.this, user1);
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
        /*
        if (currentCoordinates == null) {
            currentCoordinates = new LatLng(40.891444, 29.379922);
        }*/
        addCurrentPositionMapMarker();
        /*
        Marker testmarker = map.addMarker(new MarkerOptions()
                .position(new LatLng(currentCoordinates.latitude + 0.001, currentCoordinates.longitude + 0.001))
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapHelper.getBitmapFromVectorDrawable(this.getResources().getDrawable(R.drawable.chat)))));
        */

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                int pos = 0;
                for (Marker m : eventMarkers) {
                    if (marker.equals(m)) {
                        eventsRecyclerView.smoothScrollToPosition(pos);
                        updateSelectedMarker(pos);
                        break;
                    }
                    pos++;
                }
                return true;//marker.getId().equals(currentPositionMarker.getId());
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

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                killDetailsFragment();
            }
        });
    }

    private void addCurrentPositionMapMarker() {
        if (currentPositionMarker != null) {
            currentPositionMarker.remove();
        }
        if (currentCoordinates != null) {
            final MarkerOptions myMarkerOptions = new MarkerOptions()
                    .position(currentCoordinates)
                    .icon(MapHelper.getDefaultMapIcon(this));

            currentPositionMarker = map.addMarker(myMarkerOptions);
        }
    }

    private void updateSelectedMarker(int index) {
        //update previously selectedItem marker
        Event previouslySelectedEvent = events.get(selectedItemPosition);
        String mapMarkerState;
        if (TimeHelper.isEventNow(ActivityHelper.stringToDateTime(previouslySelectedEvent.getLatestStartDate())))
            mapMarkerState = "NOW";
        else
            mapMarkerState = "LATER";
        LatLng eventLocation = new LatLng(previouslySelectedEvent.getLatitude(), previouslySelectedEvent.getLongitude());
        final MarkerOptions previousMyMarkerOptions = new MarkerOptions()
                .position(eventLocation)
                .icon(MapHelper.getMapIcon(HomeActivity.this, previouslySelectedEvent.getCategory(), mapMarkerState));
        Marker previouslySelectedEventMarker = eventMarkers.get(selectedItemPosition);
        previouslySelectedEventMarker.remove();
        previouslySelectedEventMarker = map.addMarker(previousMyMarkerOptions);
        eventMarkers.remove(selectedItemPosition);
        eventMarkers.add(selectedItemPosition, previouslySelectedEventMarker);

        //updating first marker as selected
        Marker selectedMarker = eventMarkers.get(index);
        selectedMarker.remove();
        Event selectedEvent = events.get(index);
        LatLng selectedEventLocation = new LatLng(selectedEvent.getLatitude(), selectedEvent.getLongitude());
        MarkerOptions myMarkerOptions = new MarkerOptions()
                .position(selectedEventLocation)
                .icon(MapHelper.getMapIcon(HomeActivity.this, selectedEvent.getCategory(), "SELECTED"));
        selectedMarker = map.addMarker(myMarkerOptions);
        eventMarkers.remove(index);
        eventMarkers.add(index, selectedMarker);
        selectedItemPosition = index;

        updateRecyclerViewPositionIndicator(selectedItemPosition);
    }

    private void updateRecyclerViewPositionIndicator(int pos) {
        eventsCounterTextView.setText(pos + 1 + "/" + events.size());
    }
    private void killDetailsFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentByTag("details");
        if (f != null)
            fm.beginTransaction().remove(f).commit();
    }
}
