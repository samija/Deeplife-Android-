package abella.deeplife;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Database.Schedule;
import Database.UserMeasurment;
import Database.UsersDataSource;

/**
 * Created by Ab on 4/10/2015.
 */
public class DesipleItemClicked extends Activity implements View.OnClickListener {

    Canvas canvas;
    Button set;
    CheckBox a, b;
    EditText numberof;
    UsersDataSource usersDataSource;
    int parser;
    Button edit, delete, add, show, schedule, showschedule;
    String gotbread;
    TextView fn, ln, ph, em, informer;
    ImageView iv;
    ArrayAdapter<UserMeasurment> adapter;
    ArrayAdapter<Schedule> adaptera;
    ListView listcombination;
    UserMeasurment userMeasurment = new UserMeasurment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desipleitemclicked);
        set = (Button) findViewById(R.id.bsm);
        set.setOnClickListener(this);
        a = (CheckBox) findViewById(R.id.cbme);
        b = (CheckBox) findViewById(R.id.cbotoe);
        a.setOnClickListener(this);
        b.setOnClickListener(this);
        numberof = (EditText) findViewById(R.id.etno);
        informer = (TextView) findViewById(R.id.tvinformer);
        usersDataSource = new UsersDataSource(this);
        usersDataSource.open();


        Bundle gotbasket = getIntent().getExtras();
        gotbread = gotbasket.getString("key");
        parser = Integer.parseInt(gotbread.toString());

        //initializer
        fn = (TextView) findViewById(R.id.tvfirstname);
        ln = (TextView) findViewById(R.id.tvlastname);
        ph = (TextView) findViewById(R.id.tvphonenumber);
        em = (TextView) findViewById(R.id.tvemailaddress);
        iv = (ImageView) findViewById(R.id.image);


        listcombination = (ListView) findViewById(R.id.listcombination);
        showschedule = (Button) findViewById(R.id.bshowschedule);
        showschedule.setOnClickListener(this);

        schedule = (Button) findViewById(R.id.bschedule);
        schedule.setOnClickListener(this);
        edit = (Button) findViewById(R.id.bedit);
        edit.setOnClickListener(this);
        delete = (Button) findViewById(R.id.bdelete);
        delete.setOnClickListener(this);
        add = (Button) findViewById(R.id.baddactivity);
        add.setOnClickListener(this);
        show = (Button) findViewById(R.id.bshowactivity);
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
            Bitmap bmap = BitmapFactory.decodeFile(image);


            if (image != "") {
            //    iv.setImageURI(Uri.parse(image));

                try {
                   //iv.setImageResource(Integer.parseInt(image));
                    getCircleCroppedBitmap(bmap);
                } catch (Exception e) {
                    iv.setImageResource(R.drawable.desiple);
                    e.printStackTrace();
                }finally{

                }

            } else if (image.contentEquals("")) {
               iv.setImageResource(R.drawable.desiple);

            }






            fn.setText(fname + " " + lname);
            // ln.setText(lname);
            ph.setText(phone);
            em.setText(email);
        }
        cursor.close();





    }


    private  Bitmap getCircleCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        iv.setImageBitmap(output);

        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }



























    @Override
    protected void onPause() {
        super.onPause();
      // finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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

                Intent bi;
                String bbread = String.valueOf(parser);
                Bundle bbasket = new Bundle();
                bbasket.putString("delkey", bbread);
                bi = new Intent(DesipleItemClicked.this, DeletePrompt.class);
                bi.putExtras(bbasket);
                startActivity(bi);

         //       usersDataSource.deleteRow(parser);
           //     i = new Intent(DesipleItemClicked.this, DesipleList.class);
           //     startActivity(i);
                break;
            case R.id.baddactivity:
                String dbkeya = String.valueOf(parser);
                Bundle holdera = new Bundle();

                listcombination.setVisibility(View.INVISIBLE);

                set.setVisibility(View.VISIBLE);
                numberof.setVisibility(View.VISIBLE);
                a.setVisibility(View.VISIBLE);
                b.setVisibility(View.VISIBLE);
                informer.setVisibility(View.VISIBLE);


                break;

            case R.id.bshowactivity:

                listcombination.setVisibility(View.VISIBLE);

                set.setVisibility(View.INVISIBLE);
                numberof.setVisibility(View.INVISIBLE);
                a.setVisibility(View.INVISIBLE);
                b.setVisibility(View.INVISIBLE);
                informer.setVisibility(View.INVISIBLE);


                add.setVisibility(View.VISIBLE);
                schedule.setVisibility(View.INVISIBLE);

                Cursor cursor = usersDataSource.getRowfromtabletwo(parser);
                Cursor usermeasurment = usersDataSource.getRowfromtabletwo(parser);
                while (!cursor.isAfterLast()) {


                    List<UserMeasurment> usermeasurmentl = usersDataSource.getRowfromtable(parser);
                    adapter = new ArrayAdapter<UserMeasurment>(this, android.R.layout.simple_list_item_1, usermeasurmentl);
                    listcombination.setAdapter(adapter);

                    cursor.moveToNext();

                }
                cursor.close();
                listcombination.setVisibility(View.VISIBLE);

                break;
            case R.id.bschedule:
                dbkeya = String.valueOf(parser);
                holdera = new Bundle();
                holdera.putString("key", dbkeya);

                Intent ix = new Intent(DesipleItemClicked.this, Scheduler.class);
                ix.putExtras(holdera);
                startActivity(ix);
                break;

            case R.id.bshowschedule:




                set.setVisibility(View.INVISIBLE);
                numberof.setVisibility(View.INVISIBLE);
                a.setVisibility(View.INVISIBLE);
                b.setVisibility(View.INVISIBLE);
                informer.setVisibility(View.INVISIBLE);


                add.setVisibility(View.INVISIBLE);
                schedule.setVisibility(View.VISIBLE);
                listcombination.setVisibility(View.VISIBLE);
                Cursor cursora = usersDataSource.getRowfromschedule(parser);
                Cursor usermeasurmenta = usersDataSource.getRowfromtabletwo(parser);
                while (!cursora.isAfterLast()) {


                    List<Schedule> usermeasurmentl = usersDataSource.getRowfromtablethree(parser);
                    adaptera = new ArrayAdapter<Schedule>(this, android.R.layout.simple_list_item_1, usermeasurmentl);
                    listcombination.setAdapter(adaptera);

                    cursora.moveToNext();
                }
                listcombination.setVisibility(View.VISIBLE);
                break;
            case R.id.bsm:
                createData();
                numberof.setText("");
                break;
            case R.id.cbme:
                b.setChecked(false);
                break;
            case R.id.cbotoe:
                a.setChecked(false);
                break;

        }

    }


    private void createData() {

        userMeasurment.setIdcheat(parser);
        userMeasurment.setNumberof(numberof.getText().toString());
        String getter = numberof.getText().toString();
        if (numberof.getText().toString().contentEquals("")) {
           Toast.makeText(DesipleItemClicked.this,"Please Select A Method",Toast.LENGTH_SHORT).show();
     show.performClick();
        }
       else if(a.isChecked() && getter != ""){
            userMeasurment.setMethod("Massive Evangelism");
            userMeasurment =  usersDataSource.createtabletwo(userMeasurment);
        }else if (b.isChecked()&& getter != ""){
            userMeasurment.setMethod("One To One Evangelism");
            userMeasurment =  usersDataSource.createtabletwo(userMeasurment);
        }else {
            Toast.makeText(this, "Please Select a Method", Toast.LENGTH_SHORT).show();
        }



    }
}