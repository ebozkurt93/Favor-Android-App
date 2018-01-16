package android.ebozkurt.com.favor.helpers;

import android.content.Context;
import android.ebozkurt.com.favor.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by erdem on 7.11.2017.
 */

public class TimeHelper {

    //event being now = 'in 1 hours', being later = 'in 24 hours'
    public static boolean isEventNow(Calendar calendar) {
        Calendar now = Calendar.getInstance();
        int difInMin = (int) (calendar.getTimeInMillis() - now.getTimeInMillis()) / 60000;
        if (difInMin >= 60)
            return false;
        else return true;
    }

    public static String[] setEventExpirationDate(Context context, Calendar expirationDate, boolean expirationDateIsFromServer) {
        String day, time;

       /* SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = formatter.parse(OurDate);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy HH:mm"); //this format changeable
        dateFormatter.setTimeZone(TimeZone.getDefault());
        OurDate = dateFormatter.format(value);
        */
        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_YEAR, -1);
        //expirationDate.add(Calendar.HOUR, hoursToAdd);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        long gmtTime = expirationDate.getTime().getTime();

        if (expirationDateIsFromServer) {
            long timezoneAlteredTime = gmtTime + TimeZone.getDefault().getRawOffset();
            expirationDate.setTimeInMillis(timezoneAlteredTime);
        }
        sdf.setCalendar(expirationDate);
        time = sdf.format(expirationDate.getTime());
        if (yesterday.get(Calendar.DAY_OF_YEAR) == expirationDate.get(Calendar.DAY_OF_YEAR)) {
            day = context.getResources().getString(R.string.yesterday);
        } else if (today.get(Calendar.YEAR) == expirationDate.get(Calendar.YEAR) && today.get(Calendar.DAY_OF_YEAR) == expirationDate.get(Calendar.DAY_OF_YEAR)) {
            //today
            day = context.getResources().getString(R.string.today);
        } else {
            String[] days = context.getResources().getStringArray(R.array.days_short);
            day = days[expirationDate.get(Calendar.DAY_OF_WEEK) - 1];
        }
        String finalText = String.format(context.getResources().getString(R.string.timeVar_dayVar), time, day);
        String[] result = new String[2];
        result[0] = time;
        result[1] = day;
        return result;
    }
}
