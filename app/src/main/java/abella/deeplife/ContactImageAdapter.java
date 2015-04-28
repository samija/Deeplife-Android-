package abella.deeplife;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Database.User;
import Database.UsersDataSource;

public class ContactImageAdapter extends ArrayAdapter<User> {
    UsersDataSource usersDataSource;

	 Context context;
	    int layoutResourceId;

	    ArrayList<User> data=new ArrayList<User>();
	    public ContactImageAdapter(Context context, int layoutResourceId, List<User> data) {
	        super(context, layoutResourceId, data);
	        this.layoutResourceId = layoutResourceId;
	        this.context = context;
	        this.data = (ArrayList<User>) data;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
	        ImageHolder holder = null;

	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);

	            holder = new ImageHolder();
	            holder.txtTitle = (TextView)row.findViewById(R.id.tv);
	            holder.imgIcon = (ImageView)row.findViewById(R.id.imageView2);
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (ImageHolder)row.getTag();
	        }

	       User picture = data.get(position);
	       holder.txtTitle.setText(picture.fname + " " +picture.lname + "\n" + picture.phone);

usersDataSource = new UsersDataSource(context);
            usersDataSource.open();


    Cursor cursor = usersDataSource.getRow(position+1);
    if (cursor.moveToFirst()) {

    //   holder.imgIcon.setImageURI(Uri.parse(picture.image));
        try {
          Bitmap bm=  decodeFile(cursor.getString(usersDataSource.COL_IMAGE));
            holder.imgIcon.setImageBitmap(bm);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context,"Not Done",Toast.LENGTH_SHORT).show();
        }

        return row;

}
            return row;
	    }

	    static class ImageHolder
	    {
	        ImageView imgIcon;
	        TextView txtTitle;
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
            scale = (int)Math.pow(2, (int) Math.ceil(Math.log(1000 /
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
