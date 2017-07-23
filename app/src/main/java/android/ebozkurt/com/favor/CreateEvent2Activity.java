package android.ebozkurt.com.favor;

import android.app.Activity;
import android.content.Intent;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.BottomNavigationViewHelper;
import android.ebozkurt.com.favor.helpers.CounterHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.ebozkurt.com.favor.helpers.MapHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreateEvent2Activity extends AppCompatActivity implements CounterHandler.CounterListener, OnMapReadyCallback {

    //x eventPoints left variables
    EditText description;
    TextView description_counter, eventPoints, userPointsTextView;
    ImageButton minus, plus;
    AHBottomNavigation bottomNavigationView;
    Button create;
    RadioButton nowRadioButton, laterRadioButton;

    //actionbar components
    TextView title;
    ImageView back;

    GoogleMap map;
    String category_id, category_name;
    LatLng coordinates; //todo update this for getting current coordinates, or coordinate of a preselected place

    int userPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event2);
        ActivityHelper.initialize(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_create_event2_mapfragment);
        mapFragment.getMapAsync(this);

        category_id = getIntent().getStringExtra("category_id");
        category_name = getIntent().getStringExtra("category_name");
        userPoints = 256;

        description = (EditText) findViewById(R.id.activity_create_event2_description_edittext);
        description_counter = (TextView) findViewById(R.id.activity_create_event2_description_counter_textview);
        description.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.event_description_max_length))});
        eventPoints = (TextView) findViewById(R.id.activity_create_event2_point_textview);
        eventPoints.setText(Integer.toString(0));
        userPointsTextView = (TextView) findViewById(R.id.activity_create_event2_user_points_textview);
        userPointsTextView.setText(Integer.toString(userPoints));
        minus = (ImageButton) findViewById(R.id.activity_create_event2_minus_imagebutton);
        plus = (ImageButton) findViewById(R.id.activity_create_event2_plus_imagebutton);
        bottomNavigationView = (AHBottomNavigation) findViewById(R.id.activity_create_event2_bottom_navigation_bar);
        BottomNavigationViewHelper.initialize(bottomNavigationView, 2);
        create = (Button) findViewById(R.id.activity_create_event2_create_button);
        create.setText(getResources().getString(R.string.post_event, getResources().getString(R.string.app_name)));
        nowRadioButton = (RadioButton) findViewById(R.id.activity_create_event2_time_now_radiobutton);
        //not used, since there is 2 options only
        laterRadioButton = (RadioButton) findViewById(R.id.activity_create_event2_time_later_radiobutton);

        setEventEndDateRadioButton(nowRadioButton, 1);
        setEventEndDateRadioButton(laterRadioButton, 24);

        title = (TextView) findViewById(R.id.sign_up1_action_bar_middle_text_view);
        title.setText(category_name);
        back = (ImageButton) findViewById(R.id.sign_up1_action_bar_image_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    description_counter.setVisibility(View.VISIBLE);
                    description_counter.setText(description.getText().length() + " / " + getResources().getInteger(R.integer.event_description_max_length));
                }
            }
        });

        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int descriptionLength = description.getText().length();
                description_counter.setText(descriptionLength + " / " + getResources().getInteger(R.integer.event_description_max_length));
                if (descriptionLength == getResources().getInteger(R.integer.event_description_max_length)) {
                    description_counter.setTextColor(getResources().getColor(R.color.createEventCounterMax));
                } else {
                    description_counter.setTextColor(getResources().getColor(R.color.createEventText));
                }
                checkAllForPosting();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        new CounterHandler.Builder()
                .incrementalView(plus)
                .decrementalView(minus)
                .startNumber(0)
                .minRange(0) // cant go any less than -50
                .maxRange(userPoints) // cant go any further than 50
                .isCycle(false) // 49,50,-50,-49 and so on
                .counterDelay(50) // speed of counter
                .counterStep(1)  // steps e.g. 0,2,4,6...
                .maxCounterStep(3)
                .listener(this) // to listen counter results and show them in app
                .build();

        //sabancı coordinates
        coordinates = new LatLng(40.891444, 29.379922);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                category_id = data.getStringExtra("category_id");
                category_name = data.getStringExtra("category_name");
                coordinates = data.getParcelableExtra("position");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }


    @Override
    public void onIncrement(View view, long number) {
        eventPoints.setText(String.valueOf(number));

    }

    @Override
    public void onDecrement(View view, long number) {
        eventPoints.setText(String.valueOf(number));

    }

    public void checkAllForPosting() {
        int pointValue = Integer.valueOf(eventPoints.getText().toString());
        if (pointValue > 0 && description.getText().length() > 0) {
            create.setEnabled(true);
        } else create.setEnabled(false);
    }


    public void setEventEndDateRadioButton(RadioButton button, int hoursToAdd) {
        String day, time;

        Calendar eventDate = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        eventDate.add(Calendar.HOUR, hoursToAdd);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        time = sdf.format(eventDate.getTime());
        if (today.get(Calendar.YEAR) == eventDate.get(Calendar.YEAR) && today.get(Calendar.DAY_OF_YEAR) == eventDate.get(Calendar.DAY_OF_YEAR)) {
            //today
            day = getResources().getString(R.string.today);
        } else {
            String[] days = getResources().getStringArray(R.array.days_short);
            day = days[eventDate.get(Calendar.DAY_OF_WEEK) - 1];
        }
        String finalText = String.format(getResources().getString(R.string.timeVar_dayVar), time, day);
        button.setText(finalText);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        MapHelper.setMapSettings(map, this, false, false);


        //LatLng coordinates = new LatLng(40.7143528,-74.0059731); //ny coordinates
        String addressText = MapHelper.getAddress(CreateEvent2Activity.this.getApplicationContext(), coordinates);

        final MarkerOptions myMarkerOptions = new MarkerOptions()
                .position(coordinates)
                .title(addressText)
                .icon(MapHelper.getMapIcon(this, category_id));

        final Marker marker = map.addMarker(myMarkerOptions);

        map.addMarker(myMarkerOptions).showInfoWindow();
        map.moveCamera(CameraUpdateFactory.newLatLng(coordinates));

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent i = new Intent(CreateEvent2Activity.this, CreateEventMapActivity.class);
                i.putExtra("position", coordinates);
                i.putExtra("category_id", category_id);
                i.putExtra("category_name", category_name);
                startActivityForResult(i, 1);
                return false;
            }
        });

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent i = new Intent(CreateEvent2Activity.this, CreateEventMapActivity.class);
                i.putExtra("position", coordinates);
                i.putExtra("category_id", category_id);
                i.putExtra("category_name", category_name);
                startActivityForResult(i, 1);
            }
        });

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                marker.showInfoWindow();
            }
        });
    }


}
