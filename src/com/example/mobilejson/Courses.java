package com.example.mobilejson;

import android.graphics.Bitmap;

public class Courses {
	
	public String courseName;
	public String courseNumber;
	public String courseDescription;
	public String imageURL;
	public String JsonLink;
	public Bitmap myImage;
	
	public Courses(String JsonLink,String Name, String Number)
	{
		this.JsonLink = JsonLink;
		this.courseName = Name;
		this.courseNumber = Number;
	}
	
	public Courses(String Number, String Name,String Description, String URL)
	{
		this.courseNumber = Number;
		this.courseName = Name;
		this.courseDescription = Description;
		this.imageURL = URL;
	}

}
