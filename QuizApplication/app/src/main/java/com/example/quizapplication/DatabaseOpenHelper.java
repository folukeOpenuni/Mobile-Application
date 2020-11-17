package com.example.quizapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.ContentValues;

import androidx.annotation.Nullable;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    //”student” = name of the database; 1 = version of the db;
    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "questions_db";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Answer.CREATE_TABLE);
        System.out.println("Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Answer.TABLE_NAME);

    // Create tables again
        onCreate(db);
    }

    public long insertQuestions(String question, String answer) {
    // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Answer.COLUMN_QUESTION, question);
        values.put(Answer.COLUMN_ANSWER, answer);


        // insert row
        long id = db.insert(Answer.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Answer getQuestion(int id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Answer.TABLE_NAME,
                new String[]{Answer.COLUMN_ID, Answer.COLUMN_QUESTION, Answer.COLUMN_ANSWER},
                Answer.COLUMN_ID + "=?",
                new String[]{String.valueOf(id + 1)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Answer answer = new Answer(
                cursor.getInt(cursor.getColumnIndex(Answer.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Answer.COLUMN_QUESTION)),
                cursor.getString(cursor.getColumnIndex(Answer.COLUMN_ANSWER)));

        // close the db connection
        cursor.close();

        return answer;
    }

    public int getQuestionsCount() {
        String countQuery = "SELECT * FROM " + Answer.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }




}
