package com.example.laborconecta.dbhelpers;

import static com.example.laborconecta.contracts.SessionContract.SQL_CREATE_ENTRIES;
import static com.example.laborconecta.contracts.SessionContract.SQL_DELETE_ENTRIES;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.laborconecta.contracts.SessionContract;
import com.example.laborconecta.contracts.UserContract;
import com.example.laborconecta.repositories.UserRepository;

import org.jetbrains.annotations.Nullable;

public class SessionHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Sessions.db";

    private final Context context;

    public SessionHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public @Nullable UserContract.UserModel getSession() {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                SessionContract.Session.C_UID,
        };

        String selection = SessionContract.Session.C_ACTIVE + " = 1";

        // How you want the results sorted in the resulting Cursor
        String sortOrder = BaseColumns._ID + " DESC";

        Cursor cursor = db.query(
                SessionContract.Session.TABLE_NAME, // The table to query
                projection, // The array of columns to return (pass null to get all)
                selection, // The columns for the WHERE clause
                null, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                sortOrder // The sort order
        );

        cursor.moveToFirst();

        try {

            if (cursor.getCount() == 0) {
                return null;
            }

            int uid = cursor.getInt(
                    cursor.getColumnIndexOrThrow(SessionContract.Session.C_UID)
            );

            return (new UserHelper(context)).getUser(uid);

        } finally {
            cursor.close();
        }
    }
    public void createSession(int uid) {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SessionContract.Session.C_UID, uid);
        values.put(SessionContract.Session.C_ACTIVE, true);

        // Insert the new row, returning the primary key value of the new row
        db.insert(SessionContract.Session.TABLE_NAME, null, values);

        // Atualiza o usuário
        UserRepository.getInstance(this.context).setUser(this.getSession());
    }
    public void invalidate(int uid) {
        SQLiteDatabase db = this.getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(SessionContract.Session.C_ACTIVE, "0");

        // Which row to update, based on the title
        String selection = SessionContract.Session.C_UID + " = ?";
        String[] selectionArgs = { Integer.toString(uid) };

        db.update(
                SessionContract.Session.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }
}
