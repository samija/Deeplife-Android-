package Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ab on 4/7/2015.
 */
public class UsersDBOpenHelper extends SQLiteOpenHelper{
    private static final String LOGTAG = "EXPLOREUSER";

    private static final String DTATBASE_NAME="user.db";
    private static final int DATABASE_VERSION = 1;

//ma Table one
    public static final String TABLE_USER = "user";
    public static final String COLUMN_ID ="userId";
    public static final String COLUMN_FNAME = "fname";
    public static final String COLUMN_LNAME = "lname";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "image";
    public static final String COLUMN_IMAGE = "image";
    //ma Table two
    public static final String TABLE_USERMEASURMENT = "usermeasurment";
    public static final String COLUMN_IDMEASURMENT ="useridmeasurment";
    public static final String COLUMN_METHOD = "method";
    public static final String COLUMN_NUMBEROF = "numberof";
    public static final String COLUMN_IDCHEAT = "idcheat";
   // public static final String COLUMN_TIME = "time";



//**** create ma table one
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_USER + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FNAME + " TEXT, " +
            COLUMN_LNAME + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_PHONE + " NUMERIC " +
            COLUMN_IMAGE + " TEXT " + ")";


    //**** create ma table two
    public static final String TABLE_CREATEUSERMEASURMENT = "CREATE TABLE " + TABLE_USERMEASURMENT + " (" +
            COLUMN_IDMEASURMENT + " INTEGER, " +
            COLUMN_METHOD + " TEXT, " +
           COLUMN_IDCHEAT + " TEXT, " +
        //  COLUMN_TIME + " TIMESTAMP, " +
            COLUMN_NUMBEROF + " TEXT " + ")";


    public UsersDBOpenHelper(Context context) {
        super(context, DTATBASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        Log.i(LOGTAG, "TABLE Has been created");
        db.execSQL(TABLE_CREATEUSERMEASURMENT);
        Log.i(LOGTAG, "TABLE Has been created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }






}
