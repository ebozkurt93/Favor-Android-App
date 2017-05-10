package android.ebozkurt.com.favor;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class InitialViewPager1Fragment extends Fragment {
    // Store instance variables
    private String title;
    private String description;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static InitialViewPager1Fragment newInstance(int page, String title, String description) {
        InitialViewPager1Fragment fragmentFirst = new InitialViewPager1Fragment();
        Bundle args = new Bundle();
        //args.putInt("PageNum", page);
        args.putString("Title", title);
        args.putString("Description", description);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //page = getArguments().getInt("PageNum", 0);
        title = getArguments().getString("Title");
        description = getArguments().getString("Description");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_initial_view_pager1, container, false);
        TextView titleTextView = (TextView) view.findViewById(R.id.fragment_initial_view_pager1_title);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.fragment_initial_view_pager1_description);
        titleTextView.setText(title);
        descriptionTextView.setText(description);
        return view;
    }
}