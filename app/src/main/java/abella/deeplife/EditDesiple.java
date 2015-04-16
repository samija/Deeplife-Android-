package abella.deeplife;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
String image,imagenew;
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


            if(selectedImagePath!= null){
                image = selectedImagePath.toString();

            }else{
                selectedImagePath = String.valueOf(R.mipmap.ic_launcher);
                image = selectedImagePath.toString();
            }


            image = cursor.getString(usersDataSource.COL_IMAGE);
            imv.setImageURI(Uri.parse(image));

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
                String imageupdater;
                if (imagenew==null){
                    imageupdater = image;
                }else{
                   imageupdater = imagenew;
                }

                usersDataSource.updateEntry(parser,fnn.getText().toString(),lnn.getText().toString(),phh.getText().toString(),emm.getText().toString(),imageupdater);
                Intent i = new Intent(EditDesiple.this,DesipleList.class);
                Toast.makeText(this,"Update Sucess",Toast.LENGTH_SHORT).show();
                startActivity(i);

                break;
        }


    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);

                System.out.println("Image Path : " + selectedImagePath);

             imv.setImageURI(selectedImageUri);
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
