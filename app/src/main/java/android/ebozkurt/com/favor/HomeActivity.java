package android.ebozkurt.com.favor;

import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.BitmapHelper;
import android.ebozkurt.com.favor.helpers.BottomNavigationViewHelper;
import android.ebozkurt.com.favor.helpers.MapHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
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

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {
    AHBottomNavigation bottomNavigationView;
    TextView userPointsTextView;
    //SearchView searchBar;

    GoogleMap map;
    LatLng currentCoordinates = new LatLng(40.891444, 29.379922);
    ; //todo update this for getting current coordinates, or coordinate of a preselected place
    Marker currentPositionMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActivityHelper.initialize(this);
        ActivityHelper.transparentStatusBar(getWindow(), getResources());

        bottomNavigationView = (AHBottomNavigation) findViewById(R.id.activity_home_bottom_navigation_bar);
        BottomNavigationViewHelper.initialize(this, bottomNavigationView, 0);

        userPointsTextView = (TextView) findViewById(R.id.activity_home_points_textview);
        BitmapHelper.currencyIconInitializer(this, userPointsTextView);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_home_mapfragment);
        mapFragment.getMapAsync(this);

        //searchBar = (SearchView) findViewById(R.id.activity_home_searchbar_search_view);
        //searchBar.setQueryHint(String.format(getResources().getString(R.string.search_bar_hint), getResources().getString(R.string.app_name)));
                //        create.setText(getResources().getString(R.string.post_event, getResources().getString(R.string.app_name)));


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
