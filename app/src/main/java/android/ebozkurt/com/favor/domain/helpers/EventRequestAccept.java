package android.ebozkurt.com.favor.domain.helpers;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by erdem on 16.01.2018.
 */

public class EventRequestAccept {

    private int request_id;

    public EventRequestAccept() {
    }

    public EventRequestAccept(int requestId) {
        this.request_id = requestId;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }
}
