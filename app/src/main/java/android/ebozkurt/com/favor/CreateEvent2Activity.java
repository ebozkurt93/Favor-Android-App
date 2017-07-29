package android.ebozkurt.com.favor;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.BottomNavigationViewHelper;
import android.ebozkurt.com.favor.helpers.CounterHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.ebozkurt.com.favor.helpers.KeyboardHelper;
import android.ebozkurt.com.favor.helpers.MapHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    LinearLayout layout;
    //x eventPointsTextView left variables
    EditText description;
    TextView description_counter, userPointsTextView, eventPointsTextView;
    ImageButton minus, plus;
    AHBottomNavigation bottomNavigationView;
    Button create;
    RadioButton nowRadioButton, laterRadioButton;
    String markerState;

    //actionbar components
    TextView title;
    ImageView back;

    //Animation shake;

    GoogleMap map;
    String category_id, category_name;
    LatLng coordinates; //todo update this for getting current coordinates, or coordinate of a preselected place
    Marker marker;

    int userPoints;

    BroadcastReceiver broadcastReceiver;

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


        layout = (LinearLayout) findViewById(R.id.activity_create_event2_layout);
        setupParent(layout);

        description = (EditText) findViewById(R.id.activity_create_event2_description_edittext);
        description_counter = (TextView) findViewById(R.id.activity_create_event2_description_counter_textview);
        description.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.event_description_max_length))});
        description.setImeOptions(EditorInfo.IME_ACTION_DONE);
        description.setRawInputType(InputType.TYPE_CLASS_TEXT);
        eventPointsTextView = (TextView) findViewById(R.id.activity_create_event2_point_textview);
        eventPointsTextView.setText(Integer.toString(0));
        //eventPointsTextView.setFilters(new InputFilter[]{ new InputFilterMinMax("0", Integer.toString(userPoints))});
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
        markerState = "now";

        setNowLaterRadioButtonValues();

        title = (TextView) findViewById(R.id.sign_up1_action_bar_middle_text_view);
        title.setText(category_name);
        back = (ImageButton) findViewById(R.id.sign_up1_action_bar_image_button);
        //shake = AnimationUtils.loadAnimation(this, R.anim.button_shake_animation);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    KeyboardHelper.hideSoftKeyboard(CreateEvent2Activity.this);
                }
            }
        });

        nowRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    markerState = "now";
                } else {
                    markerState = "later";
                }
                checkAllForPosting();
                marker.setIcon(MapHelper.getMapIcon(CreateEvent2Activity.this, category_id, markerState));
                marker.hideInfoWindow();
                marker.showInfoWindow();
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

        eventPointsTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int p = Integer.parseInt(eventPointsTextView.getText().toString());
                if (p < 0) {
                    eventPointsTextView.setText("0");
                } else if (p > userPoints) {
                    eventPointsTextView.setText(userPoints);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        eventPointsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo animation shake
                //plus.startAnimation(shake);
                //minus.startAnimation(shake);
                //plus.setTranslationX(20);
                //animate up and to full alpha.
                //plus.animate().translationX(0).setDuration(100).start();
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


        eventPointsTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int pointsLeft = userPoints - Integer.valueOf(eventPointsTextView.getText().toString());
                userPointsTextView.setText(Integer.toString(pointsLeft));
            }

            @Override
            public void afterTextChanged(Editable s) {
                int pointsLeft = userPoints - Integer.valueOf(eventPointsTextView.getText().toString());
                userPointsTextView.setText(Integer.toString(pointsLeft));
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0) {
                    setNowLaterRadioButtonValues();
                }
            }
        };

        registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    @Override
    public void onStop() {
        super.onStop();
        if (broadcastReceiver != null)
            unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                category_id = data.getStringExtra("category_id");
                category_name = data.getStringExtra("category_name");
                coordinates = data.getParcelableExtra("position");
                Log.i("dev", coordinates.toString());
                onMapReady(map);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }


    @Override
    public void onIncrement(View view, long number) {
        eventPointsTextView.setText(String.valueOf(number));
    }

    @Override
    public void onDecrement(View view, long number) {
        eventPointsTextView.setText(String.valueOf(number));
    }

    public void checkAllForPosting() {
        int pointValue = Integer.valueOf(eventPointsTextView.getText().toString());
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

        //sabancı coordinates
        if (coordinates == null) {
            coordinates = new LatLng(40.891444, 29.379922);
        }
        //LatLng coordinates = new LatLng(40.7143528,-74.0059731); //ny coordinates
        String addressText = MapHelper.getAddress(CreateEvent2Activity.this.getApplicationContext(), coordinates);

        final MarkerOptions myMarkerOptions = new MarkerOptions()
                .position(coordinates)
                .title(addressText)
                .icon(MapHelper.getMapIcon(this, category_id, markerState));

        marker = map.addMarker(myMarkerOptions);
        marker.showInfoWindow();

        LatLng cameraPos = new LatLng(coordinates.latitude + 0.001, coordinates.longitude);
        map.moveCamera(CameraUpdateFactory.newLatLng(cameraPos));

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mapActivityIntent();
                return false;
            }
        });

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                mapActivityIntent();
            }
        });

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                marker.showInfoWindow();
                mapActivityIntent();
            }
        });
    }

    public void mapActivityIntent() {
        Intent i = new Intent(CreateEvent2Activity.this, CreateEventMapActivity.class);
        marker.remove();
        i.putExtra("position", coordinates);
        i.putExtra("category_id", category_id);
        i.putExtra("category_name", category_name);
        i.putExtra("marker_state", markerState);
        startActivityForResult(i, 1);
    }

    protected void setupParent(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    KeyboardHelper.hideSoftKeyboard(CreateEvent2Activity.this);
                    return false;
                }
            });
        }
        //If a layout container, iterate over children
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupParent(innerView);
            }
        }
    }

    private void setNowLaterRadioButtonValues() {
        setEventEndDateRadioButton(nowRadioButton, 1);
        setEventEndDateRadioButton(laterRadioButton, 24);
    }
}
