package android.ebozkurt.com.favor.helpers;

import android.ebozkurt.com.favor.R;


/**
 * Created by erdem on 22.07.2017.
 */

public class CategoryHelper {

    public static int getCategoryIcon(String categoryId) {
        int iconId;
        switch (categoryId) {
            case "RIDE":
                iconId = R.drawable.ride;
                break;
            case "DELIVERY":
                iconId = R.drawable.delivery;
                break;
            case "TEACH":
                iconId = R.drawable.teach;
                break;
            case "BORROW":
                iconId = R.drawable.borrow;
                break;
            case "SOCIALIZE":
                iconId = R.drawable.socialize;
                break;
            default:
                iconId = R.color.invisible;
                break;
        }
        return iconId;

    }
}
