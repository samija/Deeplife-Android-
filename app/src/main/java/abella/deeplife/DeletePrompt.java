package abella.deeplife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import Database.UsersDataSource;

/**
 * Created by Ab on 4/23/2015.
 */
public class DeletePrompt extends Activity implements View.OnClickListener {
    String gotbread;
    int parser;
    Button yes,no;
    UsersDataSource usersDataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.deleteprompt);
        yes=(Button)findViewById(R.id.byes);
        no=(Button)findViewById(R.id.bno);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);




        usersDataSource = new UsersDataSource(this);
        usersDataSource.open();

        Bundle gotbasket = getIntent().getExtras();
        gotbread = gotbasket.getString("delkey");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.byes:
                usersDataSource.deleteRow(Integer.parseInt(gotbread));
                Intent i;
                i = new Intent(DeletePrompt.this, DesipleList.class);
                  startActivity(i);
                break;

            case R.id.bno:

finish();
                break;

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

