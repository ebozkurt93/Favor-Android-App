package android.ebozkurt.com.favor;

import android.content.Context;
import android.content.Intent;
import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.ebozkurt.com.favor.helpers.BottomNavigationViewHelper;
import android.ebozkurt.com.favor.helpers.DividerItemDecoration;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

import java.util.ArrayList;
import java.util.List;

public class CreateEvent1Activity extends AppCompatActivity {

    TextView title;
    AHBottomNavigation bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event1);
        ActivityHelper.initialize(this);


        bottomNavigationView = (AHBottomNavigation) findViewById(R.id.activity_create_event1_bottom_navigation_bar);
        BottomNavigationViewHelper.initialize(bottomNavigationView, 2);

        title = (TextView) findViewById(R.id.activity_create_event1_title_textview);
        title.setText(getResources().getString(R.string.create_event, getResources().getString(R.string.app_name)));

        RecyclerView categoriesRecyclerView = (RecyclerView) findViewById(R.id.activity_create_event1_recyclerview);

        Category ride = new Category(getResources().getDrawable(R.drawable.chat), getResources().getString(R.string.ride), "ride");
        Category delivery = new Category(getResources().getDrawable(R.drawable.home), getResources().getString(R.string.delivery), "delivery");
        Category teach = new Category(getResources().getDrawable(R.drawable.chat), getResources().getString(R.string.teach), "teach");
        Category borrow = new Category(getResources().getDrawable(R.drawable.chat), getResources().getString(R.string.borrow), "borrow");
        Category socialize = new Category(getResources().getDrawable(R.drawable.chat), getResources().getString(R.string.socialize), "socialize");
        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(ride);
        categoryList.add(delivery);
        categoryList.add(teach);
        categoryList.add(borrow);
        categoryList.add(socialize);

        CategoriesAdapter adapter = new CategoriesAdapter(this, categoryList);
        categoriesRecyclerView.setAdapter(adapter);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoriesRecyclerView.addItemDecoration(new DividerItemDecoration(CreateEvent1Activity.this.getDrawable(R.drawable.category_divider),
                false, false));
    }

    public class Category {
        Drawable icon;
        String name;
        String category_id;

        public Category() {
        }

        public Category(Drawable icon, String name, String category_id) {
            this.icon = icon;
            this.name = name;
            this.category_id = category_id;
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

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }
    }


    public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

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

                icon = (ImageView) itemView.findViewById(R.id.create_event_category_icon);
                name = (TextView) itemView.findViewById(R.id.create_event_category_textview);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(CreateEvent1Activity.this, CreateEvent2Activity.class);
                        i.putExtra("category", mCategories.get(getAdapterPosition()).getCategory_id());
                        Log.i("dev", mCategories.get(getAdapterPosition()).getCategory_id());
                        startActivity(i);
                    }
                });

            }

        }

        // Store a member variable for the contacts
        private List<Category> mCategories;
        // Store the context for easy access
        private Context mContext;

        // Pass in the contact array into the constructor
        public CategoriesAdapter(Context context, List<Category> categories) {
            mCategories = categories;
            mContext = context;
        }

        // Easy access to the context object in the recyclerview
        private Context getContext() {
            return mContext;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.create_event_category_item, parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(contactView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            Category category = mCategories.get(position);

            // Set item views based on your views and data model
            TextView name = viewHolder.name;
            name.setText(category.getName());
            ImageView icon = viewHolder.icon;
            icon.setImageDrawable(category.getIcon());
        }

        @Override
        public int getItemCount() {
            return mCategories.size();
        }

    }


}


