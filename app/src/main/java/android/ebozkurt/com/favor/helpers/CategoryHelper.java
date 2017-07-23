package android.ebozkurt.com.favor.helpers;

import android.ebozkurt.com.favor.R;


/**
 * Created by erdem on 22.07.2017.
 */

public class CategoryHelper {

    public static int getCategoryIcon(String categoryId) {
        int iconId;
        switch (categoryId) {
            case "ride":
                iconId = R.drawable.ride;
                break;
            case "delivery":
                iconId = R.drawable.delivery;
                break;
            case "teach":
                iconId = R.drawable.teach;
                break;
            case "borrow":
                iconId = R.drawable.borrow;
                break;
            case "socialize":
                iconId = R.color.invisible;
                break;
            default:
                iconId = R.color.invisible;
                break;
        }
        return iconId;

    }
}
