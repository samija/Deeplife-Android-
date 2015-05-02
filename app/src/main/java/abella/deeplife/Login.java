package abella.deeplife;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;

import Database.UserLogin;
import Database.UsersDataSource;

/**
 * Created by Ab on 5/1/2015.
 */
public class Login extends Activity implements View.OnClickListener{
    //save one to preferance then check if this one exists so that ezi login page west aygebam :DDD
    final static String Prefss = "ABG";
    SharedPreferences settings;
    String prefvalue;


    Button login;
    ImageView loginerimage;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    EditText firsnamel,lastnamel;
    UsersDataSource usersDataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        settings = getPreferences(MODE_PRIVATE);
        login=(Button)findViewById(R.id.Baddl);
        firsnamel = (EditText)findViewById(R.id.etfirstnamel);
        lastnamel = (EditText)findViewById(R.id.etlastnamel);
        login.setOnClickListener(this);
        loginerimage = (ImageView)findViewById(R.id.imageViewl);
        loginerimage.setOnClickListener(this);

        usersDataSource = new UsersDataSource(this);
        usersDataSource.open();
       loginerimage.setImageResource(R.drawable.avater1);
        prefvalue = settings.getString(Prefss,"Not Found");

        if(prefvalue.contentEquals("hacked")){
             Intent i = new Intent(this,DesipleList.class);
             startActivity(i);
             this.finish();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Baddl:
                //creates the data
                UserLogin userLogin = new UserLogin();

                userLogin.setFnamel(firsnamel.getText().toString());
                userLogin.setLnamel(lastnamel.getText().toString());

                if (selectedImagePath!=null){

                    userLogin.setImagel(selectedImagePath);

                }else{
                    selectedImagePath = String.valueOf(R.drawable.avater1);
                    userLogin.setImagel(selectedImagePath);

                }

                userLogin = usersDataSource.createlogin(userLogin);


                SharedPreferences.Editor editer;
                editer = settings.edit();
                String prefv = "hacked";
                editer.putString(Prefss,prefv);
                editer.commit();

                Toast.makeText(this,"Welcome "+ firsnamel.getText().toString() + " " + lastnamel.getText().toString(),Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this,DesipleList.class);
Login.this.finish();
                startActivity(i);

                break;
            case R.id.imageViewl:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, "Select Picture"),
                        SELECT_PICTURE);
                break;
        }

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);

//befit yeneberewen image clear lemareg
                loginerimage.setImageResource(0);


                try {
                    Bitmap bm = decodeFile(getPath(selectedImageUri));
                    //resize z bitmap to ma imageview fo btr performance
                    Bitmap bmscaled = Bitmap.createScaledBitmap(bm,200,200,true);
                    loginerimage.setImageResource(0);
                    loginerimage.setImageBitmap(bmscaled);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @SuppressWarnings("deprecation")
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
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


}
