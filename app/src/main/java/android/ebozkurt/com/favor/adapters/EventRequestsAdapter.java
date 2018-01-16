package android.ebozkurt.com.favor.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.ebozkurt.com.favor.R;
import android.ebozkurt.com.favor.domain.Event;
import android.ebozkurt.com.favor.domain.EventRequest;
import android.ebozkurt.com.favor.domain.User;
import android.ebozkurt.com.favor.domain.helpers.EventRequestAccept;
import android.ebozkurt.com.favor.domain.helpers.JSONResponse;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.BitmapHelper;
import android.ebozkurt.com.favor.helpers.CategoryHelper;
import android.ebozkurt.com.favor.helpers.CommonOperations;
import android.ebozkurt.com.favor.helpers.TimeHelper;
import android.ebozkurt.com.favor.helpers.WindowHelper;
import android.ebozkurt.com.favor.network.BoonApiInterface;
import android.ebozkurt.com.favor.network.RetrofitBuilder;
import android.ebozkurt.com.favor.views.CustomRatingBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by erdem on 9.01.2018.
 */

public class EventRequestsAdapter extends RecyclerView.Adapter<EventRequestsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView requestSenderTextView;
        public TextView descriptionTextView;
        public TextView pointsTextView;
        public CustomRatingBar ratingCustomRatingBar;
        public ImageView categoryImageView;
        public TextView timeLeftTextView;
        public TextView sendDateTextView;
        public View view1, view2;
        public Button acceptRequestButton;

        //public FrameLayout eventRequestCardFrameLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            requestSenderTextView = (TextView) itemView.findViewById(R.id.event_request_card_request_sender_textview);
            descriptionTextView = (TextView) itemView.findViewById(R.id.event_request_card_event_description_textview);
            pointsTextView = (TextView) itemView.findViewById(R.id.event_request_card_event_points_textview);
            ratingCustomRatingBar = (CustomRatingBar) itemView.findViewById(R.id.event_request_card_request_sender_rating);
            categoryImageView = (ImageView) itemView.findViewById(R.id.event_request_card_event_category);
            timeLeftTextView = (TextView) itemView.findViewById(R.id.event_request_card_event_end_textview);
            sendDateTextView = (TextView) itemView.findViewById(R.id.event_request_card_request_send_date_textview);
            acceptRequestButton = (Button) itemView.findViewById(R.id.event_request_card_send_request_button);

            view1 = (View) itemView.findViewById(R.id.event_request_card_view1);
            view2 = (View) itemView.findViewById(R.id.event_request_card_view2);

            //eventRequestCardFrameLayout = (FrameLayout) itemView.findViewById(R.id.event_request_card_frame_layout);

        }
    }

    private List<EventRequest> eventRequests;
    private Context context;

    public EventRequestsAdapter(List<EventRequest> eventRequests, Context context) {
        this.eventRequests = eventRequests;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View eventView = inflater.inflate(R.layout.event_request_card, parent, false);
        eventView.setLayoutParams(new RecyclerView.LayoutParams(WindowHelper.getWindowSize(context)[0] - BitmapHelper.dpToPx(context, 16), RecyclerView.LayoutParams.MATCH_PARENT));
        //WindowHelper.setMargins(eventView, 8, 0, 8, 0);
        ViewHolder viewHolder = new ViewHolder(eventView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final EventRequest request = eventRequests.get(position);

        holder.requestSenderTextView.setText(request.getUser().getName());
        holder.descriptionTextView.setText(request.getEvent().getDescription());
        holder.pointsTextView.setText("" + request.getEvent().getPoints());
        if (request.getUser().getRating() == null)
            holder.ratingCustomRatingBar.setScore(0f);
        else holder.ratingCustomRatingBar.setScore(request.getUser().getRating().floatValue());
        holder.categoryImageView.setImageDrawable(context.getResources().getDrawable(CategoryHelper.getCategoryIcon(request.getEvent().getCategory())));

        Calendar eventLatestStartDate = ActivityHelper.stringToDateTime(request.getEvent().getLatestStartDate());
        String[] arr = TimeHelper.setEventExpirationDate(context, eventLatestStartDate, true);
        holder.timeLeftTextView.setText(String.format(context.getResources().getString(R.string.timeVar_dayVar_newLine), arr[0], arr[1]));

        Calendar eventRequestDate = ActivityHelper.stringToDateTime(request.getRequestDate());
        String[] arr2 = TimeHelper.setEventExpirationDate(context, eventRequestDate, true);
        holder.sendDateTextView.setText(String.format(context.getResources().getString(R.string.timeVar_dayVar), arr2[0], arr2[1]));
        final int color;
        if (TimeHelper.isEventNow(eventLatestStartDate))
            color = getContext().getResources().getColor(R.color.marker_background_now);
        else
            color = getContext().getResources().getColor(R.color.marker_background_later);
        holder.view1.setBackgroundTintList(ColorStateList.valueOf(color));
        holder.view2.setBackgroundColor(color);


        holder.acceptRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventRequestAccept requestAccept = new EventRequestAccept(request.getId());
                Log.d("dev", Integer.toString(request.getId()));

                BoonApiInterface apiService = RetrofitBuilder.returnService();
                String accessToken = CommonOperations.getAccessToken(context);
                Call<JSONResponse> call = apiService.acceptEventRequest(accessToken, requestAccept);
                Log.i("dev", call.request().toString());
                call.enqueue(new Callback<JSONResponse>() {
                    @Override
                    public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                        if (response.body() instanceof JSONResponse) {
                            if(response.body().isSuccess()) {
                                ActivityHelper.DisplayCustomToast(context, context.getResources().getString(R.string.success));
                            } else {
                                ActivityHelper.DisplayCustomToast(context, response.body().getError().getMessage());
                            }

                        } else {
                            ActivityHelper.DisplayGeneralErrorToast(context);
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONResponse> call, Throwable t) {
                        ActivityHelper.DisplayGeneralErrorToast(context);
                    }
                });
            }
        });



/*
        public View view1, view2;
        public Button acceptRequestButton;

 */
    }

    @Override
    public int getItemCount() {
        return eventRequests.size();
    }


}
