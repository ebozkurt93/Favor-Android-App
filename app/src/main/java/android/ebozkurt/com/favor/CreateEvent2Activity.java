package android.ebozkurt.com.favor;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.ebozkurt.com.favor.domain.Event;
import android.ebozkurt.com.favor.domain.User;
import android.ebozkurt.com.favor.domain.helpers.EventCreate;
import android.ebozkurt.com.favor.domain.helpers.JSONResponse;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.BottomNavigationViewHelper;
import android.ebozkurt.com.favor.helpers.CommonOperations;
import android.ebozkurt.com.favor.helpers.CounterHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.ebozkurt.com.favor.helpers.KeyboardHelper;
import android.ebozkurt.com.favor.helpers.MapHelper;
import android.ebozkurt.com.favor.helpers.TimeHelper;
import android.ebozkurt.com.favor.network.BoonApiInterface;
import android.ebozkurt.com.favor.network.RetrofitBuilder;
import android.ebozkurt.com.favor.views.LoadingDialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEvent2Activity extends AppCompatActivity implements CounterHandler.CounterListener, OnMapReadyCallback {

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

    Animation shake;

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
        ActivityHelper.hideKeyboardWhenEdittextNotFocused(getWindow().getDecorView().getRootView(), CreateEvent2Activity.this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_create_event2_mapfragment);
        mapFragment.getMapAsync(this);

        category_id = getIntent().getStringExtra("category_id");
        category_name = getIntent().getStringExtra("category_name");
        userPoints = CommonOperations.getUserInfo(this).getPoints();

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

        //BitmapHelper.currencyIconInitializer(this, userPointsTextView);

        minus = (ImageButton) findViewById(R.id.activity_create_event2_minus_imagebutton);
        plus = (ImageButton) findViewById(R.id.activity_create_event2_plus_imagebutton);
        bottomNavigationView = (AHBottomNavigation) findViewById(R.id.activity_create_event2_bottom_navigation_bar);
        BottomNavigationViewHelper.initialize(this, bottomNavigationView, 2);
        create = (Button) findViewById(R.id.activity_create_event2_create_button);
        create.setText(getResources().getString(R.string.post_event, getResources().getString(R.string.app_name)));
        nowRadioButton = (RadioButton) findViewById(R.id.activity_create_event2_time_now_radiobutton);
        //not used, since there is 2 options only
        laterRadioButton = (RadioButton) findViewById(R.id.activity_create_event2_time_later_radiobutton);
        markerState = "NOW";

        setNowLaterRadioButtonValues();

        title = (TextView) findViewById(R.id.sign_up1_action_bar_middle_text_view);
        title.setText(category_name);
        back = (ImageButton) findViewById(R.id.sign_up1_action_bar_image_button);
        shake = AnimationUtils.loadAnimation(this, R.anim.createevent2_point_button_shake_animation);

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
                    markerState = "NOW";
                } else {
                    markerState = "LATER";
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
                checkAllForPosting();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        eventPointsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo animation shake
                plus.startAnimation(shake);
                minus.startAnimation(shake);
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

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Event event = new Event();
                event.setDescription(description.getText().toString());
                event.setLatitude(coordinates.latitude);
                event.setLongitude(coordinates.longitude);
                event.setCategory(category_id);
                //event.setCreationDate();
                event.setPoints(Integer.parseInt(eventPointsTextView.getText().toString()));
                BoonApiInterface apiService = RetrofitBuilder.returnService();
                String accessToken = CommonOperations.getAccessToken(CreateEvent2Activity.this);
                boolean isNow = nowRadioButton.isChecked();

                EventCreate eventCreate = new EventCreate(event, isNow);

                Call<JSONResponse> call = apiService.createEvent(accessToken, eventCreate);
                final LoadingDialogFragment loadingDialogFragment = ActivityHelper.getLoadingDialog();
                loadingDialogFragment.show(getSupportFragmentManager(), "");
                call.enqueue(new Callback<JSONResponse>() {
                    @Override
                    public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                        loadingDialogFragment.dismiss();
                        if (response.body().isSuccess()) {
                            ActivityHelper.DisplayCustomToast(CreateEvent2Activity.this, String.format(getResources().getString(R.string.event_created), getString(R.string.app_name)), Toast.LENGTH_LONG);

                            User user = CommonOperations.getUserInfo(CreateEvent2Activity.this);
                            user.setPoints(user.getPoints() - Integer.valueOf(eventPointsTextView.getText().toString()));
                            user.setActiveEventCount(user.getActiveEventCount() - 1);
                            CommonOperations.saveUserInfo(CreateEvent2Activity.this, user);

                            Intent i = new Intent(CreateEvent2Activity.this, HomeActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            finish();
                        } else {
                            ActivityHelper.DisplayCustomToast(CreateEvent2Activity.this, response.body().getError().getMessage(), Toast.LENGTH_LONG);

                        }
                    }

                    @Override
                    public void onFailure(Call<JSONResponse> call, Throwable t) {
                        ActivityHelper.DisplayCustomToast(CreateEvent2Activity.this, getResources().getString(R.string.general_error), Toast.LENGTH_LONG);

                    }
                });
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
                checkAllForPosting();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        onMapReady(map);
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
        Calendar eventDate = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        eventDate.add(Calendar.HOUR, hoursToAdd);
        String[] endDate = TimeHelper.setEventExpirationDate(this, eventDate, false);
        String finalText = String.format(getResources().getString(R.string.timeVar_dayVar), endDate[0], endDate[1]);
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


    private void setNowLaterRadioButtonValues() {
        setEventEndDateRadioButton(nowRadioButton, 1);
        setEventEndDateRadioButton(laterRadioButton, 24);
    }
}
