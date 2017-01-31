package com.example.ulan.znakomstvakarta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Админ on 24.01.2017.
 */

public class DataHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mydatabase.db";

    public static final String TABLE_USER="User";
    public static final String USER_EMAIl_COLUMN="user_email";
    public static final String USER_NAME_COLUMN="user_name";
    public static final String USER_SURNAME_COLUMN="user_surname";
    public static final String USER_USERNAME_COLUMN="user_username";
    public static final String USER_ID_COLUMN="user_id";
    public static final String USER_PASSWORD_COLUMN="user_password";
    public static final String USER_PHONE_NUMBER_COLUMN="user_phone_number";
    public static final String USER_DESCRIPTION_COLUMN="user_description";





    public static final String TABLE_DATE ="Date";
    public static final String DATE_D_COLUMN="date_d";




    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_USER + "(" +
                USER_DESCRIPTION_COLUMN + " text," +
                USER_EMAIl_COLUMN + " text," +
                USER_NAME_COLUMN + " text," +
                USER_PASSWORD_COLUMN + " text," +
                USER_PHONE_NUMBER_COLUMN + " text," +
                USER_SURNAME_COLUMN + " text," +
                USER_USERNAME_COLUMN + " text," +
                USER_ID_COLUMN + " integer);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public  void addUser(User user){
        ContentValues values = new ContentValues();

        values.put(USER_DESCRIPTION_COLUMN, user.getDescription());
        values.put(USER_EMAIl_COLUMN, user.getEmail());
        values.put(USER_ID_COLUMN, user.getId());
        values.put(USER_NAME_COLUMN, user.getName());
        values.put(USER_PASSWORD_COLUMN, user.getPassword());
        values.put(USER_PHONE_NUMBER_COLUMN, user.getPhone());
        values.put(USER_SURNAME_COLUMN, user.getSurname());
        values.put(USER_USERNAME_COLUMN, user.getUsername());


        getWritableDatabase().insert(TABLE_USER, null, values);
    }

    public Cursor getDataUser() {
        return getReadableDatabase().query(TABLE_USER,
                null, null, null,
                null, null, null);
    }


    public void deleteUser() {
        getWritableDatabase().delete(TABLE_USER, null, null);
    }



}
