package android.ebozkurt.com.favor.helpers;

import android.app.Activity;
import android.content.Context;
import android.ebozkurt.com.favor.R;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.DisplayMetrics;
import android.widget.TextView;


/**
 * Created by erdem on 26.08.2017.
 */

public class BitmapHelper {

    public static Bitmap getBitmapFromVectorDrawable(Drawable drawable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static int dpToPx(Context c, int dp) {
        DisplayMetrics displayMetrics = c.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(Context c, int px) {
        DisplayMetrics displayMetrics = c.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static void currencyIconInitializer(Activity activity, TextView v) {
        Drawable userPointsCurrency = activity.getResources().getDrawable(R.drawable.currency).mutate();
        //userPointsCurrency.setTint(getResources().getColor(R.color.colorAccent));
        Bitmap bitmap = BitmapHelper.getBitmapFromVectorDrawable(userPointsCurrency);
        int size = BitmapHelper.dpToPx(activity.getApplicationContext(), 16);
        Drawable d = new BitmapDrawable(activity.getResources(), Bitmap.createScaledBitmap(bitmap, size, size, true));
        v.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
        v.setCompoundDrawablePadding(dpToPx(activity.getApplicationContext(), 2));
    }
}
