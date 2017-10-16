package android.ebozkurt.com.favor.domain;

import android.ebozkurt.com.favor.domain.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by erdem on 7.10.2017.
 */

public class Event {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("creator")
    @Expose
    private User creator;
    @SerializedName("helper")
    @Expose
    private Object helper;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("points")
    @Expose
    private Integer points;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("creationDate")
    @Expose
    private String creationDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Object getHelper() {
        return helper;
    }

    public void setHelper(Object helper) {
        this.helper = helper;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
