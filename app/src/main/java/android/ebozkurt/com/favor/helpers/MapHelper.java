package android.ebozkurt.com.favor.helpers;

import android.app.Activity;
import android.content.Context;
import android.ebozkurt.com.favor.R;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/**
 * Created by erdem on 15.07.2017.
 */

public class MapHelper {

    public static Bitmap loadBitmapFromView(View v) {

        if (v.getMeasuredHeight() <= 0) {
            v.measure(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
            v.draw(c);
            return b;
        }
        return null;
    }

    public static BitmapDescriptor getMapIcon(Activity activity, String categoryId) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.default_marker, null);
        ImageView icon = (ImageView) v.findViewById(R.id.default_map_marker_icon);

        switch (categoryId) {
            case "ride":
                icon.setImageResource(R.drawable.ride);
                break;
            case "delivery":
                icon.setImageResource(R.drawable.delivery);
                break;
            case "teach":
                icon.setImageResource(R.drawable.teach);
                break;
            case "borrow":
                icon.setImageResource(R.drawable.borrow);
                break;
            case "socialize":
                //icon.setImageResource(R.drawable.socialize);
                break;
            default:
                break;
        }


        return BitmapDescriptorFactory.fromBitmap(MapHelper.loadBitmapFromView(v));
    }

    public static void setMapSettings(GoogleMap mMap, Activity activity, boolean scrollable) {
        mMap.setInfoWindowAdapter(new MyMapInfoWindowAdapter(activity));
        mMap.getUiSettings().setMapToolbarEnabled(false);
        //mMap.setMyLocationEnabled(true);

        //myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setCompassEnabled(true);

        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(scrollable);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.setMinZoomPreference(15f);
        mMap.setMaxZoomPreference(35f);
        //or myMap.getUiSettings().setAllGesturesEnabled(true);
    }
}
