package android.ebozkurt.com.favor.domain.helpers;

/**
 * Created by erdem on 17.09.2017.
 */

import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class JSONResponse<T> {

    @JsonFormat
    private boolean success;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("created_time")
    private Date createdTime;
    @JsonFormat
    @Nullable
    private T payload;
    @JsonFormat
    @Nullable
    private JSONResponseError error;

    public JSONResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    @Nullable
    public T getPayload() {
        return payload;
    }

    @Nullable
    public JSONResponseError getError() {
        return error;
    }
}