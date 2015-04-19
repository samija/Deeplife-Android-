package abella.deeplife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Database.User;
import Database.UserMeasurment;
import Database.UsersDataSource;

/**
 * Created by Ab on 4/10/2015.
 */
public class DesipleList extends Activity implements View.OnClickListener {
    Button add, desiples, activities;
int hacker=1;
    UsersDataSource userdatasource;


    ListView numberlist = null;

    public List<User> myUser = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desiplelist);





        activities = (Button) findViewById(R.id.btnshowactivitiesB);
        activities.setOnClickListener(this);

        desiples = (Button) findViewById(R.id.btnshowdesiplesB);
        desiples.setOnClickListener(this);

        add = (Button) findViewById(R.id.badddesipleB);
        add.setOnClickListener(this);


        numberlist = (ListView) findViewById(R.id.listView);
        userdatasource = new UsersDataSource(this);
        userdatasource.open();


        final List<User> users = userdatasource.findall();


        numberlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
hacker=pos;
                User current = users.get(pos);
                TextView tx = (TextView) findViewById(R.id.tvreavel1);
                tx.setText("" + current.id);

                String bread = tx.getText().toString();
                Bundle basket = new Bundle();
                basket.putString("key", bread);
                Intent i;
                i = new Intent(DesipleList.this, DesipleItemClicked.class);
                i.putExtras(basket);
                startActivity(i);

            }
        });


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

                break;
            case R.id.btnshowdesiplesB:

                ArrayAdapter<User> adapter;
                final List<User> users = userdatasource.findall();
                adapter = new ArrayAdapter<User>(this, R.layout.item_layout, R.id.tv, users);

                numberlist.setAdapter(adapter);
                // adapter = new ArrayAdapter<User>(this,android.R.layout.simple_list_item_1, users);
                // numberlist.setAdapter(adapter);
                numberlist.setVisibility(View.VISIBLE);



                break;
        }

    }


    public void onButtonClickListner(View v) {
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
    }
}