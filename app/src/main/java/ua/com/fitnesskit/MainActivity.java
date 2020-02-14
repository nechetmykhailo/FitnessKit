package ua.com.fitnesskit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.com.fitnesskit.adapter.RecyclerAdapterUroks;
import ua.com.fitnesskit.databinding.ActivityMainBinding;
import ua.com.fitnesskit.db.SQLiteConnector;
import ua.com.fitnesskit.model.Urok;
import ua.com.fitnesskit.retrofit.MyInterface;
import ua.com.fitnesskit.retrofit.RetroClient;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final MyInterface api = RetroClient.getApiService();
    private RecyclerAdapterUroks adapterUroks;
    private List<Urok> list;

    private SQLiteConnector sqLiteConnector;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        list = new ArrayList<>();
        sqLiteConnector = new SQLiteConnector(getApplicationContext());
        sqLiteDatabase = sqLiteConnector.getWritableDatabase();

        LinearLayoutManager mLayoutManagers = new LinearLayoutManager(getApplicationContext());
        binding.recyclerView.setLayoutManager(mLayoutManagers);



        Cursor cursor = sqLiteDatabase.query("Urok", null, null, null, null, null, null);
        if (cursor.getCount() <=0) {
            Call<JsonArray> call = api.urokData();
            call.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    JsonArray jsonArray = response.body();

                    for (JsonElement element : jsonArray) {
                        JsonObject jsonObject = element.getAsJsonObject();
                        Urok urok = new Urok();

                        urok.setName(jsonObject.get("name").getAsString());
                        urok.setPlace(jsonObject.get("place").getAsString());
                        urok.setWeekDay(jsonObject.get("weekDay").getAsInt());
                        urok.setDescription(jsonObject.get("description").getAsString());
                        urok.setTeacher(jsonObject.get("teacher").getAsString());
                        urok.setStartTime(jsonObject.get("startTime").getAsString());
                        urok.setEndTime(jsonObject.get("endTime").getAsString());
                        urok.setTeacherImg(jsonObject.get("teacher_v2").getAsJsonObject().get("imageUrl").getAsString());
                        list.add(urok);


                        sqLiteDatabase = sqLiteConnector.getWritableDatabase();
                        sqLiteConnector.insertUroks(urok.getName(), urok.getStartTime(), urok.getEndTime(),
                                urok.getTeacher(), urok.getTeacherImg(), urok.getPlace(),
                                urok.getDescription(), urok.getWeekDay());

                        Collections.sort(list, (o1, o2) -> {
                                int rPrice = (int) Double.parseDouble(String.valueOf(o1.getWeekDay()));
                                int lPrice = (int) Double.parseDouble(String.valueOf(o2.getWeekDay()));
                                return rPrice > lPrice ?
                                        1 : rPrice < lPrice ? -1 : 0;
                        });

                        adapterUroks = new RecyclerAdapterUroks(getApplicationContext(), list);
                        binding.recyclerView.setAdapter(adapterUroks);
                    }

                    adapterUroks.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                }
            });
        } else {
            getDataFromDB();
        }
    }

    private void getDataFromDB() {
        Cursor cursor = sqLiteConnector.getReadableDatabase().query("Urok"
                , new String[]{"name", "startTime", "endTime",
                        "teacher", "teacherImg", "place", "description", "weekDay"}
                , null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Urok urok = new Urok();

                urok.setName(cursor.getString(0));
                urok.setStartTime(cursor.getString(1));
                urok.setEndTime(cursor.getString(2));
                urok.setTeacher(cursor.getString(3));
                urok.setTeacherImg(cursor.getString(4));
                urok.setPlace(cursor.getString(5));
                urok.setDescription(cursor.getString(6));
                urok.setWeekDay(cursor.getInt(7));
                list.add(urok);

                Collections.sort(list, (o1, o2) -> {
                    int rPrice = (int) Double.parseDouble(String.valueOf(o1.getWeekDay()));
                    int lPrice = (int) Double.parseDouble(String.valueOf(o2.getWeekDay()));
                    return rPrice > lPrice ?
                            1 : rPrice < lPrice ? -1 : 0;
                });

                adapterUroks = new RecyclerAdapterUroks(getApplicationContext(), list);
                binding.recyclerView.setAdapter(adapterUroks);
            } while (cursor.moveToNext());
        }
    }
}
