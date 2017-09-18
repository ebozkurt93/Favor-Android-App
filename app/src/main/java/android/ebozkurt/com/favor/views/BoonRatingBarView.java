package android.ebozkurt.com.favor.views;

/**
 * Created by erdem on 10.09.2017.
 */

import android.content.Context;
import android.content.res.Resources;
import android.ebozkurt.com.favor.R;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.widget.TextView;

public class BoonRatingBarView extends android.support.v7.widget.AppCompatRatingBar {

    private int[] iconArrayActive = {
            R.drawable.rating_full,
            R.drawable.rating_full,
            R.drawable.rating_full,
            R.drawable.rating_full,
            R.drawable.rating_full
    };

    private int[] iconArrayInactive = {
            R.drawable.rating_empty,
            R.drawable.rating_empty,
            R.drawable.rating_empty,
            R.drawable.rating_empty,
            R.drawable.rating_empty
    };

    private int[] iconArrayHalf = {
            R.drawable.rating_half,
            R.drawable.rating_half,
            R.drawable.rating_half,
            R.drawable.rating_half,
            R.drawable.rating_half
    };

    public BoonRatingBarView(Context context) {
        super(context);
        init();
    }

    public BoonRatingBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BoonRatingBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setMax(5);
        this.setNumStars(5);
        this.setStepSize(1.0f);
        this.setRating(3.0f);
    }

    private Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
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

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        int stars = getNumStars();
        float rating = getRating();
        float x = 0;

        Bitmap bitmap;
        Resources res = getResources();
        Paint paint = new Paint();

        int W = getWidth();
        int H = getHeight();
        int icon_size = (W / stars) - 32;//21 //(H < W)?(H):(W); //72
        if (icon_size > H - 16) {
            icon_size = H - 16;
        }
        int y_pos = (H / 2) - icon_size / 2;

        int delta = ((H > W) ? (H) : (W)) / (stars);
        int offset =(W - (icon_size + (stars - 1) * delta)) / 2;
/*
        for (float i = 0f; i < stars; i = i + 1f) {

            if (rating - 1 >= i) {
                bitmap = getBitmapFromVectorDrawable(getContext(), iconArrayActive[(int) i]);
                //i = i + 0.5f;
            } else {
                if (rating - 1 >= -0.5f) {
                    bitmap = getBitmapFromVectorDrawable(getContext(), iconArrayHalf[(int) i]);
                } else {
                    bitmap = getBitmapFromVectorDrawable(getContext(), iconArrayInactive[(int) i]);
                    i = i + 0.5f;
                }
            }*/

        for (int i = 0; i < stars; i++) {
            if ((int) rating - 1 >= i) {
                bitmap = getBitmapFromVectorDrawable(getContext(), iconArrayActive[i]);
            } else {
                bitmap = getBitmapFromVectorDrawable(getContext(), iconArrayInactive[i]);
            }

            x = offset + (i * delta);
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, icon_size, icon_size, true);
            canvas.drawBitmap(scaled, x, y_pos, paint);
            canvas.save();
        }
    }


}