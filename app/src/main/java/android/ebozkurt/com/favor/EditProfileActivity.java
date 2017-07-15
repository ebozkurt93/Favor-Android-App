package android.ebozkurt.com.favor;

import android.content.Context;
import android.content.Intent;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.DividerItemDecoration;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {

    //actionbar components
    TextView title;
    ImageView back;
    TextView done;

    String name, lastname, personalDescription, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ActivityHelper.initialize(this);

        RecyclerView settingsRecyclerView = (RecyclerView) findViewById(R.id.activity_edit_profile_recyclerview);
        title = (TextView) findViewById(R.id.sign_up1_action_bar_middle_text_view);
        title.setText(R.string.edit_profile);
        back = (ImageView) findViewById(R.id.sign_up1_action_bar_image_button);
        back.setVisibility(View.INVISIBLE);
        done = (TextView) findViewById(R.id.sign_up1_action_bar_right_text_view);
        done.setText(R.string.done);
        done.setVisibility(View.VISIBLE);

        //todo get profile picture, name, lastname, description, email address at here, set them to list items
        name = "ExampleName";
        lastname = "ExampleLastname";
        personalDescription = "This is an example personal descriptionEditText, try changing it.";
        email = "example@boon-app.com";


        EditProfileActivity.Settings list_profile_pic = new EditProfileActivity.Settings(getResources().getDrawable(R.drawable.profile), getResources().getString(R.string.profile_picture), "profile_pic");
        EditProfileActivity.Settings list_name_lastname = new EditProfileActivity.Settings(getResources().getDrawable(R.drawable.profile), name + " " + lastname, "name_lastname");
        EditProfileActivity.Settings list_description = new EditProfileActivity.Settings(getResources().getDrawable(R.drawable.description), personalDescription, "description");
        EditProfileActivity.Settings list_email = new EditProfileActivity.Settings(getResources().getDrawable(R.drawable.mail), email, "email");
        EditProfileActivity.Settings list_password = new EditProfileActivity.Settings(getResources().getDrawable(R.drawable.password), getResources().getString(R.string.password), "password");
        ArrayList<EditProfileActivity.Settings> settingsList = new ArrayList<>();
        settingsList.add(list_profile_pic);
        settingsList.add(list_name_lastname);
        settingsList.add(list_description);
        settingsList.add(list_email);
        settingsList.add(list_password);
        EditProfileActivity.SettingsAdapter adapter = new EditProfileActivity.SettingsAdapter(this, settingsList);
        settingsRecyclerView.setAdapter(adapter);
        settingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        settingsRecyclerView.addItemDecoration(new DividerItemDecoration(EditProfileActivity.this.getDrawable(R.drawable.category_divider),
                false, false));


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public class Settings {
        Drawable icon;
        String name;
        String category_id;

        public Settings() {
        }

        public Settings(Drawable icon, String name, String settings_id) {
            this.icon = icon;
            this.name = name;
            this.category_id = settings_id;
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSettings_id() {
            return category_id;
        }

        public void setSettings_id(String category_id) {
            this.category_id = category_id;
        }
    }


    public class SettingsAdapter extends RecyclerView.Adapter<EditProfileActivity.SettingsAdapter.ViewHolder> {

        // Provide a direct reference to each of the views within a data item
        // Used to cache the views within the item layout for fast access
        public class ViewHolder extends RecyclerView.ViewHolder {
            // Your holder should contain a member variable
            // for any view that will be set as you render a row
            public ImageView icon;
            public TextView name;

            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public ViewHolder(final View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);

                icon = (ImageView) itemView.findViewById(R.id.edit_profile_item_icon_imageview);
                name = (TextView) itemView.findViewById(R.id.edit_profile_item_text_textview);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Class c;
                        //todo add name etc as extra here and send with intent
                        switch (mSettings.get(getAdapterPosition()).getSettings_id()) {
                            //  case "profile_pic": c = EditProfileActivity.class;
                            case "name_lastname":
                                c = EditProfileNameLastnameActivity.class;
                                break;
                            case "description":
                                c = EditProfileDescriptionActivity.class;
                                break;
                            case "email":
                                c = EditProfileEmailActivity.class;
                                break;
                            case "password":
                                c = EditProfilePasswordActivity.class;
                                break;
                            default:
                                c = EditProfileNameLastnameActivity.class;
                                break;

                        }
                        Intent i = new Intent(EditProfileActivity.this, c);
                        //i.putExtra("category_id", mSettings.get(getAdapterPosition()).getSettings_id());
                        //Log.i("dev", mCategories.get(getAdapterPosition()).getSettings_id());
                        startActivity(i);
                    }
                });

            }

        }

        // Store a member variable for the contacts
        private List<EditProfileActivity.Settings> mSettings;
        // Store the context for easy access
        private Context mContext;

        // Pass in the contact array into the constructor
        public SettingsAdapter(Context context, List<EditProfileActivity.Settings> settings) {
            mSettings = settings;
            mContext = context;
        }

        // Easy access to the context object in the recyclerview
        private Context getContext() {
            return mContext;
        }

        @Override
        public EditProfileActivity.SettingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.edit_profile_item, parent, false);

            // Return a new holder instance
            EditProfileActivity.SettingsAdapter.ViewHolder viewHolder = new EditProfileActivity.SettingsAdapter.ViewHolder(contactView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(EditProfileActivity.SettingsAdapter.ViewHolder viewHolder, int position) {
            EditProfileActivity.Settings setting = mSettings.get(position);

            // Set item views based on your views and data model
            TextView name = viewHolder.name;
            name.setText(setting.getName());
            ImageView icon = viewHolder.icon;
            icon.setImageDrawable(setting.getIcon());
        }

        @Override
        public int getItemCount() {
            return mSettings.size();
        }

    }
}
