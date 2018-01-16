package android.ebozkurt.com.favor.helpers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by erdem on 16.01.2018.
 */

public class WindowHelper {

    public static int[] getWindowSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();
        int[]arr = new int[2];
        arr[0] = width;
        arr[1] = height;
        return arr;
    }

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
