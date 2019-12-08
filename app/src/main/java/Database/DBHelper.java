package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Users1.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + UserManager.Users.TABLE_NAME + " (" +
                UserManager.Users._ID + " INTEGER PRIMARY KEY," +
                UserManager.Users.COLUMN_NAME_USERNAME + " TEXT," +
                UserManager.Users.COLUMN_NAME_PASSWORD + " TEXT)";

        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //
    }

    public void add(String un, String pw) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserManager.Users.COLUMN_NAME_USERNAME, un);
        values.put(UserManager.Users.COLUMN_NAME_PASSWORD, pw);

        long newRowId = db.insert(UserManager.Users.TABLE_NAME, null, values);

        //System.out.println(newRowId);
        //Log.d("a", "" + newRowId);
    }

    public void update(String un, String pw) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserManager.Users.COLUMN_NAME_PASSWORD, pw);

        String selection = UserManager.Users.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = {un};

        int count = db.update(
                UserManager.Users.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public void delete(String un) {
        SQLiteDatabase db = getReadableDatabase();

        String selection = UserManager.Users.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = {un};

        int deletedRows = db.delete(UserManager.Users.TABLE_NAME, selection, selectionArgs);
    }

    public Cursor selectall() {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                UserManager.Users.COLUMN_NAME_USERNAME,
                UserManager.Users.COLUMN_NAME_PASSWORD
        };

        Cursor cursor = db.query(
                UserManager.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        return cursor;
    }

    public boolean signin(String un, String pw) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                UserManager.Users.COLUMN_NAME_USERNAME,
                UserManager.Users.COLUMN_NAME_PASSWORD
        };

        Cursor cursor = db.query(
                UserManager.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList u = new ArrayList();
        ArrayList p = new ArrayList();

        while (cursor.moveToNext()) {
            u.add(cursor.getString(1));
            p.add(cursor.getString(2));
        }

        for (int i = 0; i < u.size(); i++) {
            if (u.get(i).equals(un) && p.get(i).equals(pw)) {
                return true;
            }
        }

        return false;
    }
}
