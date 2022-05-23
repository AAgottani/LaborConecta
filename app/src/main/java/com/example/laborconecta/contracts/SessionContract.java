package com.example.laborconecta.contracts;

import android.provider.BaseColumns;

public final class SessionContract {

    public SessionContract() {}

    /* Inner class that defines the table contents */
    public static class Session implements BaseColumns {
        public static final String TABLE_NAME = "tb_users";
        public static final String C_UID = "uid";
        public static final String C_ACTIVE = "active";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Session.TABLE_NAME + " (" +
                    Session._ID + " INTEGER PRIMARY KEY," +
                    Session.C_ACTIVE + " INTEGER DEFAULT 1," +
                    Session.C_UID + " INTEGER)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Session.TABLE_NAME;
}
