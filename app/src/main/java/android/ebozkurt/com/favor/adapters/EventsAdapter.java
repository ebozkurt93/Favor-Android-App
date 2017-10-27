package android.ebozkurt.com.favor.adapters;

import android.content.Context;
import android.ebozkurt.com.favor.R;
import android.ebozkurt.com.favor.domain.Event;
import android.ebozkurt.com.favor.helpers.CategoryHelper;
import android.ebozkurt.com.favor.views.CustomRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

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

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.event_detail_card_event_creator_textview);
            descriptionTextView = (TextView) itemView.findViewById(R.id.event_detail_card_event_description_textview);
            pointsTextView = (TextView) itemView.findViewById(R.id.event_detail_card_event_points_textview);
            ratingCustomRatingBar = (CustomRatingBar) itemView.findViewById(R.id.event_detail_card_event_creator_rating);
            categoryImageView = (ImageView) itemView.findViewById(R.id.event_detail_card_event_category);

        }
    }

    private List<Event> events;
    private Context context;

    public EventsAdapter(List<Event> events, Context context) {
        this.events = events;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View eventView = inflater.inflate(R.layout.event_detail_card, parent, false);

        ViewHolder viewHolder = new ViewHolder(eventView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = events.get(position);
        holder.nameTextView.setText(event.getCreator().getName());
        holder.descriptionTextView.setText(event.getDescription());
        holder.pointsTextView.setText(""+event.getPoints());
        if (event.getCreator().getRating() == null)
            holder.ratingCustomRatingBar.setScore(0f);
        else holder.ratingCustomRatingBar.setScore(event.getCreator().getRating().floatValue());
        holder.categoryImageView.setImageDrawable(context.getResources().getDrawable(CategoryHelper.getCategoryIcon(event.getCategory())));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
