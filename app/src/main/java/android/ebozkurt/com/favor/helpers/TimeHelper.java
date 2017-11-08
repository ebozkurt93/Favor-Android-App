package android.ebozkurt.com.favor.helpers;

import java.util.Calendar;

/**
 * Created by erdem on 7.11.2017.
 */

public class TimeHelper {

    //event being now = 'in 1 hours', being later = 'in 24 hours'
    public static boolean isEventNow(Calendar calendar) {
        Calendar now = Calendar.getInstance();
        int difInMin = (int)(calendar.getTimeInMillis() - now.getTimeInMillis()) / 60000;
        if (difInMin >= 60)
            return false;
        else return true;
    }
}
