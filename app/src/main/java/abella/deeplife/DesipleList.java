package abella.deeplife;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import Database.User;
import Database.UserMeasurment;
import Database.UsersDataSource;

/**
 * Created by Ab on 4/10/2015.
 */
public class DesipleList extends Activity implements View.OnClickListener{
    ImageView loginimg;
    TextView logintxt;
    ContactImageAdapter contactImageAdapter;
    Button add, desiples, activities;
    int pars = 0;
    UsersDataSource userdatasource;
    User user;
    ImageView lm,di;
    ListView numberlist = null;
    TextView totald,tv,welcome,t18; //t18 malet activityn click sidereg adapter adapt yemiyaregew layout west yalew textview nw
Context context;
    ContactImageAdapter  adapter;
ProgressBar pb;
TextView tc;


//for ma drawer
private DrawerLayout drawerLayout;
    private ListView listView;
    private DrawerAdapter drawerAdapter;
    private ActionBarDrawerToggle drawerlistner;
    //end ma drawer declaration
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //actionbar color chnage
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0099cc")));

        setContentView(R.layout.desiplelist);
        //Fo ma drawer
drawerAdapter = new DrawerAdapter(this);

        loginimg = (ImageView)findViewById(R.id.loginimage);
        logintxt = (TextView)findViewById(R.id.tvwelcome);
        listView = (ListView)findViewById(R.id.drawerlistl);
        //listview is a listview on ma drawer
       listView.setAdapter(drawerAdapter);
        drawerLayout =(DrawerLayout)findViewById(R.id.drawerlayout);
        drawerlistner = new ActionBarDrawerToggle(this,drawerLayout,R.drawable.nav2,R.string.droweropen ,R.string.drowerclose);
        drawerLayout.setDrawerListener(drawerlistner);
        //allow clickable actionbar
        getActionBar().setHomeButtonEnabled(true);
        //allow animatted navigation button on z actionbar. drawere slide sidereg default animate yaregal ma button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //end ma drawer





t18 = (TextView)findViewById(R.id.textView18);
totald = (TextView)findViewById(R.id.tvtotaldisaples);

context = this;
welcome = (TextView)findViewById(R.id.tvwelcome);
        welcome.setTypeface(Typeface.createFromAsset(context.getAssets(), "mafont2.ttf"));
        tv = (TextView) findViewById(R.id.tv);
pb = (ProgressBar)findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
        tc = (TextView)findViewById(R.id.textView16);
        tc.setVisibility(View.INVISIBLE);
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
        totald.setTypeface(Typeface.createFromAsset(context.getAssets(), "mafont2.ttf"));

        final List<User> users = userdatasource.findall();
        //logindata set maregya
        findandsetlogindata();


    }

    private void findandsetlogindata() {
        Cursor cursor = userdatasource.getRowl(0);
        if (cursor.moveToFirst()) {

            String fname = cursor.getString( userdatasource.COL_FNAMEL);
            String lname = cursor.getString( userdatasource.COL_LNAMEL);
            String image = cursor.getString(userdatasource.COL_IMAGEL);
            logintxt.setText("Welcome "+fname+" "+lname);

            try {
                Bitmap bm = decodeFile(image);

               //if image is not null
                if (image.contains("/")) {
                    //imv.setImageURI(Uri.parse(image));
                    getCircleCroppedBitmap(bm);

                }
           } catch (IOException e) {
                e.printStackTrace();
                loginimg.setImageResource(R.drawable.avater1);

            }


        }
        cursor.close();


    }
    private Bitmap decodeFile(String f) throws IOException {
        Bitmap b = null;

        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        FileInputStream fis = new FileInputStream(f);
        BitmapFactory.decodeStream(fis, null, o);
        fis.close();

        int scale = 1;
        if (o.outHeight > 1000 || o.outWidth > 1000) {
            scale = (int) Math.pow(2, (int) Math.ceil(Math.log(1000 /
                    (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
        }

        //Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        fis = new FileInputStream(f);
        b = BitmapFactory.decodeStream(fis, null, o2);
        fis.close();

        return b;
    }
    private Bitmap getCircleCroppedBitmap(Bitmap bitmap) {
        try{
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
            loginimg.setImageBitmap(output);

            //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
            //return _bmp;
            return output;
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Image Too Large To Handle",Toast.LENGTH_SHORT).show();

        }
        return null;
    }

    //drawer adaptere navigation imageen action bar lay adapt endiyareg
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerlistner.syncState();
    }
//drawer dadptere adapt yaregewen image seneka drawere endikefet
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerlistner.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
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

                adapterum = new ArrayAdapter<UserMeasurment>(this, R.layout.activitylayoutitem,R.id.textView18,use);

                numberlist.setAdapter(adapterum);
                numberlist.setVisibility(View.VISIBLE);
//displays how many Activities are there
                totald.setText("Total Activities: "+UsersDataSource.getnumofactivities);
                break;
            case R.id.btnshowdesiplesB:
              new BitmapWorkerTask().execute();
                //displays how many Desiples are there
                totald.setText("Total Desiples: "+UsersDataSource.getnumofdesiples);

                  break;

}}

    class BitmapWorkerTask extends AsyncTask<Void,Void,Void > {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

                pb.setVisibility(View.VISIBLE);
                tc.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            final List<User> users = userdatasource.findall();
            adapter = new ContactImageAdapter(DesipleList.this, R.layout.item_layout,
                    users);
            tc.setVisibility(View.VISIBLE);
            pb.setVisibility(View.VISIBLE);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            numberlist.setAdapter(adapter);
            numberlist.setVisibility(View.VISIBLE);
            pb.setVisibility(View.INVISIBLE);
            tc.setVisibility(View.INVISIBLE);


        }
    }





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

class DrawerAdapter extends BaseAdapter{
String[] drawerliststr;
    private Context context;
    int[] drawerlistimg = {R.drawable.drawerhome,R.drawable.draweradduser,R.drawable.drawercontactus,R.drawable.draweraboutus,R.drawable.drawerlogout};
    public DrawerAdapter(Context context){
        drawerliststr = context.getResources().getStringArray(R.array.Draweritems);
this.context=context;
    }
    @Override
    public int getCount() {
        return drawerliststr.length;
    }

    @Override
    public Object getItem(int pos) {
        return drawerliststr[pos];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int pos, View Convertview, ViewGroup viewGroup) {
        View row = null;
        if(Convertview == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          row =  inflater.inflate(R.layout.drawerrow,viewGroup,false);
        }else{
row = Convertview;
        }
        TextView itemstr = (TextView)row.findViewById(R.id.tvdrawer);
        ImageView itemimg = (ImageView)row.findViewById(R.id.imgdrawer);

        itemstr.setText(drawerliststr[pos]);
        itemimg.setImageResource(drawerlistimg[pos]);
        return row;
    }
}