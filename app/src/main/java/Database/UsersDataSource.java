package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ab on 4/7/2015.
 */
public class UsersDataSource {
    private static final String LOGTAG = "EXPLOREUSER";

   SQLiteOpenHelper dbhelper;
   public SQLiteDatabase database;
UsersDBOpenHelper usersDBOpenHelper;
//for retriving from table one
private static final String[] allColumns = {
        UsersDBOpenHelper.COLUMN_LNAME,
        UsersDBOpenHelper.COLUMN_ID,
        UsersDBOpenHelper.COLUMN_FNAME,
        UsersDBOpenHelper.COLUMN_PHONE,
        UsersDBOpenHelper.COLUMN_EMAIL,
        UsersDBOpenHelper.COLUMN_IMAGE};

//for retriving form table two
private static final String[] allcolumnsfromtabletwo = {
        UsersDBOpenHelper.COLUMN_IDMEASURMENT,
        UsersDBOpenHelper.COLUMN_METHOD,
       UsersDBOpenHelper.COLUMN_IDCHEAT,
        UsersDBOpenHelper.COLUMN_NUMBEROF};
    //    UsersDBOpenHelper.COLUMN_TIME};



    private static final String[] allColumnsschedule = {
            UsersDBOpenHelper.COLUMN_SID,
            UsersDBOpenHelper.COLUMN_LABLE,
            UsersDBOpenHelper.COLUMN_YEAR,
            UsersDBOpenHelper.COLUMN_MONTH,
            UsersDBOpenHelper.COLUMN_DAY,
            UsersDBOpenHelper.COLUMN_HOUR,
            UsersDBOpenHelper.COLUMN_MINUTE};


    public static final String KEY_ROWID = "userId";
    public static final String KEY_ROWIDUM = "useridmeasurment";
    public static final String KEY_ROWIDSCHEDULE = "useridschedule";

    public static final int COL_ROWID = 0;


    public static final int COL_FNAME = 2;
    public static final int COL_LNAME = 0;
    public static final int COL_PHONE = 3;
    public static final int COL_EMAIL = 4;
    public static final int COL_IMAGE = 5;


    public static final int COL_IDCHEAT = 2;
    public static final int COL_METHOD = 1;
    public static final int COL_NUMBEROF = 3;


    public static final int COL_SID = 0;
    public static final int COL_LABLE = 1;
    public static final int COL_YEAR = 2;
    public static final int COL_MONTH = 3;
    public static final int COL_DAY = 4;
    public static final int COL_HOUR = 5;
    public static final int COL_MINUTE = 6;



public UsersDataSource(Context context){
    dbhelper = new UsersDBOpenHelper(context);

}

    public void open(){
        database = dbhelper.getWritableDatabase();
        Log.i(LOGTAG,"DATABASE opened");

    }

    public void close(){

        dbhelper.close();
        Log.i(LOGTAG,"DATABASE closed");
    }

   public User create(User user){
       ContentValues values = new ContentValues();
       values.put(UsersDBOpenHelper.COLUMN_FNAME,user.getFname());
       values.put(UsersDBOpenHelper.COLUMN_LNAME,user.getLname());
       values.put(UsersDBOpenHelper.COLUMN_PHONE,user.getPhone());
       values.put(UsersDBOpenHelper.COLUMN_EMAIL,user.getEmail());
      values.put(UsersDBOpenHelper.COLUMN_IMAGE,user.getImage());
       int insertid = (int) database.insert(UsersDBOpenHelper.TABLE_USER,null,values);
       user.setId(insertid);
return user;
    }

    public UserMeasurment createtabletwo(UserMeasurment userMeasurment){
        ContentValues value = new ContentValues();
        value.put(UsersDBOpenHelper.COLUMN_METHOD,userMeasurment.getMethod());
        value.put(UsersDBOpenHelper.COLUMN_NUMBEROF,userMeasurment.getNumberof());
       value.put(UsersDBOpenHelper.COLUMN_IDCHEAT,userMeasurment.getIdcheat());
        value.put(UsersDBOpenHelper.COLUMN_IDMEASURMENT,userMeasurment.getIdcheat());
       // values.put(UsersDBOpenHelper.COLUMN_TIME,userMeasurment.getTime());
        int insertMeasurmentid = (int) database.insert(UsersDBOpenHelper.TABLE_USERMEASURMENT,null,value);
        userMeasurment.setId(insertMeasurmentid);
        return userMeasurment;
    }

    public Schedule createschedule(Schedule schedule){
        ContentValues values = new ContentValues();

        values.put(UsersDBOpenHelper.COLUMN_LABLE,schedule.getLable());
        values.put(UsersDBOpenHelper.COLUMN_YEAR,schedule.getYear());
        values.put(UsersDBOpenHelper.COLUMN_MONTH,schedule.getMonth());
        values.put(UsersDBOpenHelper.COLUMN_DAY,schedule.getDay());
        values.put(UsersDBOpenHelper.COLUMN_HOUR,schedule.getHour());
        values.put(UsersDBOpenHelper.COLUMN_MINUTE,schedule.getMinute());
        values.put(UsersDBOpenHelper.COLUMN_CHEATSID,schedule.getCheatsid());
        values.put(UsersDBOpenHelper.COLUMN_SID,schedule.getCheatsid());
        int insertid = (int) database.insert(UsersDBOpenHelper.TABLE_SCHEDULE,null,values);
        schedule.setId(insertid);
        return schedule;
    }





public List<User> findall(){
List<User> users = new ArrayList<User>();

    Cursor cursor = database.query(UsersDBOpenHelper.TABLE_USER,allColumns,null,null,null,null,null,null);
Log.i(LOGTAG,"returned "+ cursor.getCount() + " rows");
if (cursor.getCount() >0){
while (cursor.moveToNext()){
    User user = new User();
    user.setId(cursor.getInt(cursor.getColumnIndex(UsersDBOpenHelper.COLUMN_ID.toString())));
    user.setFname(cursor.getString(cursor.getColumnIndex(UsersDBOpenHelper.COLUMN_FNAME.toString())));
    user.setLname(cursor.getString(cursor.getColumnIndex(UsersDBOpenHelper.COLUMN_LNAME)));
    user.setPhone(cursor.getString(cursor.getColumnIndex(UsersDBOpenHelper.COLUMN_PHONE)));
    user.setEmail(cursor.getString(cursor.getColumnIndex(UsersDBOpenHelper.COLUMN_EMAIL)));
    user.setImage(cursor.getString(cursor.getColumnIndex(UsersDBOpenHelper.COLUMN_IMAGE)));

   users.add(user);
}

}

return users;
}

    public List<UserMeasurment> findallusermeasurment(){
        List<UserMeasurment> userMeasurments = new ArrayList<UserMeasurment>();

        Cursor cursor = database.query(UsersDBOpenHelper.TABLE_USERMEASURMENT,allcolumnsfromtabletwo,null,null,null,null,null,null);
        Log.i(LOGTAG,"returned "+ cursor.getCount() + " rows");
        if (cursor.getCount() >0){
            while (cursor.moveToNext()){
                UserMeasurment userMeasurment = new UserMeasurment();
                userMeasurment.setIdcheat(cursor.getInt(cursor.getColumnIndex(UsersDBOpenHelper.COLUMN_IDCHEAT.toString())));
                userMeasurment.setMethod(cursor.getString(cursor.getColumnIndex(UsersDBOpenHelper.COLUMN_METHOD.toString())));
                userMeasurment.setNumberof(cursor.getString(cursor.getColumnIndex(UsersDBOpenHelper.COLUMN_NUMBEROF)));
                userMeasurment.setId(cursor.getInt(cursor.getColumnIndex(UsersDBOpenHelper.COLUMN_IDCHEAT)));
               // userMeasurment.setTime(cursor.getString(cursor.getColumnIndex(UsersDBOpenHelper.COLUMN_TIME)));
                userMeasurments.add(userMeasurment);
            }

        }

        return userMeasurments;
    }



    public List<Schedule> findallschedule(){
        List<Schedule> schedules = new ArrayList<Schedule>();

        Cursor cursor = database.query(UsersDBOpenHelper.TABLE_SCHEDULE,allColumnsschedule,null,null,null,null,null,null);
        Log.i(LOGTAG,"returned "+ cursor.getCount() + " rows");
        if (cursor.getCount() >0){
            while (cursor.moveToNext()){
                Schedule schedule = new Schedule();

                schedule.setLable(cursor.getString(cursor.getColumnIndex(UsersDBOpenHelper.COLUMN_LABLE.toString())));
                schedule.setYear(cursor.getString(cursor.getColumnIndex(UsersDBOpenHelper.COLUMN_YEAR.toString())));
                schedule.setMonth(cursor.getString(cursor.getColumnIndex(UsersDBOpenHelper.COLUMN_MONTH.toString())));
                schedule.setDay(cursor.getString(cursor.getColumnIndex(UsersDBOpenHelper.COLUMN_DAY.toString())));
                schedule.setHour(cursor.getString(cursor.getColumnIndex(UsersDBOpenHelper.COLUMN_HOUR.toString())));
                schedule.setMinute(cursor.getString(cursor.getColumnIndex(UsersDBOpenHelper.COLUMN_MINUTE.toString())));
                schedule.setCheatsid(cursor.getInt(cursor.getColumnIndex(UsersDBOpenHelper.COLUMN_CHEATSID)));
             //   schedule.setSid(cursor.getInt(cursor.getColumnIndex(UsersDBOpenHelper.COLUMN_CHEATSID)));
                schedules.add(schedule);
            }

        }

        return schedules;
    }




    public int getuserscount() {
        String countQuery = "SELECT  * FROM " + UsersDBOpenHelper.TABLE_USER;
        SQLiteDatabase db = this.usersDBOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


    public Cursor getRow(int rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	database.query(true, UsersDBOpenHelper.TABLE_USER, allColumns,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public List<UserMeasurment> getRowfromtable(int rowId) {
        List<UserMeasurment> userMeasurmentList = new ArrayList<UserMeasurment>();
Cursor c = database.query(UsersDBOpenHelper.TABLE_USERMEASURMENT,allcolumnsfromtabletwo,UsersDBOpenHelper.COLUMN_IDMEASURMENT + " = ?",
        new String[]{String.valueOf(rowId)},null,null,null,null);
        c.moveToFirst();

        while(!c.isAfterLast()){

            UserMeasurment userMeasurment = cursortouserme(c);
           userMeasurmentList.add(userMeasurment);
            c.moveToNext();

        }
        c.close();
        return userMeasurmentList;
    }





    public List<Schedule> getRowfromtablethree(int rowId) {
        List<Schedule> schedulelist = new ArrayList<Schedule>();
        Cursor c = database.query(UsersDBOpenHelper.TABLE_SCHEDULE,allColumnsschedule,UsersDBOpenHelper.COLUMN_SID + " = ?",
                new String[]{String.valueOf(rowId)},null,null,null,null);
        c.moveToFirst();

        while(!c.isAfterLast()){

            Schedule schedule = cursoradder(c);
            schedulelist.add(schedule);
            c.moveToNext();

        }
        c.close();
        return schedulelist;
    }


    private Schedule cursoradder(Cursor c) {
        Schedule uma= new Schedule();
        uma.setId(c.getInt(0));
        uma.setSid(c.getInt(0));
        uma.setLable(c.getString(1));
        uma.setYear(c.getString(2));
        uma.setMonth(c.getString(3));
        uma.setDay(c.getString(4));
        uma.setHour(c.getString(5));
        uma.setMinute(c.getString(6));

        return  uma;

    }



    private UserMeasurment cursortouserme(Cursor c) {
UserMeasurment um= new UserMeasurment();
        um.setId(c.getInt(0));
        um.setIdcheat(c.getInt(0));
        um.setNumberof(c.getString(3));
        um.setMethod(c.getString(1));
        return  um;

    }


    public Cursor getRowfromtabletwo(int rowId) {
        String where = KEY_ROWIDUM + "=" + rowId;
        Cursor c = 	database.query(true, UsersDBOpenHelper.TABLE_USERMEASURMENT, allcolumnsfromtabletwo,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    public Cursor getRowfromschedule(int rowId) {
        String where = KEY_ROWIDSCHEDULE + "=" + rowId;
        Cursor c = 	database.query(true, UsersDBOpenHelper.TABLE_SCHEDULE, allColumnsschedule,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }






    public boolean deleteRow(int rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return database.delete(usersDBOpenHelper.TABLE_USER, where, null) != 0;
    }
    public boolean deleteRowfromtabletwo(int rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return database.delete(usersDBOpenHelper.TABLE_USERMEASURMENT, where, null) != 0;
    }


    //update a specific row
    public void updateEntry(int id,String firstname,String lasttname,String phone,String email,String image){
        ContentValues cvUpdate =new ContentValues();
        cvUpdate.put(UsersDBOpenHelper.COLUMN_FNAME,firstname);
        cvUpdate.put(UsersDBOpenHelper.COLUMN_LNAME,lasttname);
        cvUpdate.put(UsersDBOpenHelper.COLUMN_PHONE,phone);
        cvUpdate.put(UsersDBOpenHelper.COLUMN_EMAIL,email);
        cvUpdate.put(UsersDBOpenHelper.COLUMN_IMAGE,image);

        database.update(usersDBOpenHelper.TABLE_USER,cvUpdate,KEY_ROWID + "=" + id,null);
    }

}
