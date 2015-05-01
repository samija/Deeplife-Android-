package abella.deeplife;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
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

public class ContactImageAdapter extends ArrayAdapter<User> {
    public int getnumberofdesiples = 0;
    Bitmap bmscaled,bm;
String hackstr;

    Context context;
    int layoutResourceId,pos=0;
    View row;
    ImageHolder holder;
    ArrayList<User> data = new ArrayList<User>();
    User picture;
    public ContactImageAdapter(Context context, int layoutResourceId, List<User> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = (ArrayList<User>) data;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        row = convertView;


        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ImageHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.tv);
            holder.imgIcon = (ImageView) row.findViewById(R.id.imageView2);
            holder.txtTitle.setTypeface(Typeface.createFromAsset(context.getAssets(), "mafont2.ttf"));
            row.setTag(holder);
        } else {
            holder = (ImageHolder) row.getTag();
        }
        picture = data.get(position);
        hackstr = picture.image;
        holder.txtTitle.setText("   " +picture.fname + " " + picture.lname + "\n\n   +" + picture.phone +"\n\n   "+ picture.email);









        try {
            if(picture.image.contains("/")){
                bm = BitmapFactory.decodeFile(picture.image);
                //   resize z bitmap to ma imgview fo btr performance
                bmscaled = Bitmap.createScaledBitmap(bm, 80, 75, true);
                holder.imgIcon.setImageBitmap(bmscaled);

            }else{
                holder.imgIcon.setImageResource(R.drawable.avater1);

            }

        } catch (OutOfMemoryError e) {

            e.printStackTrace();

            try {
                if(picture.image.contains("/")){
                    bm = decodeFile(picture.image);
                    bmscaled = Bitmap.createScaledBitmap(bm,80,75 ,true);
                    holder.imgIcon.setImageBitmap(bmscaled);

                }else{
                    holder.imgIcon.setImageResource(R.drawable.avater1);

                }

            } catch (IOException e1) {
                e1.printStackTrace();
                Toast.makeText(context, "Not Done", Toast.LENGTH_SHORT).show();
            }catch(NullPointerException ee){

            }
        }


      //*for future use*  new BitmapWorkerTask().execute();
      //  new BitmapWorkerTask().execute();
getnumberofdesiples+=1;
        return row;
    }




    //for future use
    class BitmapWorkerTask extends AsyncTask<View,View,View> {

        @Override
        protected void onPostExecute(View view) {
            super.onPostExecute(view);

        holder.imgIcon.setImageBitmap(bmscaled);

        }

        @Override
        protected View doInBackground(View... views) {
            try {
                bm = decodeFile(picture.image);
                bmscaled = Bitmap.createScaledBitmap(bm, 50, 40, true);
            } catch (IOException e) {
                e.printStackTrace();
            }catch (RuntimeException re){
                re.printStackTrace();
            }

            return row;
        }
    }


    public Bitmap decodeFile(String f) throws IOException {
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

    static class ImageHolder {
        ImageView imgIcon;
        TextView txtTitle;


    }

}