package Database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ab on 4/7/2015.
 */
public class UsersDBOpenHelper extends SQLiteOpenHelper{
    private static final String LOGTAGA = "EXPLOREUSERB";

    private static final String DTATBASE_NAME="user.db";
    private static final int DATABASE_VERSION = 3;

//ma Table one
    public static final String TABLE_USER = "user";
    public static final String COLUMN_ID ="userId";
    public static final String COLUMN_FNAME = "fname";
    public static final String COLUMN_LNAME = "lname";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "image";
    public static final String COLUMN_IMAGE = "price";


    //ma Table login
    public static final String TABLE_USERLOGIN = "userlogin";
    public static final String COLUMN_IDL ="useridlogin";
    public static final String COLUMN_FNAMEL = "fname";
    public static final String COLUMN_LNAMEL = "lname";
    public static final String COLUMN_PHONEL = "phone";
    public static final String COLUMN_EMAILL = "image";
    public static final String COLUMN_IMAGEL = "price";



    //ma Table two
    public static final String TABLE_USERMEASURMENT = "usermeasurment";
    public static final String COLUMN_IDMEASURMENT ="useridmeasurment";
    public static final String COLUMN_METHOD = "method";
    public static final String COLUMN_NUMBEROF = "numberof";
    public static final String COLUMN_IDCHEAT = "idcheat";
   // public static final String COLUMN_TIME = "time";

//MA TABLE THREE
    public static final String TABLE_SCHEDULE = "schedule";
    public static final String COLUMN_SID = "useridschedule";
    public static final String COLUMN_LABLE = "lable";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_HOUR = "hour";
    public static final String COLUMN_MINUTE = "minute";
    public static final String COLUMN_CHEATSID = "cheatsid";


    //**** create ma table one
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_USER + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FNAME + " TEXT, " +
            COLUMN_LNAME + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_IMAGE + " TEXT, " +
            COLUMN_PHONE + " INTEGER " +")";

    //**** create ma tablelogin
    public static final String TABLE_CREATEUSERLOGIN = "CREATE TABLE " + TABLE_USERLOGIN + " (" +
            COLUMN_IDL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FNAMEL + " TEXT, " +
            COLUMN_LNAMEL + " TEXT, " +
            COLUMN_EMAILL + " TEXT, " +
            COLUMN_IMAGEL + " TEXT, " +
            COLUMN_PHONEL + " INTEGER " +")";

    //**** create ma table two
    public static final String TABLE_CREATEUSERMEASURMENT = "CREATE TABLE " + TABLE_USERMEASURMENT + " (" +
            COLUMN_IDMEASURMENT + " INTEGER, " +
            COLUMN_METHOD + " TEXT, " +
           COLUMN_IDCHEAT + " TEXT, " +
        //  COLUMN_TIME + " TIMESTAMP, " +
            COLUMN_NUMBEROF + " TEXT " + ")";


    //create ma table three
    public static final String TABLE_CREATESCHEDULE = "CREATE TABLE " + TABLE_SCHEDULE + " (" +
            COLUMN_SID + " INTEGER, " +
            COLUMN_LABLE + " TEXT, " +
            COLUMN_YEAR + " TEXT, " +
            COLUMN_MONTH + " TEXT, " +
            COLUMN_DAY + " TEXT, " +
            COLUMN_HOUR + " TEXT, " +
            COLUMN_MINUTE + " TEXT, " +
            COLUMN_CHEATSID + " TEXT " + ")";










    public UsersDBOpenHelper(Context context) {
        super(context, DTATBASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        Log.i(LOGTAGA, "TABLE USER Has been created");
        db.execSQL(TABLE_CREATEUSERMEASURMENT);
        Log.i(LOGTAGA, "TABLE MEASURMENT Has been created");
        db.execSQL(TABLE_CREATEUSERLOGIN);
        Log.i(LOGTAGA, "TABLE login Has been created");
        db.execSQL(TABLE_CREATESCHEDULE);
        Log.i(LOGTAGA, "TABLE SCHEDULE Has been created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREATESCHEDULE);
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERLOGIN);
       onCreate(db);
    }






}
