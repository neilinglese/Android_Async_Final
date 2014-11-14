package com.example.mobilejson;

import java.util.ArrayList;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;







import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ListActivity {

	public static ArrayList<Courses> myCourses;
	MyAdapter adapter;
	JsonData jd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		jd = new JsonData();
		myCourses = new ArrayList<Courses>();
		adapter = (new MyAdapter(MainActivity.this,R.layout.row,myCourses));	
		new getCourseList("http://iam.colum.edu/portfolio/api/course?json=True").execute();    		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	protected void onListItemClick(ListView l, View v, int position, long id){
   		String name = myCourses.get(position).courseName;
		String number = myCourses.get(position).courseNumber;	        		
		String des = myCourses.get(position).courseDescription;
		String theImage = myCourses.get(position).imageURL;
		
		Intent intent = new Intent(this, Descriptions.class);
		intent.putExtra("TheName", name);
		intent.putExtra("TheNumber", number);
		intent.putExtra("TheDes",des);
		intent.putExtra("TheImage",theImage);

		startActivity(intent);
	}
		
	private class getCourseList extends AsyncTask<Void,Void,Void>
	{
		private String url;
		private ProgressDialog progressDialog;
		
		public getCourseList(String url){
			
			this.url = url;
		}
		
		protected void onPreExecute(){
			progressDialog = ProgressDialog.show(MainActivity.this,"","Loading, please wait...",true);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			try{
				String data = jd.getJSON(url);
				JSONArray obj = new JSONArray(data);
				for (int i=0; i<obj.length(); i++) 
				{
					String text = obj.getString(i);
					   // String text = obj.getString(position);
					    int indexOfFirstSpace = text.indexOf(" ");
					    String returnedNumber = text.substring(0, indexOfFirstSpace);
					    String returnedName = text.substring(indexOfFirstSpace);
					    String Link = "http://iam.colum.edu/portfolio/api/course/"+returnedNumber+"?json=True";		
					    
					    MainActivity.myCourses.add(new Courses(Link,returnedName,"Loading...."));	
				}
				
				}catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result)
		{
			progressDialog.dismiss();
			setListAdapter(adapter);
			//adapter.notifyDataSetChanged();
		}
	}
	private class loadingViews extends AsyncTask<Void,Void,Void>{
		
		
		private int position;

		
		public loadingViews(int position){
			this.position = position;
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			
			try{
					String data = jd.getJSON(MainActivity.myCourses.get(position).JsonLink);
					JSONObject obj = new JSONObject(data);
					JSONArray array = obj.getJSONArray("Images");


					//getting image link in array at 1st position converting to string
					MainActivity.myCourses.get(position).imageURL = array.get(0).toString();
					//gets obj course name and stores as a sting
					MainActivity.myCourses.get(position).courseName = obj.getString("CourseName");
					//gets obj course number stores as string
					MainActivity.myCourses.get(position).courseNumber = obj.getString("CourseNumber");
					//gets obj course description stores as string
					MainActivity.myCourses.get(position).courseDescription = obj.getString("CourseDescription");

			}catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			return null;
		}
		
		@Override
		protected void onPostExecute(Void result){
			adapter.notifyDataSetChanged();
		}
	}
	static class ViewHolder{
		 TextView name;
		 TextView num;
		 ImageView Image;
		
	}
	private class MyAdapter extends ArrayAdapter<Courses>{
		
		private ArrayList<Courses> items;
		private ImageDownloader imageLoader;
		

		public MyAdapter(Context context, int textViewResourceId, ArrayList<Courses> items){
			super(context, textViewResourceId,items);
			this.items = items;
			imageLoader = new ImageDownloader(context);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			
			//View v = convertView;

			ViewHolder holder = null;
			
			if(convertView == null){
				holder = new ViewHolder();
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.row, null);
				holder.name = (TextView)convertView.findViewById(R.id.cname);
				holder.num = (TextView)convertView.findViewById(R.id.cnumber);
				holder.Image = (ImageView)convertView.findViewById(R.id.imageView1);
				
	
				convertView.setTag(holder);		
			}
			else{
				holder = (ViewHolder)convertView.getTag();
			}
			
				holder.name.setText(MainActivity.myCourses.get(position).courseName);
				holder.num.setText(MainActivity.myCourses.get(position).courseNumber);
				imageLoader.DisplayImage(MainActivity.myCourses.get(position).imageURL, holder.Image);
				
			    new loadingViews(position).execute();

			return convertView;
		}

	}
}

 