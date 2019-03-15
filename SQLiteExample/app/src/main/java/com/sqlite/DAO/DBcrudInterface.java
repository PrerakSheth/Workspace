package com.android.sqlitewithmvc.DAO;

import java.util.ArrayList;



public interface DBcrudInterface 
{
	public long insertstudent(String student);
	
	public  ArrayList<String>  getallname();
	
	public void deleteByID(int id);
}
