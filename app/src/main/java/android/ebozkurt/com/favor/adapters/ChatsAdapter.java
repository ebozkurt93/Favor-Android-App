package android.ebozkurt.com.favor.adapters;

import android.content.Context;
import android.ebozkurt.com.favor.R;
import android.ebozkurt.com.favor.domain.Event;
import android.ebozkurt.com.favor.domain.User;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.CategoryHelper;
import android.ebozkurt.com.favor.helpers.CommonOperations;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by erdem on 8.01.2018.
 */

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView profilePicCircleImageView;
        public TextView nameTextView;
        public TextView descriptionTextView;
        public ImageView categoryImageView;
        public TextView lastMessageTimeTextView;

        public ConstraintLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            profilePicCircleImageView = (CircleImageView) itemView.findViewById(R.id.chat_details_profilepic_circleimageview);
            nameTextView = (TextView) itemView.findViewById(R.id.chat_details_name_textview);
            descriptionTextView = (TextView) itemView.findViewById(R.id.chat_details_event_description_textview);
            categoryImageView = (ImageView) itemView.findViewById(R.id.chat_details_category_icon_imageview);
            lastMessageTimeTextView = (TextView) itemView.findViewById(R.id.chat_details_last_message_time_textview);
            layout = (ConstraintLayout) itemView.findViewById(R.id.chat_details_constraintlayout);
        }
    }

    private List<Event> chats;
    private Context context;

    public ChatsAdapter(List<Event> chats, Context context) {
        this.chats = chats;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.chat_details, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Context context = getContext();
        Event event = chats.get(position);
        User user = CommonOperations.getUserInfo(context);
        boolean isUserCreator = event.getCreator().getId().equals(user.getId());
        holder.nameTextView.setText(isUserCreator ? event.getHelper().getName() : event.getCreator().getName());
        holder.descriptionTextView.setText(event.getDescription());
        //holder.profilePicCircleImageView
        holder.categoryImageView.setImageResource(CategoryHelper.getCategoryIcon(event.getCategory()));
        //holder.lastMessageTimeTextView

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHelper.DisplayCustomToast(context, "Test");
            }
        });

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

}
