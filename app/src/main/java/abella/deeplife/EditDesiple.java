package abella.deeplife;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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

import Database.UsersDBOpenHelper;
import Database.UsersDataSource;

/**
 * Created by Ab on 4/12/2015.
 */
public class EditDesiple extends Activity implements View.OnClickListener{

    String gotbread;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    int parser;
Button update,editimage;
   UsersDataSource usersDataSource;
    ImageView imv;
    EditText fnn,lnn,phh,emm;
String image,imagenew,imageuriholder;
    UsersDBOpenHelper usersDBOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
imv = (ImageView)findViewById(R.id.imaged);
editimage = (Button) findViewById(R.id.beditimage);
update = (Button) findViewById(R.id.bupdatedesiple);
        update.setOnClickListener(this);
        editimage.setOnClickListener(this);
       handle(parser);

    }

    private void handle(int parse) {

        Cursor cursor = usersDataSource.getRow(parse);
        if (cursor.moveToFirst()) {

            String fname = cursor.getString(usersDataSource.COL_FNAME);
            String lname = cursor.getString(usersDataSource.COL_LNAME);
            String phone = cursor.getString(usersDataSource.COL_PHONE);
            String email = cursor.getString(usersDataSource.COL_EMAIL);

            image = cursor.getString(usersDataSource.COL_IMAGE);

            if (image.contains("/")) {
                imv.setImageURI(Uri.parse(image));
                imageuriholder = image;
                try {
                    imv.setImageResource(Integer.parseInt(image));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                imv.setImageResource(R.drawable.avater1);
            }



                fnn.setText(fname);
                lnn.setText(lname);
                phh.setText(phone);
                emm.setText(email);
            }
            cursor.close();


        }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.beditimage:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, "Select Picture"),
                        SELECT_PICTURE);

                break;
            case R.id.bupdatedesiple:
try{

    String imageupdater = null;
    if (imagenew==""){
        imageupdater = imageuriholder;
    }else if(imagenew.contains("/")){
        imageupdater = imagenew;
    }

    usersDataSource.updateEntry(parser,fnn.getText().toString(),lnn.getText().toString(),phh.getText().toString(),emm.getText().toString(),imageupdater.toString());
    Intent i = new Intent(EditDesiple.this,DesipleList.class);
    Toast.makeText(this,"Update Sucess",Toast.LENGTH_SHORT).show();
    startActivity(i);

}catch(Exception e){


    usersDataSource.updateEntry(parser,fnn.getText().toString(),lnn.getText().toString(),phh.getText().toString(),emm.getText().toString(),image);
    Intent i = new Intent(EditDesiple.this,DesipleList.class);
    Toast.makeText(this,"Update Sucess",Toast.LENGTH_SHORT).show();
    startActivity(i);


    e.printStackTrace();
}


                break;
        }


    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                imv.setImageURI(selectedImageUri);
                imv.setVisibility(View.VISIBLE);


    imagenew = selectedImagePath;




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




}
