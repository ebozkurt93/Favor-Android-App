package android.ebozkurt.com.favor.domain;

/**
 * Created by erdem on 9.01.2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventRequest {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("event")
    @Expose
    private Event event;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("requestDate")
    @Expose
    private String requestDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

}