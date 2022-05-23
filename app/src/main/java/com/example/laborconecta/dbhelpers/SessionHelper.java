package com.example.laborconecta.dbhelpers;

import static com.example.laborconecta.contracts.UserContract.SQL_CREATE_ENTRIES;
import static com.example.laborconecta.contracts.UserContract.SQL_DELETE_ENTRIES;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.laborconecta.contracts.UserContract;

import org.jetbrains.annotations.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class UserHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Users.db";

    public UserHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    public boolean exists(String email, @Nullable String password) {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserContract.User.C_NAME,
                UserContract.User.C_EMAIL
        };

        // Filter results WHERE "email" = email and "password" = md5(password)
        String selection = UserContract.User.C_EMAIL + " = ?";
        ArrayList<String> selectionArgs = new ArrayList<>();
        selectionArgs.add(email);

        if (password != null) {
            selection += " AND " + UserContract.User.C_PASSWORD + " = ?";
            selectionArgs.add(md5(password));
        }

        Log.d("USER_HELPER", String.join(", ", selectionArgs));

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserContract.User.C_EMAIL + " ASC";

        Cursor cursor = db.query(
                UserContract.User.TABLE_NAME, // The table to query
                projection, // The array of columns to return (pass null to get all)
                selection, // The columns for the WHERE clause
                selectionArgs.toArray(new String[0]), // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                sortOrder // The sort order
        );

        cursor.moveToFirst();

        int count = cursor.getCount();
        cursor.close();

        return count == 1;
    }

    public boolean doLogin(String email, String password) {
        return exists(email, password);
    }

    public boolean doRegister(String name, String email, String password) {
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserContract.User.C_NAME, name);
        values.put(UserContract.User.C_EMAIL, email);
        values.put(UserContract.User.C_PASSWORD, md5(password));

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UserContract.User.TABLE_NAME, null, values);

        return newRowId > 0;
    }

    private String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                StringBuilder h = new StringBuilder(Integer.toHexString(0xFF & aMessageDigest));
                while (h.length() < 2)
                    h.insert(0, "0");
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
