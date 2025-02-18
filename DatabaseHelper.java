package com.example.notebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOTE = "note";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // ✅ Notes Table তৈরি করা
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOTE + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // ✅ নতুন Note সংরক্ষণ
    public boolean insertNote(String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NOTE, note);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    // ✅ সমস্ত Note লোড করা
    public List<String> getAllNotes() {
        List<String> notesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                notesList.add(cursor.getString(1));  // Column Index 1 = Note Content
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notesList;
    }

    // ✅ নির্দিষ্ট Note মুছতে হলে
    public void deleteNote(String noteContent) {
        SQLiteDatabase db = this.getWritableDatabase();

        // ✅ Debugging Log
        Log.d("DELETE_NOTE", "Deleting note: " + noteContent);

        db.delete(TABLE_NAME, COLUMN_NOTE + "=?", new String[]{noteContent});
        db.close();
    }
}
