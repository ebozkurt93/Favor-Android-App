package android.ebozkurt.com.favor.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by erdem on 17.09.2017.
 */

public class RetrofitBuilder {

    // Trailing slash is needed
    public static final String BASE_URL = "http://10.0.2.2:8080/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .baseUrl("http://192.168.1.4:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static BoonApiInterface returnService () {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
        Retrofit retrofit = retrofitBuilder.retrofit;
        BoonApiInterface apiService =
                retrofit.create(BoonApiInterface.class);
        return apiService;
    }
}
