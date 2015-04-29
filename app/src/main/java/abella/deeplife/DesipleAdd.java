package abella.deeplife;

import android.app.Activity;
import android.content.Intent;
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

import java.io.FileInputStream;
import java.io.IOException;

import Database.User;
import Database.UsersDataSource;

/**
 * Created by Ab on 4/10/2015.
 */
public class DesipleAdd extends Activity implements View.OnClickListener{
    UsersDataSource userdatasource;
    EditText firstname,lastname,phonenumber,email,code;
    Button add,show,getimage;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    ImageView imageview;
Bitmap bm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desipleadd);



        userdatasource = new UsersDataSource(this);
        userdatasource.open();
        initialize();
        add.setOnClickListener(this);
        show.setOnClickListener(this);
        getimage.setOnClickListener(this);
imageview.setImageResource(R.drawable.avater1);
    }

    private void initialize() {
code = (EditText)findViewById(R.id.etcc);
        firstname = (EditText)findViewById(R.id.etfirstname);
        lastname = (EditText)findViewById(R.id.etlastname);
        phonenumber = (EditText)findViewById(R.id.etphonenumber);
        email = (EditText)findViewById(R.id.etemailaddress);
        add = (Button)findViewById(R.id.Badd);
        show= (Button)findViewById(R.id.Bshow);
getimage= (Button)findViewById(R.id.bimage);
      imageview = (ImageView)findViewById(R.id.imageView);
    }

    private void createData(){
        User user = new User();
        user.setFname(firstname.getText().toString());
        user.setLname(lastname.getText().toString());
        user.setEmail(email.getText().toString());
     //   String phoner = code.getText().toString()+phonenumber.getText().toString();
        user.setPhone(code.getText().toString()+phonenumber.getText().toString());

        if (selectedImagePath!=null){

            user.setImage(selectedImagePath);

        }else{
            selectedImagePath = String.valueOf(R.drawable.avater1);
            user.setImage(selectedImagePath);

        }



        user = userdatasource.create(user);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Badd: {
                createData();
                code.setText("");
                firstname.setText("");
                lastname.setText("");
                phonenumber.setText("");
                email.setText("");
                selectedImagePath = null;
               imageview.setImageResource(R.drawable.avater1);

                break;
            }
            case R.id.Bshow: {
                Intent i = new Intent(DesipleAdd.this, DesipleList.class);
                startActivity(i);
                break;
            }
            case R.id.bimage:{
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(
                        Intent.createChooser(intent, "Select Picture"),
                        SELECT_PICTURE);

                break;
        }
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);

//befit yeneberewen image clear lemareg
                   imageview.setImageResource(0);
                //   imageview.setImageURI(selectedImageUri);

                try {
                    Bitmap  bm = decodeFile(getPath(selectedImageUri));
                    //resize z bitmap to ma imageview fo btr performance
                    Bitmap bmscaled = Bitmap.createScaledBitmap(bm,200,200,true);
                    imageview.setImageBitmap(bmscaled);
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


    public void onimageclicked(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                SELECT_PICTURE);
    }
}
