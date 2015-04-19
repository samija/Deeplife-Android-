package abella.deeplife;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import Database.User;
import Database.UsersDBOpenHelper;
import Database.UsersDataSource;

/**
 * Created by Ab on 4/10/2015.
 */
public class DesipleItemClicked extends Activity implements View.OnClickListener{

    UsersDataSource usersDataSource;
    int  parser;
Button edit,delete,add,show,schedule,showschedule;
    String gotbread;
    TextView fn,ln,ph,em;
ImageView iv;
    @Override
    protected void onCreate  (Bundle savedInstanceState) {
        super.onCreate  (savedInstanceState);
        setContentView(R.layout.desipleitemclicked);

        usersDataSource = new UsersDataSource(this);
        usersDataSource.open();


        Bundle gotbasket = getIntent().getExtras();
        gotbread = gotbasket.getString("key");
        parser = Integer.parseInt(gotbread.toString());

        //initializer
        fn = (TextView)findViewById(R.id.tvfirstname);
        ln = (TextView)findViewById(R.id.tvlastname);
        ph = (TextView)findViewById(R.id.tvphonenumber);
        em = (TextView)findViewById(R.id.tvemailaddress);
        iv = (ImageView)findViewById(R.id.image);

        showschedule = (Button)findViewById(R.id.bshowschedule);
        showschedule.setOnClickListener(this);

        schedule=(Button)findViewById(R.id.bschedule);
        schedule.setOnClickListener(this);
        edit=(Button)findViewById(R.id.bedit);
        edit.setOnClickListener(this);
        delete=(Button)findViewById(R.id.bdelete);
        delete.setOnClickListener(this);
        add=(Button)findViewById(R.id.baddactivity);
        add.setOnClickListener(this);
        show = (Button)findViewById(R.id.bshowactivity);
        show.setOnClickListener(this);


        interfaceview(parser);

    }

    private void interfaceview(int idInDB) {

        Cursor cursor = usersDataSource.getRow(idInDB);
        if (cursor.moveToFirst()) {

            String fname = cursor.getString(usersDataSource.COL_FNAME);
            String lname = cursor.getString(usersDataSource.COL_LNAME);
            String phone = cursor.getString(usersDataSource.COL_PHONE);
            String email = cursor.getString(usersDataSource.COL_EMAIL);
String image = cursor.getString(usersDataSource.COL_IMAGE);
            iv.setImageURI(Uri.parse(image));

            fn.setText(fname);
            ln.setText(lname);
            ph.setText(phone);
            em.setText(email);
        }
        cursor.close();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onClick(View view) {
switch (view.getId()){
    case R.id.bedit:
        Intent i;
        String bread = String.valueOf(parser);
        Bundle basket = new Bundle();
        basket.putString("nun", bread);
        i = new Intent(DesipleItemClicked.this, EditDesiple.class);
        i.putExtras(basket);

        startActivity(i);

        break;
    case R.id.bdelete:
        usersDataSource.deleteRow(parser);
         i = new Intent(DesipleItemClicked.this, DesipleList.class);
        startActivity(i);
        break;
    case R.id.baddactivity:
        String dbkeya = String.valueOf(parser);
        Bundle holdera = new Bundle();
        holdera.putString("key", dbkeya);
        Intent ii;
        ii = new Intent(DesipleItemClicked.this, AddDesipleActivity.class);
        ii.putExtras(holdera);
        startActivity(ii);
        break;

    case R.id.bshowactivity:
        String dbkey = String.valueOf(parser);
        Bundle holder = new Bundle();
        holder.putString("key", dbkey);
        Intent iii;
        iii = new Intent(DesipleItemClicked.this, ShowDesipleActivity.class);
        iii.putExtras(holder);
        startActivity(iii);
        break;
    case R.id.bschedule:
        dbkeya = String.valueOf(parser);
       holdera = new Bundle();
        holdera.putString("key", dbkeya);

Intent ix = new Intent(DesipleItemClicked.this,Scheduler.class);
        ix.putExtras(holdera);
        startActivity(ix);
        break;

    case R.id.bshowschedule:
        dbkeya = String.valueOf(parser);
        holdera = new Bundle();
        holdera.putString("key", dbkeya);

        Intent ixx = new Intent(DesipleItemClicked.this,ShowSchedule.class);
        ixx.putExtras(holdera);
        startActivity(ixx);
        break;

}
    }
}
