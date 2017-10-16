package android.ebozkurt.com.favor.domain.helpers;

import android.ebozkurt.com.favor.domain.Event;

/**
 * Created by erdem on 8.10.2017.
 */

public class EventCreate {

    private Event event;
    private boolean isNow;

    public EventCreate() {
    }

    public EventCreate(Event event, boolean isNow) {
        this.event = event;
        this.isNow = isNow;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean isNow() {
        return isNow;
    }

    public void setNow(boolean now) {
        isNow = now;
    }

    @Override
    public String toString() {
        return "EventCreate{" +
                "event=" + event +
                ", isNow=" + isNow +
                '}';
    }
}
