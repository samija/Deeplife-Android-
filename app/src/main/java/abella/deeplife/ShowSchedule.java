package abella.deeplife;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import Database.Schedule;
import Database.User;
import Database.UserMeasurment;
import Database.UsersDataSource;

/**
 * Created by Ab on 4/17/2015.
 */

public class ShowSchedule extends Activity {
    UsersDataSource userdatasource;
    String holder;
    int parser;
    ArrayAdapter<Schedule> adapter;
    ListView listschedules = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showschedule);
listschedules = (ListView)findViewById(R.id.listviewschedule);
        userdatasource = new UsersDataSource(this);
        userdatasource.open();

        Bundle gotholder = getIntent().getExtras();
        holder = gotholder.getString("key");
        parser = Integer.parseInt(holder);







interfaceview();


    }

    private void interfaceview() {

        Cursor cursor = userdatasource.getRowfromschedule(parser);
       Cursor usermeasurment = userdatasource.getRowfromtabletwo(parser);
        while (!cursor.isAfterLast()) {


            List<Schedule> usermeasurmentl = userdatasource.getRowfromtablethree(parser);
            adapter = new ArrayAdapter<Schedule>(this,android.R.layout.simple_list_item_1,usermeasurmentl);
            listschedules.setAdapter(adapter);

            cursor.moveToNext();

            Toast.makeText(this,"done",Toast.LENGTH_SHORT).show();


        }
        cursor.close();





    }

    }


