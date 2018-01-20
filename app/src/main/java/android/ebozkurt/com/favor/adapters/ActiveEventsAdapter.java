package android.ebozkurt.com.favor.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.ebozkurt.com.favor.R;
import android.ebozkurt.com.favor.domain.Event;
import android.ebozkurt.com.favor.domain.User;
import android.ebozkurt.com.favor.domain.helpers.JSONResponse;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.CategoryHelper;
import android.ebozkurt.com.favor.helpers.CommonOperations;
import android.ebozkurt.com.favor.helpers.TimeHelper;
import android.ebozkurt.com.favor.network.BoonApiInterface;
import android.ebozkurt.com.favor.network.RetrofitBuilder;
import android.ebozkurt.com.favor.views.CustomRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by erdem on 20.01.2018.
 */

public class ActiveEventsAdapter extends RecyclerView.Adapter<ActiveEventsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView nameTextView;
        public TextView descriptionTextView;
        public TextView pointsTextView;
        public CustomRatingBar ratingCustomRatingBar;
        public ImageView categoryImageView;
        public TextView timeLeftTextView;
        public TextView distanceTextView;
        public View view1, view2;
        public Button deleteEventButton;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.event_detail_card_event_creator_textview);
            descriptionTextView = (TextView) itemView.findViewById(R.id.event_detail_card_event_description_textview);
            pointsTextView = (TextView) itemView.findViewById(R.id.event_detail_card_event_points_textview);
            ratingCustomRatingBar = (CustomRatingBar) itemView.findViewById(R.id.event_detail_card_event_creator_rating);
            categoryImageView = (ImageView) itemView.findViewById(R.id.event_detail_card_event_category);
            timeLeftTextView = (TextView) itemView.findViewById(R.id.event_detail_card_event_end_textview);
            distanceTextView = (TextView) itemView.findViewById(R.id.event_detail_card_distance_textview);
            deleteEventButton = (Button) itemView.findViewById(R.id.event_detail_card_send_request_button);

            view1 = (View) itemView.findViewById(R.id.event_detail_card_view1);
            view2 = (View) itemView.findViewById(R.id.event_detail_card_view2);
        }
    }

    private List<Event> events;
    private Context context;
    private android.support.v4.app.FragmentManager fragmentManager;
    private Activity activity;

    public ActiveEventsAdapter(List<Event> events, Context context, android.support.v4.app.FragmentManager fragmentManager, Activity activity) {
        this.events = events;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
    }

    private Context getContext() {
        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View eventView = inflater.inflate(R.layout.event_detail_card, parent, false);
        eventView = ActivityHelper.adjustItemWidthForRecyclerView(context, eventView);

        ViewHolder viewHolder = new ActiveEventsAdapter.ViewHolder(eventView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Event event = events.get(position);
        holder.setIsRecyclable(false);
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
        //int distance = (int) MapHelper.distance(event.getLatitude(), myLocation.latitude, event.getLongitude(), myLocation.longitude);
        holder.distanceTextView.setVisibility(View.INVISIBLE);
        final int color;
        if (TimeHelper.isEventNow(eventLatestStartDate))
            color = getContext().getResources().getColor(R.color.marker_background_now);
        else
            color = getContext().getResources().getColor(R.color.marker_background_later);
        holder.view1.setBackgroundTintList(ColorStateList.valueOf(color));
        holder.view2.setBackgroundColor(color);

        if(event.getHelper() != null) {
            holder.deleteEventButton.setVisibility(View.INVISIBLE);
        }
        else {
            holder.deleteEventButton.setText(context.getResources().getString(R.string.delete));
        }


        holder.deleteEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo add are you sure to delete dialog
                //AlertDialogFragment dialogFragment = AlertDialogFragment.newInstance("a","b","c","d");
                //dialogFragment.show(fragmentManager, "alert_fragment");
                //ActivityHelper.DisplayCustomToast(context, "Coming soon...");
                deleteEvent(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }


    public void deleteEvent(final int position) {
        final Event event = events.get(position);
        BoonApiInterface apiService = RetrofitBuilder.returnService();
        String accessToken = CommonOperations.getAccessToken(context);
        Call<JSONResponse> call = apiService.deleteEvent(accessToken, event);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.body() instanceof JSONResponse) {
                    if (response.body().isSuccess()) {
                        //removeAt(position);
                        ActivityHelper.DisplayCustomToast(context, context.getResources().getString(R.string.success));
                        User user = CommonOperations.getUserInfo(activity);
                        user.setPoints(user.getPoints() + event.getPoints());
                        user.setActiveEventCount(user.getActiveEventCount() + 1);
                        CommonOperations.saveUserInfo(activity, user);
                        ActivityHelper.restartActivity(activity);
                    } else {
                        String message = response.body().getError().getMessage();
                        ActivityHelper.DisplayCustomToast(context, message);
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                ActivityHelper.DisplayGeneralErrorToast(context);
            }
        });
    }

    public void removeAt(int position) {
        events.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, events.size());
    }

}
