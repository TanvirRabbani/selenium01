package com.ebfs.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

 public class PropertiesUtil {
	
	private static Properties prop;
	//private static PropertiesUtil propUtil;
	public static String filePath = "./object.properties";
	
	
	
	public static void getPropertiesInstance() 
	{
		getPropertiesInstance(filePath);
		
	}
	
	public static void getPropertiesInstance(String path)
	{
		try{
		if(prop==null)
		{
			prop= new Properties();
			prop.load( new FileInputStream(path));
			
		}
		}catch(FileNotFoundException fnf)
		{
			System.out.println(fnf.getStackTrace());
		}
		catch(Exception e)
		{
			System.out.println(e.getStackTrace());
		}
		
		
	}
	
	public static String getProperty(String key)
	{
		String returnVal = null;
		if(prop==null)
		{
			getPropertiesInstance();
		}
		returnVal = prop.getProperty(key);
		return returnVal;
	}
	


 }