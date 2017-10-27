package android.ebozkurt.com.favor.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by erdem on 17.09.2017.
 */

public class User {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("birthDate")
    @Expose
    private String birthDate;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("points")
    @Expose
    private Integer points;
    @SerializedName("activeEventCount")
    @Expose
    private Integer activeEventCount;
    @SerializedName("rating")
    @Expose
    private Double rating;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getActiveEventCount() {
        return activeEventCount;
    }

    public void setActiveEventCount(Integer activeEventCount) {
        this.activeEventCount = activeEventCount;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", description=" + description +
                ", birthDate='" + birthDate + '\'' +
                ", password='" + password + '\'' +
                ", points=" + points +
                ", activeEventCount=" + activeEventCount +
                ", rating=" + rating +
                '}';
    }
}
