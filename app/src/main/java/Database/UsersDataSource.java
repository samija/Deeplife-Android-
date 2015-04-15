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



    public static final String KEY_ROWID = "userId";
    public static final String KEY_ROWIDUM = "useridmeasurment";
    public static final int COL_ROWID = 0;


    public static final int COL_FNAME = 2;
    public static final int COL_LNAME = 0;
    public static final int COL_PHONE = 3;
    public static final int COL_EMAIL = 4;
    public static final int COL_IMAGE = 5;

    public static final int COL_IDCHEAT = 2;
    public static final int COL_METHOD = 1;
    public static final int COL_NUMBEROF = 3;


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






    public boolean deleteRow(int rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return database.delete(usersDBOpenHelper.TABLE_USER, where, null) != 0;
    }
    public boolean deleteRowfromtabletwo(int rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return database.delete(usersDBOpenHelper.TABLE_USERMEASURMENT, where, null) != 0;
    }


    //update a specific row
    public void updateEntry(int id,String firstname,String lasttname,String phone,String email){
        ContentValues cvUpdate =new ContentValues();
        cvUpdate.put(UsersDBOpenHelper.COLUMN_FNAME,firstname);
        cvUpdate.put(UsersDBOpenHelper.COLUMN_LNAME,lasttname);
        cvUpdate.put(UsersDBOpenHelper.COLUMN_PHONE,phone);
        cvUpdate.put(UsersDBOpenHelper.COLUMN_EMAIL,email);
     //   cvUpdate.put(UsersDBOpenHelper.COLUMN_IMAGE,image);

        database.update(usersDBOpenHelper.TABLE_USER,cvUpdate,KEY_ROWID + "=" + id,null);
    }

}
