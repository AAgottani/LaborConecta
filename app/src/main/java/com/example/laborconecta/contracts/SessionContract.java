package com.example.laborconecta.contracts;

import android.provider.BaseColumns;

public final class UserContract {

    private UserContract() {}

    /* Inner class that defines the table contents */
    public static class User implements BaseColumns {
        public static final String TABLE_NAME = "tb_users";
        public static final String C_NAME = "name";
        public static final String C_EMAIL = "email";
        public static final String C_PASSWORD = "password";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + User.TABLE_NAME + " (" +
                    User._ID + " INTEGER PRIMARY KEY," +
                    User.C_NAME + " TEXT," +
                    User.C_EMAIL + " TEXT," +
                    User.C_PASSWORD + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + User.TABLE_NAME;
}
