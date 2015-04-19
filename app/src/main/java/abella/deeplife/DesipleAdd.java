package abella.deeplife;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import Database.User;
import Database.UsersDataSource;

/**
 * Created by Ab on 4/10/2015.
 */
public class DesipleAdd extends Activity implements View.OnClickListener{
    UsersDataSource userdatasource;
    EditText firstname,lastname,phonenumber,email;
    Button add,show,getimage;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desipleadd);
        userdatasource = new UsersDataSource(this);
        userdatasource.open();
        initialize();
        add.setOnClickListener(this);
        show.setOnClickListener(this);
        getimage.setOnClickListener(this);

    }

    private void initialize() {

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
        user.setPhone(phonenumber.getText().toString());

        if (selectedImagePath!=null){

            user.setImage(selectedImagePath);
        }else{
            selectedImagePath = String.valueOf(R.mipmap.ic_launcher);

            user.setImage(selectedImagePath);
        }



        user = userdatasource.create(user);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Badd: {
                createData();
                firstname.setText("");
                lastname.setText("");
                phonenumber.setText("");
                email.setText("");
                selectedImagePath = null;
              imageview.setImageDrawable(null);

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

                System.out.println("Image Path : " + selectedImagePath);
                imageview.setVisibility(View.VISIBLE);
                imageview.setImageURI(selectedImageUri);



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
