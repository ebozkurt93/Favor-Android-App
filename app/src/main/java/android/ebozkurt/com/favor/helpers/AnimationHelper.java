package android.ebozkurt.com.favor.helpers;

import android.app.Activity;
import android.ebozkurt.com.favor.R;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by erdem on 17.09.2017.
 */

public class AnimationHelper {

    public static void initializeShakeAnimation(Activity activity, View v) {
        Animation shake = AnimationUtils.loadAnimation(activity, R.anim.button_shake_animation);
        v.startAnimation(shake);
    }
}
