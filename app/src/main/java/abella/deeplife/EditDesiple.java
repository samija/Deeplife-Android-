package abella.deeplife;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import Database.User;
import Database.UsersDBOpenHelper;
import Database.UsersDataSource;

/**
 * Created by Ab on 4/12/2015.
 */
public class EditDesiple extends Activity implements View.OnClickListener{
    String gotbread;
    int parser;
Button update;
   UsersDataSource usersDataSource;
    ImageView iv;
    EditText fnn,lnn,phh,emm;
    UsersDBOpenHelper usersDBOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editdesiple);

        usersDataSource = new UsersDataSource(this);
        usersDataSource.open();

        Bundle gotbasket = getIntent().getExtras();
        gotbread = gotbasket.getString("nun");


        parser = Integer.parseInt(gotbread.toString());
        fnn = (EditText)findViewById(R.id.etupdatefn);
        lnn = (EditText)findViewById(R.id.etupdateln);
        phh = (EditText)findViewById(R.id.etupdatephone);
        emm= (EditText)findViewById(R.id.etupdateemail);
update = (Button) findViewById(R.id.bupdatedesiple);
        update.setOnClickListener(this);
       handle(parser);

    }

    private void handle(int parse) {

        Cursor cursor = usersDataSource.getRow(parse);
        if (cursor.moveToFirst()) {

           String fname = cursor.getString(usersDataSource.COL_FNAME);
            String lname = cursor.getString(usersDataSource.COL_LNAME);
            String phone = cursor.getString(usersDataSource.COL_PHONE);
            String email = cursor.getString(usersDataSource.COL_EMAIL);
           fnn.setText(fname);
            lnn.setText(lname);
            phh.setText(phone);
            emm.setText(email);
        }
        cursor.close();





    }

    @Override
    public void onClick(View view) {
       usersDataSource.updateEntry(parser,fnn.getText().toString(),lnn.getText().toString(),phh.getText().toString(),emm.getText().toString());
        Intent i = new Intent(EditDesiple.this,DesipleList.class);
        Toast.makeText(this,"Update Sucess",Toast.LENGTH_SHORT).show();
        startActivity(i);

    }
}
