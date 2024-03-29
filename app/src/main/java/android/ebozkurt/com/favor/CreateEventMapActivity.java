package android.ebozkurt.com.favor;

import android.app.Activity;
import android.content.Intent;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.CategoryHelper;
import android.ebozkurt.com.favor.helpers.MapHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class CreateEventMapActivity extends AppCompatActivity implements OnMapReadyCallback {


    //fake map marker components
    ImageView markerIcon;
    TextView markerTitle;
    Button done;
    //actionbar components
    TextView title;
    ImageView cancel;
    GoogleMap map;
    LatLng coordinates;
    String category_id, category_name, markerState;
    FrameLayout markerLayout;
    LatLng center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_map);
        ActivityHelper.initialize(this);
        ActivityHelper.transparentStatusBar(getWindow(), getResources());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_create_event_map_mapfragment);
        mapFragment.getMapAsync(this);

        coordinates = getIntent().getExtras().getParcelable("position");
        category_id = getIntent().getStringExtra("category_id");
        category_name = getIntent().getStringExtra("category_name");
        markerState = getIntent().getStringExtra("marker_state");

        cancel = (ImageButton) findViewById(R.id.activity_create_event_map_cancel_imagebutton);
        markerIcon = (ImageView) findViewById(R.id.default_map_marker_icon);
        markerIcon.setImageResource(CategoryHelper.getCategoryIcon(category_id));
        markerTitle = (TextView) findViewById(R.id.map_marker_title_title);
        markerLayout = (FrameLayout) findViewById(R.id.activity_create_event_map_include_default_marker);
        done = (Button) findViewById(R.id.activity_create_event_map_new_location_button);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (markerState.equals("later")) {
            markerLayout.setBackgroundTintList(getResources().getColorStateList(R.color.marker_background_later));
            markerLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;
                }
            });
        }

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateEventMapActivity.this, CreateEvent2Activity.class);
                i.putExtra("position", center);
                i.putExtra("category_id", category_id);
                i.putExtra("category_name", category_name);
                i.putExtra("marker_state", markerState);
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        MapHelper.setMapSettings(map, this, true, true);


        String addressText = MapHelper.getAddress(CreateEventMapActivity.this.getApplicationContext(), coordinates);
        markerTitle.setText(addressText);
        map.moveCamera(CameraUpdateFactory.newLatLng(coordinates));

        map.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                markerIcon.setAlpha(0.5f);
                markerTitle.setAlpha(0.5f);
                String address = MapHelper.getAddress(CreateEventMapActivity.this.getApplicationContext(), map.getCameraPosition().target);
                markerTitle.setText(address);
            }
        });
        map.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                markerIcon.setAlpha(1f);
                markerTitle.setAlpha(1f);
                String address = MapHelper.getAddress(CreateEventMapActivity.this.getApplicationContext(), map.getCameraPosition().target);
                center = map.getCameraPosition().target;
                markerTitle.setText(address);
            }
        });


    }
}
