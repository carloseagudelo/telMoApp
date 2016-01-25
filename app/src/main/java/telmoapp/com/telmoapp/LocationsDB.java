package telmoapp.com.telmoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocationsDB extends SQLiteOpenHelper{

    /** Database name */
    private static String DBNAME = "locationmarkersqlite";

    private static int VERSION = 1;

    public static final String FIELD_ROW_ID = "_id";
    public static final String FIELD_LAT = "lat";
    public static final String FIELD_LNG = "lng";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_LOGO= "logo";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_TYPE_ID = "type_id";
    public static final String FIELD_ADDRES = "addres";



    /** A constant, stores the table name */
    private static final String DATABASE_TABLE = "Motels";

    /** An instance variable for SQLiteDatabase */
    private SQLiteDatabase mDB;

    /** Constructor */
    public LocationsDB(Context context) {
        super(context, DBNAME, null, VERSION);
        this.mDB = getWritableDatabase();
    }

    /** This is a callback method, invoked when the method getReadableDatabase() / getWritableDatabase() is called
     * provided the database does not exists
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =     "create table " + DATABASE_TABLE + " ( " +
                FIELD_ROW_ID + " integer primary key, " +
                FIELD_LNG + " double , " +
                FIELD_LAT + " double , " +
                FIELD_DESCRIPTION + " text , " +
                FIELD_NAME + " text , " +
                FIELD_LOGO + " text , " +
                FIELD_TYPE_ID + " text , " +
                FIELD_ADDRES + " text " +

                " ) ";
        db.execSQL(sql);
    }

    /** Inserts a new location to the table locations */
    public long insert(ContentValues contentValues){

        long rowID = mDB.insert(DATABASE_TABLE, null, contentValues);
        return rowID;
    }

    /** Deletes all locations from the table */
    public int del(){
        int cnt = mDB.delete(DATABASE_TABLE, null , null);
        return cnt;
    }

    /** Returns all the locations from the table */
    public Cursor getAllLocations(){
        return mDB.query(DATABASE_TABLE, new String[] { FIELD_ROW_ID,  FIELD_LAT , FIELD_LNG, FIELD_DESCRIPTION, FIELD_NAME, FIELD_LOGO, FIELD_TYPE_ID, FIELD_ADDRES } , null, null, null, null, null);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}