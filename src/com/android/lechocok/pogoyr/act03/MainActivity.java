package com.android.lechocok.pogoyr.act03;

import java.io.InputStream;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	private String[] imgNames;
	private String[] imgUrls;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		String[] imgNames = {"ToDo","Gmail","LinkedIn","Facebook","Google Play"};
		this.imgNames = imgNames;
		String[] imgUrls = {
				"http://ww1.prweb.com/prfiles/2010/05/11/1751474/gI_TodoforiPadAppIcon512.png.jpg",
				"http://cdn4.iosnoops.com/wp-content/uploads/2011/08/Icon-Gmail_large-250x250.png",
				"http://kelpbeds.files.wordpress.com/2012/02/lens17430451_1294953222linkedin-icon.jpg?w=450",
				"http://snapknot.com/blog/wp-content/uploads/2010/03/facebook-icon-copy.jpg",
				"https://lh3.googleusercontent.com/-ycDGy_fZVZc/AAAAAAAAAAI/AAAAAAAAAAc/Q0MmjxPCOzk/s250-c-k/photo.jpg"
		};
		this.imgUrls = imgUrls;
		
		ListAdapter adapter = new ListAdapter(this, imgNames, imgUrls);
        setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        bmImage.setImageBitmap(result);
	    }
	}
	
	public class ListAdapter extends ArrayAdapter<String> {
    	private final Context context;
    	private final String[] imgUrls;
    	private final String[] imgNames;
    	
		public ListAdapter(Context context, String[] imgNames, String[] imgUrls) {
			super(context, R.layout.activity_main, imgNames);
			this.imgNames = imgNames;
			this.imgUrls = imgUrls;
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
			View row = (View)inflater.inflate(R.layout.activity_main, parent, false);
			
			TextView name = (TextView)row.findViewById(R.id.TextView);
			ImageView image = (ImageView)row.findViewById(R.id.ImageView);
			
			name.setText(imgNames[position]);
			new DownloadImageTask(image).execute(imgUrls[position]);
						
			return row;
		}
	}

}
