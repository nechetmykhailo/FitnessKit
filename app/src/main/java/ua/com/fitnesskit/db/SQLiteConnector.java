package ua.com.fitnesskit.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteConnector extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private Cursor cursor;
    private Context context;

    public SQLiteConnector(Context context) {
        super(context, "FitnessKit", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("create table Urok (_id integer primary key autoincrement,"
                    + "name TEXT,"
                    + "startTime TEXT, "
                    + "endTime TEXT, "
                    + "teacher TEXT, "
                    + "teacherImg TEXT, "
                    + "place TEXT, "
                    + "description TEXT, "
                    + "weekDay INTEGER)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertUroks(String name, String startTime, String endTime, String teacher,
                            String teacherImg, String place, String description, int weekDay){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("startTime", startTime);
        values.put("endTime", endTime);
        values.put("teacher", teacher);
        values.put("teacherImg", teacherImg);
        values.put("place", place);
        values.put("description", description);
        values.put("weekDay", weekDay);

        db.insert("Urok", null, values);
        db.close();
    }
}
