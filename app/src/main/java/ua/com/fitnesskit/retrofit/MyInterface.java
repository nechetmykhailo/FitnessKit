package ua.com.fitnesskit.retrofit;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyInterface {
    @GET("/schedule/get_group_lessons_v2/1/")
    Call<JsonArray> urokData ();
}
