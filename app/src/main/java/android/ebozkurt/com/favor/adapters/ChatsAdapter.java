package android.ebozkurt.com.favor.adapters;

import android.content.Context;
import android.ebozkurt.com.favor.R;
import android.ebozkurt.com.favor.domain.Event;
import android.ebozkurt.com.favor.domain.User;
import android.ebozkurt.com.favor.helpers.CommonOperations;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by erdem on 8.01.2018.
 */

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder>{



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView descriptionTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.chat_details_name_textview);
            descriptionTextView = (TextView) itemView.findViewById(R.id.chat_details_event_description_textview);
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
        Context context = getContext();
        Event event = chats.get(position);
        User user = CommonOperations.getUserInfo(context);
        holder.nameTextView.setText(event.getCreator().getId().equals(user.getId()) ? event.getHelper().getName() : event.getCreator().getName());
        holder.descriptionTextView.setText(event.getDescription());

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

}
