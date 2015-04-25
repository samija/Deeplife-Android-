package abella.deeplife;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Database.UserMeasurment;
import Database.UsersDataSource;

/**
 * Created by Ab on 4/13/2015.
 */
public class ShowDesipleActivity extends Activity {
    TextView numof,methd;
    UsersDataSource userdatasource;
    String holder;
    int parser;
    ListView tbtwo = null;

    ArrayAdapter<UserMeasurment> adapter;
    private List<UserMeasurment> myUserMeasurment = new ArrayList<UserMeasurment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.showdesipleactivity);


        numof = (TextView)findViewById(R.id.tvgetnumberof);
        methd = (TextView)findViewById(R.id.tvgetmethodof);
        tbtwo = (ListView)findViewById(R.id.listView2);

        userdatasource = new UsersDataSource(this);
        userdatasource.open();

        Bundle gotholder = getIntent().getExtras();
        holder = gotholder.getString("key");
        parser = Integer.parseInt(holder);






      //  final List<UserMeasurment> usermeasurment = userdatasource.findallusermeasurment();
       // adapter = new ArrayAdapter<UserMeasurment>(this,android.R.layout.simple_list_item_1,usermeasurment);

    //   tbtwo.setAdapter(adapter);



        Toast.makeText(this,""+parser,Toast.LENGTH_SHORT).show();
        interfacer();


    }

    private void interfacer() {




           Cursor cursor = userdatasource.getRowfromtabletwo(parser);
           Cursor usermeasurment = userdatasource.getRowfromtabletwo(parser);
            while (!cursor.isAfterLast()) {

                String numberof = cursor.getString(userdatasource.COL_NUMBEROF);
                String method = cursor.getString(userdatasource.COL_METHOD);
                numof.setText(numberof);
                methd.setText(method);


                List<UserMeasurment> usermeasurmentl = userdatasource.getRowfromtable(parser);
                adapter = new ArrayAdapter<UserMeasurment>(this,android.R.layout.simple_list_item_1,usermeasurmentl);
                tbtwo.setAdapter(adapter);

                cursor.moveToNext();

                Toast.makeText(this,"done",Toast.LENGTH_SHORT).show();


           }
            cursor.close();





        }

    }

