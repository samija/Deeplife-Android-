package abella.deeplife;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Database.User;
import Database.UsersDataSource;

public class ContactImageAdapter extends ArrayAdapter<User> {
    UsersDataSource usersDataSource;

	 Context context;
	    int layoutResourceId;
	   // BcardImage data[] = null;
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
	        //convert byte to bitmap take from contact class
usersDataSource = new UsersDataSource(context);
            usersDataSource.open();


    Cursor cursor = usersDataSource.getRow(position+1);
    if (cursor.moveToFirst()) {
        String image =cursor.getString(usersDataSource.COL_IMAGE) ;
        holder.imgIcon.setImageURI(Uri.parse(picture.image));


return row;

}
            return row;
	    }

	    static class ImageHolder
	    {
	        ImageView imgIcon;
	        TextView txtTitle;
	    }
	}
