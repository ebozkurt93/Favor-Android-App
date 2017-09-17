package android.ebozkurt.com.favor.domain.helpers;

import android.support.annotation.Nullable;

/**
 * Created by erdem on 17.09.2017.
 */

public class JSONResponseError {

    @Nullable
    private int error_code;
    @Nullable
    private String message;

    public JSONResponseError() {
    }

    @Nullable
    public int getError_code() {
        return error_code;
    }

    @Nullable
    public String getMessage() {
        return message;
    }
}
