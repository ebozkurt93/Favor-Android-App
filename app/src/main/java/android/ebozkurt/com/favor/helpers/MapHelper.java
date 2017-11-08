package android.ebozkurt.com.favor.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.ebozkurt.com.favor.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;


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

    public static BitmapDescriptor getMapIcon(Activity activity, String categoryId, String mapMarkerState) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.default_marker, null); //todo
        switch (mapMarkerState) {
            case "NOW":
                v.setBackgroundTintList(activity.getResources().getColorStateList(R.color.marker_background_now));
                break;
            case "LATER":
                v.setBackgroundTintList(activity.getResources().getColorStateList(R.color.marker_background_later));
                break;
            case "SELECTED":
                v.setBackgroundTintList(activity.getResources().getColorStateList(R.color.marker_background_selected));
                break;
            default:
                v.setBackgroundTintList(activity.getResources().getColorStateList(R.color.marker_icon_default));
                break;
        }

        ImageView icon = (ImageView) v.findViewById(R.id.default_map_marker_icon);
        int categoryIcon = CategoryHelper.getCategoryIcon(categoryId);
        icon.setImageResource(categoryIcon);
        return BitmapDescriptorFactory.fromBitmap(MapHelper.loadBitmapFromView(v));
    }

    public static BitmapDescriptor getDefaultMapIcon(Activity activity) {

        return BitmapDescriptorFactory.fromBitmap(BitmapHelper.getBitmapFromVectorDrawable(activity.getResources().getDrawable(R.drawable.map_current_position_marker)));
    }


    public static void setMapSettings(GoogleMap map, Activity activity, boolean scrollable, boolean zoomable) {
/*
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(activity.getApplicationContext(), R.raw.map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
*/
        map.setInfoWindowAdapter(new MyMapInfoWindowAdapter(activity));
        map.getUiSettings().setMapToolbarEnabled(false);
        //map.setMyLocationEnabled(true);

        //myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setCompassEnabled(true);

        map.getUiSettings().setRotateGesturesEnabled(true);
        map.getUiSettings().setScrollGesturesEnabled(scrollable);
        map.getUiSettings().setTiltGesturesEnabled(true);
        map.getUiSettings().setZoomGesturesEnabled(zoomable);
        map.setMinZoomPreference(15f);
        map.setMaxZoomPreference(35f);
        //or myMap.getUiSettings().setAllGesturesEnabled(true);
    }

    public static String getAddress(Context context, LatLng location) {
        String addressText = "";

        try {
            Geocoder geo = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(location.latitude, location.longitude, 1);
            if (addresses.isEmpty()) {
                addressText = "";
            } else {
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    addressText = address.getAddressLine(0);
                    //addressText = address.getFeatureName() + ", " + address.getPostalCode();

                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }
        return addressText;
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * <p>
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     *
     * @returns Distance in Meters
     */
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2/*, double el1, double el2*/) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        //double height = el1 - el2;

        distance = Math.pow(distance, 2);// + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
