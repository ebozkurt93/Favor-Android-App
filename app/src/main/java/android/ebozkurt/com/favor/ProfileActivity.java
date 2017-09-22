package android.ebozkurt.com.favor;

import android.content.Intent;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.BitmapHelper;
import android.ebozkurt.com.favor.helpers.BottomNavigationViewHelper;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    ImageView editProfileImageView;
    CircleImageView profilePictureCircleImageView;
    TextView nameLastnameTextView, descriptionTextView, pointsTextView, ongoingEventsTextView, reviewsTextView;
    AHBottomNavigation bottomNavigationView;

    String name, lastname, description;
    int points, numberOfActiveEvents, numberOfReviews;
    Drawable profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActivityHelper.initialize(this);


        bottomNavigationView = (AHBottomNavigation) findViewById(R.id.activity_profile_bottom_navigation_bar);
        BottomNavigationViewHelper.initialize(this, bottomNavigationView, 3);

        //todo get actual field values here to replace
        name = "ExampleName";
        lastname = "ExampleLastname";
        //description = "This is an example personal descriptionEditText, try changing it.";
        description = "";
        points = 112;
        profilePic = getDrawable(R.drawable.berkin);
        numberOfActiveEvents = 3;
        numberOfReviews = 5;

        editProfileImageView = (ImageView) findViewById(R.id.activity_profile_edit_imageview);
        profilePictureCircleImageView = (CircleImageView) findViewById(R.id.activity_profile_profile_picture_circleimageview);
        profilePictureCircleImageView.setImageDrawable(profilePic);
        nameLastnameTextView = (TextView) findViewById(R.id.activity_profile_name_lastname_textview);
        nameLastnameTextView.setText(name + " " + lastname);
        descriptionTextView = (TextView) findViewById(R.id.activity_profile_personal_description_textview);
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
        pointsTextView = (TextView) findViewById(R.id.activity_profile_points_textview);
        pointsTextView.setText(Integer.toString(points));
        //BitmapHelper.currencyIconInitializer(this, pointsTextView);
        ongoingEventsTextView = (TextView) findViewById(R.id.activity_profile_ongoing_events_textview);
        if(numberOfActiveEvents <= 0){
            ongoingEventsTextView.setVisibility(View.GONE);
            //todo recyclerview also gone
        }else {
            ongoingEventsTextView.setText(String.format(getResources().getString(R.string.ongoing_events), getResources().getString(R.string.app_name)));
        }
        reviewsTextView = (TextView) findViewById(R.id.activity_profile_review_count_textview);
        if(numberOfReviews <= 0) {
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

}
