package com.example.map_chat_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userdb";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_PASSWORD = "password";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_PHONE + " TEXT PRIMARY KEY, " + COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public void addUser(UserSQLite user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHONE, user.getPhoneNumber());
        values.put(COLUMN_PASSWORD, user.getPassword());

        // Nếu đã tồn tại user thì không thêm vào db
        UserSQLite existingUser = getUser(user.getPhoneNumber());
        if (existingUser == null) {
            db.insert(TABLE_NAME, null, values);
        }
        db.close();
    }

    public UserSQLite getUser(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_PHONE, COLUMN_PASSWORD}, COLUMN_PHONE + "=?", new String[]{phoneNumber}, null, null, null);
        UserSQLite user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new UserSQLite(cursor.getString(0), cursor.getString(1));
        }
        if (cursor != null) {
            cursor.close();
        }
        return user;
    }

    public void deleteUser(String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_PHONE + "=?", new String[]{phoneNumber});
        db.close();
    }
}
