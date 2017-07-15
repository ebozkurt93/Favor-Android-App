package android.ebozkurt.com.favor;

import android.content.Context;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.BottomNavigationViewHelper;
import android.ebozkurt.com.favor.helpers.CounterHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.ebozkurt.com.favor.helpers.MapHelper;
import android.ebozkurt.com.favor.helpers.MyMapInfoWindowAdapter;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class CreateEvent2Activity extends FragmentActivity implements CounterHandler.CounterListener, OnMapReadyCallback {

    //x points left variables
    EditText description;
    TextView description_counter, points, eventEndDate;
    ImageButton minus, plus;
    AHBottomNavigation bottomNavigationView;
    Button create;
    RadioButton nowRadioButton, laterRadioButton;
    TextView title;
    ImageButton back;
    private GoogleMap mMap;
    String category_id, category_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event2);
        ActivityHelper.initialize(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        category_id = getIntent().getStringExtra("category_id");
        category_name = getIntent().getStringExtra("category_name");

        description = (EditText) findViewById(R.id.activity_create_event2_description_edittext);
        description_counter = (TextView) findViewById(R.id.activity_create_event2_description_counter_textview);
        description.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.event_description_max_length))});
        points = (TextView) findViewById(R.id.activity_create_event2_point_textview);
        minus = (ImageButton) findViewById(R.id.activity_create_event2_minus_imagebutton);
        plus = (ImageButton) findViewById(R.id.activity_create_event2_plus_imagebutton);
        bottomNavigationView = (AHBottomNavigation) findViewById(R.id.activity_create_event2_bottom_navigation_bar);
        BottomNavigationViewHelper.initialize(bottomNavigationView, 2);
        create = (Button) findViewById(R.id.activity_create_event2_create_button);
        create.setText(getResources().getString(R.string.post_event, getResources().getString(R.string.app_name)));
        nowRadioButton = (RadioButton) findViewById(R.id.activity_create_event2_time_now_radiobutton);
        eventEndDate = (TextView) findViewById(R.id.activity_create_event2_time_till_textview);
        setEndDateForEvent(true);
        //not used, since there is 2 options only
        laterRadioButton = (RadioButton) findViewById(R.id.activity_create_event2_time_later_radiobutton);

        nowRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setEndDateForEvent(isChecked);
            }
        });

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

        //todo add rapid counter for points on long press
/*
        new CounterHandler.Builder()
                .incrementalView(plus)
                .decrementalView(minus)
                .minRange(0) // cant go any less than -50
                .maxRange(999) // cant go any further than 50
                .isCycle(false) // 49,50,-50,-49 and so on
                .counterDelay(200) // speed of counter
                .counterStep(2)  // steps e.g. 0,2,4,6...
                .listener(this) // to listen counter results and show them in app
                .build();
*/

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.valueOf(points.getText().toString());
                if (value > 0) {
                    value = value - 1;
                    points.setText(String.valueOf(value));
                    checkAllForPosting();
                }
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.valueOf(points.getText().toString());
                if (value < 999) {
                    value = value + 1;
                    points.setText(String.valueOf(value));
                    checkAllForPosting();
                }
            }
        });
    }

    @Override
    public void onIncrement(View view, long number) {
        points.setText(String.valueOf(number));

    }

    @Override
    public void onDecrement(View view, long number) {
        points.setText(String.valueOf(number));

    }

    public void checkAllForPosting() {
        int pointValue = Integer.valueOf(points.getText().toString());
        if (pointValue > 0 && description.getText().length() > 0) {
            create.setEnabled(true);
        } else create.setEnabled(false);
    }

    public void setEndDateForEvent(boolean isNowRadioButtonChecked) {
        if (isNowRadioButtonChecked) {
            //1 hour
            setEventEndDateTextView(1);
        } else {
            //24 hours
            setEventEndDateTextView(24);
        }
    }

    public void setEventEndDateTextView(int hoursToAdd) {

        Calendar eventDate = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        String day;
        eventDate.add(Calendar.HOUR, hoursToAdd);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(eventDate.getTime());
        time = "<b>" + time + "</b>";
        if (today.get(Calendar.YEAR) == eventDate.get(Calendar.YEAR) && today.get(Calendar.DAY_OF_YEAR) == eventDate.get(Calendar.DAY_OF_YEAR)) {
            //today
            day = getResources().getString(R.string.today);
        } else {
            //tomorrow
            day = getResources().getString(R.string.tomorrow);
        }
        String finalText = String.format(getResources().getString(R.string.till_timeVar_dayVar), time, day);
        eventEndDate.setText(Html.fromHtml(finalText));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MapHelper.setMapSettings(mMap, this, false);

        //LatLng sabanciUniv = new LatLng(40.891444, 29.379922);
        LatLng sabanciUniv = new LatLng(40.7143528,-74.0059731);

        MarkerOptions myMarkerOptions = new MarkerOptions()
                .position(sabanciUniv)
                .icon(MapHelper.getMapIcon(this, category_id));
                //.title("Template marker title");
        mMap.addMarker(myMarkerOptions).showInfoWindow();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sabanciUniv));

        try {
            Geocoder geo = new Geocoder(CreateEvent2Activity.this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(sabanciUniv.latitude, sabanciUniv.longitude, 1);
            if (addresses.isEmpty()) {
                myMarkerOptions.title("Waiting for Location");
            }
            else {
                if (addresses.size() > 0) {
                    myMarkerOptions.title(addresses.get(0).getAddressLine(0)); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                    //myMarkerOptions.title(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                    //Toast.makeText(getApplicationContext(), "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }
    }


}
