package android.ebozkurt.com.favor.helpers;

import android.app.Activity;
import android.content.Context;
import android.ebozkurt.com.favor.R;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

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
        View v;

        switch (categoryId) {
            case "ride":
                v = inflater.inflate(R.layout.ride_marker, null);
                break;
            case "delivery":
                v = inflater.inflate(R.layout.delivery_marker, null);
                break;
            case "teach":
                v = inflater.inflate(R.layout.teach_marker, null);
                break;
            case "borrow":
                v = inflater.inflate(R.layout.borrow_marker, null);
                break;
            case "socialize":
                v = inflater.inflate(R.layout.socialize_marker, null);
                break;
            default:
                v = inflater.inflate(R.layout.default_marker, null);
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
