package com.IsmailUsman_i180516_i180634;

import android.net.Uri;
import android.provider.BaseColumns;

public class MyContactContract {

    public String DB_NAME ="myContacts.db";
    public static int DB_VERSION = 1;

    public static class Contact implements BaseColumns{
        public static String TABLENAME = "contactsTable";
        public static String _FIRSTNAME = "firstName";
        public static String _LASTNAME = "lastName";
        public static String _GENDER = "gender";
        public static String _BIO = "bio";
        public Uri _PROFILEUri;
    }
}
