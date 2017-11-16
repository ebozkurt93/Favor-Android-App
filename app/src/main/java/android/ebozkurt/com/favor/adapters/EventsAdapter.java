package android.ebozkurt.com.favor.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.ebozkurt.com.favor.EventCardDetailFragment;
import android.ebozkurt.com.favor.R;
import android.ebozkurt.com.favor.domain.Event;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.CategoryHelper;
import android.ebozkurt.com.favor.helpers.MapHelper;
import android.ebozkurt.com.favor.helpers.TimeHelper;
import android.ebozkurt.com.favor.views.CustomRatingBar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by erdem on 27.10.2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView descriptionTextView;
        public TextView pointsTextView;
        public CustomRatingBar ratingCustomRatingBar;
        public ImageView categoryImageView;
        public TextView timeLeftTextView;
        public TextView distanceTextView;
        public View view1, view2;
        public Button sendRequestButton;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.event_detail_card_event_creator_textview);
            descriptionTextView = (TextView) itemView.findViewById(R.id.event_detail_card_event_description_textview);
            pointsTextView = (TextView) itemView.findViewById(R.id.event_detail_card_event_points_textview);
            ratingCustomRatingBar = (CustomRatingBar) itemView.findViewById(R.id.event_detail_card_event_creator_rating);
            categoryImageView = (ImageView) itemView.findViewById(R.id.event_detail_card_event_category);
            timeLeftTextView = (TextView) itemView.findViewById(R.id.event_detail_card_event_end_textview);
            distanceTextView = (TextView) itemView.findViewById(R.id.event_detail_card_distance_textview);
            sendRequestButton = (Button) itemView.findViewById(R.id.event_detail_card_send_request_button);

            view1 = (View) itemView.findViewById(R.id.event_detail_card_view1);
            view2 = (View) itemView.findViewById(R.id.event_detail_card_view2);
        }
    }

    private List<Event> events;
    private Context context;
    private android.support.v4.app.FragmentManager fragmentManager;
    private LatLng myLocation;

    public EventsAdapter(List<Event> events, Context context, android.support.v4.app.FragmentManager fragmentManager, LatLng myLocation) {
        this.events = events;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.myLocation = myLocation;
    }

    private Context getContext() {
        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View eventView = inflater.inflate(R.layout.event_detail_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(eventView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Event event = events.get(position);
        holder.nameTextView.setText(event.getCreator().getName());
        holder.descriptionTextView.setText(event.getDescription());
        holder.pointsTextView.setText("" + event.getPoints());
        if (event.getCreator().getRating() == null)
            holder.ratingCustomRatingBar.setScore(0f);
        else holder.ratingCustomRatingBar.setScore(event.getCreator().getRating().floatValue());
        holder.categoryImageView.setImageDrawable(context.getResources().getDrawable(CategoryHelper.getCategoryIcon(event.getCategory())));

        Calendar eventLatestStartDate = ActivityHelper.stringToDateTime(event.getLatestStartDate());
        String[] arr = TimeHelper.setEventExpirationDate(context, eventLatestStartDate, true);
        holder.timeLeftTextView.setText(String.format(context.getResources().getString(R.string.timeVar_dayVar_newLine), arr[0], arr[1]));
        int distance = (int) MapHelper.distance(event.getLatitude(), myLocation.latitude, event.getLongitude(), myLocation.longitude);
        holder.distanceTextView.setText(distance + " m.");
        int color;
        if (TimeHelper.isEventNow(eventLatestStartDate))
            color = getContext().getResources().getColor(R.color.marker_background_now);
        else
            color = getContext().getResources().getColor(R.color.marker_background_later);
        holder.view1.setBackgroundTintList(ColorStateList.valueOf(color));
        holder.view2.setBackgroundColor(color);


        holder.sendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open eventcarddetailfragment
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.add(R.id.activity_home_event_card_detail_fragment_framelayout,
                        new EventCardDetailFragment().newInstance(event, (int) MapHelper.distance(event.getLatitude(), myLocation.latitude, event.getLongitude(), myLocation.longitude)),"details");
                ft.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
