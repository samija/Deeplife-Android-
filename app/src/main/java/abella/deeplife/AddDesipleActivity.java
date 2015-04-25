package abella.deeplife;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import Database.UserMeasurment;
import Database.UsersDataSource;

/**
 * Created by Ab on 4/13/2015.
 */
public class AddDesipleActivity extends Activity implements View.OnClickListener {
    Button set;
    CheckBox a,b;
    EditText numberof;
String gotbread;
    int parser;
UsersDataSource usersDataSource;
    UserMeasurment userMeasurment = new UserMeasurment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adddesipleactivity);





        usersDataSource = new UsersDataSource(this);
        usersDataSource.open();


        Bundle gotbasket = getIntent().getExtras();
        gotbread = gotbasket.getString("key");
        parser = Integer.parseInt(gotbread.toString());



        set = (Button)findViewById(R.id.bsetactivity);
        numberof=(EditText)findViewById(R.id.etsetnumberof);
        a = (CheckBox)findViewById(R.id.cbmassiveevangelism);
        b = (CheckBox)findViewById(R.id.cbonetooneevangelism);
        a.setOnClickListener(this);
        b.setOnClickListener(this);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createData();
                numberof.setText("");
               Toast.makeText(getBaseContext(),"Activity Created "+gotbread,Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void createData(){

        userMeasurment.setIdcheat(Integer.parseInt(gotbread));
        userMeasurment.setNumberof(numberof.getText().toString());
        if(a.isChecked()){
           userMeasurment.setMethod("Massive Evangelism");
       }else if (b.isChecked()){
           userMeasurment.setMethod("One To One Evangelism");
       }else{
            Toast.makeText(this,"Please Select a Method",Toast.LENGTH_SHORT).show();
            finish();
        }



       userMeasurment =  usersDataSource.createtabletwo(userMeasurment);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cbmassiveevangelism:
b.setChecked(false);

                break;
            case R.id.cbonetooneevangelism:
a.setChecked(false);
                break;
        }
    }
}
