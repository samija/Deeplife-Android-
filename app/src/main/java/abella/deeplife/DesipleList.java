package abella.deeplife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Database.User;
import Database.UserMeasurment;
import Database.UsersDataSource;

/**
 * Created by Ab on 4/10/2015.
 */
public class DesipleList extends Activity implements View.OnClickListener {
    Button add, desiples, activities;
    int pars = 0;
    UsersDataSource userdatasource;
    User user;
    ImageView lm,di;
    ListView numberlist = null;
    TextView tv;
Context context;
    ContactImageAdapter  adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //full screener
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desiplelist);
context = this;

        tv = (TextView) findViewById(R.id.tv);

        di = (ImageView) findViewById(R.id.imageView2);
        activities = (Button) findViewById(R.id.btnshowactivitiesB);
        activities.setOnClickListener(this);

        desiples = (Button) findViewById(R.id.btnshowdesiplesB);
        desiples.setOnClickListener(this);

        add = (Button) findViewById(R.id.badddesipleB);
        add.setOnClickListener(this);

        lm = (ImageView)findViewById(R.id.loginimage);

        numberlist = (ListView) findViewById(R.id.listView);
        userdatasource = new UsersDataSource(this);
        userdatasource.open();


        final List<User> users = userdatasource.findall();


    }

    @Override
    protected void onPause() {
        super.onPause();
finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.badddesipleB:
                Intent i = new Intent(DesipleList.this, DesipleAdd.class);
                startActivity(i);
                break;
            case R.id.btnshowactivitiesB:
                ArrayAdapter<UserMeasurment> adapterum;

                final List<UserMeasurment> use = userdatasource.findallusermeasurment();

                adapterum = new ArrayAdapter<UserMeasurment>(this, android.R.layout.simple_list_item_1, use);
                numberlist.setAdapter(adapterum);
                numberlist.setVisibility(View.VISIBLE);

                break;
            case R.id.btnshowdesiplesB:

                final List<User> users = userdatasource.findall();
                adapter = new ContactImageAdapter(DesipleList.this, R.layout.item_layout,
                        users);

                numberlist.setAdapter(adapter);
                numberlist.setVisibility(View.VISIBLE);

    break;
}}
































    public void onButtonClickListner(View v) {

        try{
            final List<User> users = userdatasource.findall();
            View parentRow = (View) v.getParent();
            ListView listView = (ListView) parentRow.getParent();
            final int position = listView.getPositionForView(parentRow);


            User current = users.get(position);
            TextView tx = (TextView) findViewById(R.id.tvreavel1);
            tx.setText("" + current.id);




            String bread = tx.getText().toString();
            Bundle basket = new Bundle();
            basket.putString("key", bread);
            Intent i;
            i = new Intent(DesipleList.this, DesipleItemClicked.class);
            i.putExtras(basket);
            startActivity(i);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"File Too Large To Be Handled",Toast.LENGTH_SHORT).show();
        }


    }
}