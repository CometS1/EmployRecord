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

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
       // db.execSQL(CREATE_TABLE_COLOUR);
       // db.execSQL(CREATE_TABLE_STATUS);
       // db.execSQL(CREATE_TABLE_JOB);
       // db.execSQL(CREATE_TABLE_ADDRESS);
       // db.execSQL(CREATE_TABLE_USER_JOB);
       // db.execSQL(CREATE_TABLE_NOTIFICATION);
       // db.execSQL(CREATE_TABLE_JOB_NOTIFICATION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}
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

    // create table statments
    private static final String CREATE_TABLE_USER =
            "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
                    + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_EMAIL + " TEXT NOT NULL, "
                    + COL_USERNAME + " TEXT NOT NULL, "
                    + COL_PASSWORD + " TEXT NOT NULL, "
                    + COL_FIRSTNAME + " TEXT NOT NULL, "
                    + COL_LASTNAME + " TEXT NOT NULL)";
}



