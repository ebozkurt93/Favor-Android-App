package android.ebozkurt.com.favor;

import android.content.Intent;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.BottomNavigationViewHelper;
import android.ebozkurt.com.favor.helpers.CounterHandler;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreateEvent2Activity extends AppCompatActivity implements CounterHandler.CounterListener, OnMapReadyCallback {

    //add till ... today, x points left variables
    EditText description;
    TextView description_counter, points;
    ImageButton minus, plus;
    AHBottomNavigation bottomNavigationView;
    Button create;
    TextView title;
    ImageButton back;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event2);
        ActivityHelper.initialize(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        String category_id = getIntent().getStringExtra("category_id");
        String category_name = getIntent().getStringExtra("category_name");

        description = (EditText) findViewById(R.id.activity_create_event2_description_edittext);
        description_counter = (TextView) findViewById(R.id.activity_create_event2_description_counter_textview);
        description.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.description_max_length))});
        points = (TextView) findViewById(R.id.activity_create_event2_point_textview);
        minus = (ImageButton) findViewById(R.id.activity_create_event2_minus_imagebutton);
        plus = (ImageButton) findViewById(R.id.activity_create_event2_plus_imagebutton);
        bottomNavigationView = (AHBottomNavigation) findViewById(R.id.activity_create_event2_bottom_navigation_bar);
        BottomNavigationViewHelper.initialize(bottomNavigationView, 2);
        create = (Button) findViewById(R.id.activity_create_event2_create_button);
        create.setText(getResources().getString(R.string.post_event, getResources().getString(R.string.app_name)));

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
                    description_counter.setText(description.getText().length() + " / " + getResources().getInteger(R.integer.description_max_length));
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
                description_counter.setText(descriptionLength + " / " + getResources().getInteger(R.integer.description_max_length));
                if (descriptionLength == 100) {
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
