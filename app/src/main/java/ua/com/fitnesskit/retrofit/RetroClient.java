package ua.com.fitnesskit.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit getRetrofitInstance() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://sample.fitnesskit-admin.ru/")
                .addConverterFactory(GsonConverterFactory.create(gson));

        return builder.client(httpClient.build()).build();
    }

    public static MyInterface getApiService() {
        return getRetrofitInstance().create(MyInterface.class);
    }
}
