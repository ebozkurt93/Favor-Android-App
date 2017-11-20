package android.ebozkurt.com.favor;

import android.ebozkurt.com.favor.domain.Event;
import android.ebozkurt.com.favor.domain.User;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.CategoryHelper;
import android.ebozkurt.com.favor.helpers.MapHelper;
import android.ebozkurt.com.favor.helpers.TimeHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

/**
 * Created by erdem on 11.11.2017.
 */

public class EventCardDetailFragment extends Fragment {

    private String name;
    private String description;
    private int points;
    private String[] expiration;
    private int distance;
    private String category;

    public EventCardDetailFragment newInstance(Event event, int distance) {
        EventCardDetailFragment eventCardDetailFragment = new EventCardDetailFragment();
        /*
        name = event.getCreator().getName();
        description = "random desc template";
        points = event.getPoints();
        Calendar eventLatestStartDate = ActivityHelper.stringToDateTime(event.getLatestStartDate());
        //expiration = TimeHelper.setEventExpirationDate(getContext(), eventLatestStartDate, true);
        this.distance = distance;
        category = event.getCategory();
        */
        Bundle args = new Bundle();
        args.putString("name", event.getCreator().getName());
        args.putString("description", "random desc template");
        args.putInt("points", event.getPoints());
        Calendar eventLatestStartDate = ActivityHelper.stringToDateTime(event.getLatestStartDate());
        args.putString("expiration", event.getLatestStartDate());
        args.putInt("distance", distance);
        args.putString("category", event.getCategory());
        args.putString("description", event.getCreator().getDescription());
        eventCardDetailFragment.setArguments(args);

        return eventCardDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString("name");
        description = getArguments().getString("description");
        points = getArguments().getInt("points");
        //TimeHelper.setEventExpirationDate(getContext(), eventLatestStartDate, true)
        expiration = TimeHelper.setEventExpirationDate(getContext(), ActivityHelper.stringToDateTime(getArguments().getString("expiration")), true);
        distance = getArguments().getInt("distance");
        category = getArguments().getString("category");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_detail_card_expanded, container, false);
        //event creator details
        TextView nameTextView = (TextView) view.findViewById(R.id.event_detail_card_expanded_name_textview);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.event_detail_card_expanded_description_textview);
        //event details
        TextView pointsTextView = (TextView) view.findViewById(R.id.event_detail_card_expanded_details_reward_points_textview);
        TextView endDateTextView = (TextView) view.findViewById(R.id.event_detail_card_expanded_details_expiration_textview);
        TextView distanceTextView = (TextView) view.findViewById(R.id.event_detail_card_expanded_details_location_distance_textview);
        TextView categoryTextView = (TextView) view.findViewById(R.id.event_detail_card_expanded_details_category_text_textview);

        nameTextView.setText(name);
        descriptionTextView.setText(description);
        pointsTextView.setText(String.format(getResources().getString(R.string.x_points), Integer.toString(points)));
        String expirationString = String.format(getResources().getString(R.string.timeVar_dayVar), expiration[0], expiration[1]);
        endDateTextView.setText(expirationString);
        distanceTextView.setText(String.format(getResources().getString(R.string.distance_away), distance + " m."));
        categoryTextView.setText(getResources().getString(CategoryHelper.getCategoryName(category)));

        return view;
    }
}
