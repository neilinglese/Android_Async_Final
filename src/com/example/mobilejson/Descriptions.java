package com.example.mobilejson;



import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class Descriptions extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_descriptions);
		
		Bundle bundle = getIntent().getExtras();
		String theName = bundle.getString("TheName");
		String theNumber = bundle.getString("TheNumber");
		String theDes = bundle.getString("TheDes");
		String theImage = bundle.getString("TheImage");
		
		ImageView image = (ImageView)findViewById(R.id.imageView1);
		
		ImageDownloader imageLoader = new ImageDownloader(this);
		imageLoader.DisplayImage(theImage, image);
		
		TextView name = (TextView)findViewById(R.id.dname);
		TextView number = (TextView)findViewById(R.id.dnumber);
		TextView des = (TextView)findViewById(R.id.description);
		name.setText(theName);
		number.setText(theNumber);
		des.setText(theDes);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.descriptions, menu);
		return true;
	}

}
