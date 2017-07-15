package android.ebozkurt.com.favor.helpers;

import android.app.Activity;
import android.ebozkurt.com.favor.R;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by erdem on 15.07.2017.
 */

public class MyMapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View myContentsView;

    public MyMapInfoWindowAdapter(Activity activity){
        myContentsView = activity.getLayoutInflater().inflate(R.layout.map_marker_title, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {

        TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.map_marker_title_title));
        tvTitle.setText(marker.getTitle());

        return myContentsView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        // TODO Auto-generated method stub
        return null;
    }


}