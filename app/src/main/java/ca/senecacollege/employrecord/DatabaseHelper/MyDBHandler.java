package ca.senecacollege.employrecord.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;


public class MyDBHandler extends SQLiteOpenHelper {

// Logcat tag -- not sure if we need this...but will leave it just in case
// private static final String LOG = "DatabaseHelper";

    // Database info
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "employRecord.db";

    // Table User and column names
    private static final String TABLE_USER = "user";
    private static final String COL_USER_ID = "user_id";
    private static final String COL_EMAIL = "email";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";
    private static final String COL_FIRSTNAME = "firstname";
    private static final String COL_LASTNAME = "lastname";

    // Table Colour and column names
    private static final String TABLE_COLOUR = "colour";
    private static final String COL_COLOUR_ID = "colour_id";
    private static final String COL_NAME = "name";
    private static final String COL_HEXCODE = "hexcode";

    // Table Status and column names
    private static final String TABLE_STATUS = "status";
    private static final String COL_STATUS_ID = "status_id";
    private static final String COL_STATUS_NAME = "status_name";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_COLOUR);
        db.execSQL(CREATE_TABLE_STATUS);
       // db.execSQL(CREATE_TABLE_JOB);
       // db.execSQL(CREATE_TABLE_ADDRESS);
       // db.execSQL(CREATE_TABLE_USER_JOB);
       // db.execSQL(CREATE_TABLE_NOTIFICATION);
       // db.execSQL(CREATE_TABLE_JOB_NOTIFICATION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}

    // begin user crud operation
    public String loadUserHandler() {
        String result = "";
        String query = "Select*FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_0) + " " + result_1 +
                    System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;

    }
    public void addUserHandler(User user) {
        ContentValues values = new ContentValues();
        values.put(COL_USER_ID, user.getID());
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_USERNAME, user.getUsername());
        values.put(COL_PASSWORD, user.getPassword());
        values.put(COL_FIRSTNAME, user.getFirstName());
        values.put(COL_LASTNAME, user.getLastName());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public User findUserHandler(String username) {
        String query = "Select * FROM " + TABLE_USER + "WHERE" + COL_USERNAME + " = " + "'" + username+ "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        User user= new User();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            user.setId(Integer.parseInt(cursor.getString(0)));
            user.setUsername(cursor.getString(1));
            cursor.close();
        } else {
            user= null;
        }
        db.close();
        return user;
    }

    public boolean deleteUserHandler(int ID) {
        boolean result = false;
        String query = "Select*FROM " + TABLE_USER + "WHERE" + COL_USER_ID + "= '" + String.valueOf(ID) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        User user = new User();
        if (cursor.moveToFirst()) {
            user.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_USER, COL_USER_ID + "=?",
                    new String[] {
                String.valueOf(user.getID())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean updateUserHandler(int ID, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COL_USER_ID, ID);
        args.put(COL_USERNAME, name);
        return db.update(TABLE_USER, args, COL_USER_ID + "=" + ID, null) > 0;
    }

    // end user crud operation
    // begin colour crud operation
    public String loadColourHandler() {
        String result = "";
        String query = "Select*FROM " + TABLE_COLOUR;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_0) + " " + result_1 +
                    System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;

    }
    public void addColourHandler(Colour colour) {
        ContentValues values = new ContentValues();
        values.put(COL_COLOUR_ID, colour.getColourId());
        values.put(COL_NAME, colour.getName());
        values.put(COL_HEXCODE, colour.getHexcode());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_COLOUR, null, values);
        db.close();
    }



    public Colour findColourHandler(String name) {
        String query = "Select * FROM " + TABLE_COLOUR + "WHERE" + COL_NAME + " = " + "'" + name+ "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Colour colour= new Colour();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            colour.setColourId(Integer.parseInt(cursor.getString(0)));
            colour.setColourName(cursor.getString(1));
            colour.setHexcode(cursor.getString(2));
            cursor.close();
        } else {
            colour= null;
        }
        db.close();
        return colour;
    }

    public boolean deleteColourHandler(int ID) {
        boolean result = false;
        String query = "Select*FROM " + TABLE_COLOUR + "WHERE" + COL_COLOUR_ID + "= '" + String.valueOf(ID) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Colour colour = new Colour();
        if (cursor.moveToFirst()) {
            colour.setColourId(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_COLOUR, COL_COLOUR_ID + "=?",
                    new String[] {
                            String.valueOf(colour.getColourId())
                    });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean updateColourHandler(int ID, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COL_COLOUR_ID, ID);
        args.put(COL_NAME, name);
        return db.update(TABLE_COLOUR, args, COL_COLOUR_ID + "=" + ID, null) > 0;
    }

    // end colour crud operation
    // begin status crud operation

    public String loadStatusHandler() {
        String result = "";
        String query = "Select*FROM " + TABLE_STATUS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_0) + " " + result_1 +
                    System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;

    }
    public void addStatusHandler(Status status) {
        ContentValues values = new ContentValues();
        values.put(COL_STATUS_ID, status.getStatusId());
        values.put(COL_STATUS_NAME, status.getSttusName());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_STATUS, null, values);
        db.close();
    }

    public Status findStatusHandler(String statusname) {
        String query = "Select * FROM " + TABLE_STATUS + "WHERE" + COL_NAME + " = " + "'" + statusname+ "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Status status= new Status();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            status.setStatusId(Integer.parseInt(cursor.getString(0)));
            status.setStatusName(cursor.getString(1));
            cursor.close();
        } else {
            status= null;
        }
        db.close();
        return status;
    }

    public boolean deleteStatusHandler(int ID) {
        boolean result = false;
        String query = "Select*FROM " + TABLE_STATUS + "WHERE" + COL_STATUS_ID + "= '" + String.valueOf(ID) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Status status= new Status();
        if (cursor.moveToFirst()) {
            status.setStatusId(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_STATUS, COL_STATUS_ID + "=?",
                    new String[] {
                            String.valueOf(status.getStatusId())
                    });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean updateStatusHandler(int ID, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COL_STATUS_ID, ID);
        args.put(COL_STATUS_NAME, name);
        return db.update(TABLE_STATUS, args, COL_STATUS_ID + "=" + ID, null) > 0;
    }

    //end status crud operation

    // create table statments
    private static final String CREATE_TABLE_USER =
            "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
                    + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_EMAIL + " TEXT NOT NULL, "
                    + COL_USERNAME + " TEXT NOT NULL, "
                    + COL_PASSWORD + " TEXT NOT NULL, "
                    + COL_FIRSTNAME + " TEXT NOT NULL, "
                    + COL_LASTNAME + " TEXT NOT NULL)";

    private static final String CREATE_TABLE_COLOUR =
            "CREATE TABLE IF NOT EXISTS " + TABLE_COLOUR + "("
                    + COL_COLOUR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_NAME + " TEXT NOT NULL, "
                    + COL_HEXCODE + " TEXT NOT NULL)";

    private static final String CREATE_TABLE_STATUS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_STATUS + "("
            + COL_STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_STATUS_NAME + " TEXT NOT NULL)";


}